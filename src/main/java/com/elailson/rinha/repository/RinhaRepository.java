package com.elailson.rinha.repository;

import com.elailson.rinha.dto.ExtratoTransacaoResponse;
import com.elailson.rinha.entity.Cliente;
import com.elailson.rinha.entity.Transacao;
import com.elailson.rinha.exception.NotFoundException;
import com.elailson.rinha.util.DatabaseConnection;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class RinhaRepository {

    private static final Logger log = Logger.getLogger(RinhaRepository.class.getName());

    private static final String FIND_CLIENTE_BY_ID_QUERY = "SELECT LIMITE, SALDO FROM CLIENTE WHERE ID = ?";
    private static final String SAVE_TRANSACAO_SQL = "INSERT INTO TRANSACAO (VALOR, TIPO, DESCRICAO, REALIZADA_EM, CLIENTE_ID) VALUES (?,?,?,?,?)";
    private static final String UPDATE_SALDO_CLIENTE_SQL = "UPDATE CLIENTE SET SALDO = ? WHERE ID = ?";
    private static final String FIND_LAST_TEN_TRANSACOES_BY_CLIENTE_ID =
            "SELECT VALOR, TIPO, DESCRICAO, REALIZADA_EM FROM TRANSACAO WHERE CLIENTE_ID = ? ORDER BY REALIZADA_EM DESC LIMIT 10";

    private final Connection con = DatabaseConnection.getConnection();

    public void updateSaldoCliente(Integer id, Integer saldo) {
        try (PreparedStatement statement = con.prepareStatement(UPDATE_SALDO_CLIENTE_SQL)) {
            statement.setInt(1, saldo);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.info(String.format("Houve um erro ao tentar atualizar saldo do cliente: [%s].", e.getMessage()));
        }
    }

    public void saveTransacao(Transacao transacao, Integer clienteId) {
        try (PreparedStatement statement = con.prepareStatement(SAVE_TRANSACAO_SQL)) {
            statement.setInt(1, transacao.getValor());
            statement.setString(2, transacao.getTipo());
            statement.setString(3, transacao.getDescricao());
            statement.setTimestamp(4, Timestamp.from(Instant.now()));
            statement.setInt(5, clienteId);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.info(String.format("Houve um erro ao tentar cadastrar transação: [%s].", e.getMessage()));
        }
    }

    public Cliente findClienteById(Integer id) {
        try (PreparedStatement statement = con.prepareStatement(FIND_CLIENTE_BY_ID_QUERY)) {
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            rs.next();

            return new Cliente(rs.getInt(1), rs.getInt(2));
        } catch (SQLException e) {
            String messageError = String.format("Não foi possível encontrar cliente para ID: [%s].", id);
            log.info(messageError);
            throw new NotFoundException(messageError);
        }
    }

    public List<ExtratoTransacaoResponse> findLastTenTransacoesByClienteId(Integer clienteId) {
        try (PreparedStatement statement = con.prepareStatement(FIND_LAST_TEN_TRANSACOES_BY_CLIENTE_ID)) {
            statement.setInt(1, clienteId);

            ResultSet rs = statement.executeQuery();

            List<ExtratoTransacaoResponse> extratoTransacoes = new ArrayList<>(9);

            while (rs.next()) {
                ExtratoTransacaoResponse extratoTransacao =
                        new ExtratoTransacaoResponse(
                                rs.getInt(1),
                                rs.getString(2).charAt(0),
                                rs.getString(3),
                                rs.getTimestamp(4).toInstant());
                extratoTransacoes.add(extratoTransacao);
            }

            return extratoTransacoes;
        } catch (SQLException e) {
            log.info(String.format("Não foi possível encontrar transações para cliente de ID: [%s].", clienteId));
            return Collections.emptyList();
        }
    }

}

package com.elailson.rinha.repository;

import com.elailson.rinha.entity.Cliente;
import com.elailson.rinha.entity.Transacao;
import com.elailson.rinha.exception.NotFoundException;
import com.elailson.rinha.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class RinhaRepository {

    private static final Logger log = Logger.getLogger(RinhaRepository.class.getName());
    private static final String SAVE_CLIENTE_SQL = "INSERT INTO CLIENTE (LIMITE, SALDO) VALUES (?,?)";
    private static final String FIND_CLIENTE_BY_ID_QUERY = "SELECT LIMITE, SALDO FROM CLIENTE WHERE ID = ?";
    private static final String SAVE_TRANSACAO_SQL = "INSERT INTO TRANSACAO (TIPO, DESCRICAO, REALIZADA_EM) VALUES (?,?,?)";
    private static final String UPDATE_SALDO_CLIENTE_SQL = "UPDATE CLIENTE SET SALDO = ? WHERE ID = ?";

    private final Connection con = DatabaseConnection.getConnection();

    public Cliente saveCliente(Cliente entity) {
        try (PreparedStatement statement = con.prepareStatement(SAVE_CLIENTE_SQL)) {
            statement.setInt(1, entity.getLimite());
            statement.setInt(2, entity.getSaldo());

            statement.executeUpdate();
        } catch (SQLException e) {
            log.info(String.format("Houve um erro ao tentar cadastrar cliente: [%s].", e.getMessage()));
        }

        return entity;
    }

    public Cliente updateSaldoCliente(Integer id, Integer saldo) {
        try (PreparedStatement statement = con.prepareStatement(UPDATE_SALDO_CLIENTE_SQL)) {
            statement.setInt(1, saldo);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.info(String.format("Houve um erro ao tentar atualizar saldo do cliente: [%s].", e.getMessage()));
        }

        return findClienteById(id);
    }

    public void saveTransacao(Transacao transacao) {
        try (PreparedStatement statement = con.prepareStatement(SAVE_TRANSACAO_SQL)) {
            statement.setInt(1, transacao.getTipo());
            statement.setString(2, transacao.getDescricao());
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            statement.executeUpdate();
        } catch (SQLException e) {
            log.info(String.format("Houve um erro ao tentar cadastrar transação: [%s].", e.getMessage()));
        }
    }

    public Cliente findClienteById(Integer id) {
        try (PreparedStatement statement = con.prepareStatement(FIND_CLIENTE_BY_ID_QUERY)) {
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            return new Cliente(result.getInt(1), result.getInt(2));
        } catch (SQLException e) {
            String messageError = String.format("Não foi possível encontrar cliente para ID: [%s].", id);
            log.info(messageError);
            throw new NotFoundException(messageError);
        }
    }

}

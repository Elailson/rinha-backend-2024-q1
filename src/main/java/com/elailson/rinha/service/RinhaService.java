package com.elailson.rinha.service;

import com.elailson.rinha.dto.ExtratoResponse;
import com.elailson.rinha.dto.ExtratoSaldoResponse;
import com.elailson.rinha.dto.ExtratoTransacaoResponse;
import com.elailson.rinha.entity.Cliente;
import com.elailson.rinha.entity.Transacao;
import com.elailson.rinha.exception.InsufficientLimitException;
import com.elailson.rinha.exception.NotFoundException;
import com.elailson.rinha.repository.RinhaRepository;

import java.time.Instant;
import java.util.List;

public class RinhaService {

    private static final String CREDITO = "c";
    private static final String DEBITO = "d";

    private final RinhaRepository repository;

    public RinhaService() {
        this.repository = new RinhaRepository();
    }

    public Cliente process(Transacao transacao, Integer clienteId) throws NotFoundException {
        Cliente cliente = repository.findClienteById(clienteId);

        if (CREDITO.equals(transacao.getTipo())) {
            Integer newSaldo = (cliente.getSaldo() + transacao.getValor());
            saveTransactionAndUpdateSaldoCliente(transacao, clienteId, newSaldo);
            cliente.setSaldo(newSaldo);

            return cliente;
        }

        if (DEBITO.equals(transacao.getTipo())) {
            if (cliente.getSaldo() >= transacao.getValor() ||
                    (Math.abs(transacao.getValor()) + Math.abs(cliente.getSaldo())) <= cliente.getLimite()) {
                Integer newSaldo = (cliente.getSaldo() - transacao.getValor());
                saveTransactionAndUpdateSaldoCliente(transacao, clienteId, newSaldo);
                cliente.setSaldo(newSaldo);

                return cliente;
            }

            throw new InsufficientLimitException("Limite insuficiente para completar transação.");
        }

        throw new UnsupportedOperationException("Essa exception nunca será lançada");
    }

    public ExtratoResponse getExtrato(Integer clienteId) {
        Cliente cliente = repository.findClienteById(clienteId);
        ExtratoSaldoResponse extratoSaldo =
                new ExtratoSaldoResponse(cliente.getSaldo(), cliente.getLimite(), Instant.now());

        List<ExtratoTransacaoResponse> extratoTransacoes = repository.findLastTenTransacoesByClienteId(clienteId);

        return new ExtratoResponse(extratoSaldo, extratoTransacoes);
    }

    private void saveTransactionAndUpdateSaldoCliente(Transacao transacao, Integer clienteId, Integer saldo) {
        repository.saveTransacao(transacao, clienteId);
        repository.updateSaldoCliente(clienteId, saldo);
    }

}

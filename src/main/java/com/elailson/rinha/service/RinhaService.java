package com.elailson.rinha.service;

import com.elailson.rinha.dto.ExtratoResponse;
import com.elailson.rinha.dto.ExtratoSaldoResponse;
import com.elailson.rinha.dto.ExtratoTransacaoResponse;
import com.elailson.rinha.entity.Cliente;
import com.elailson.rinha.entity.Transacao;
import com.elailson.rinha.exception.InsufficientLimitException;
import com.elailson.rinha.exception.NotFoundException;
import com.elailson.rinha.repository.RinhaRepository;

import java.time.LocalDateTime;
import java.util.List;

public class RinhaService {

    private static final char CREDITO = 'c';
    private static final char DEBITO = 'd';

    private final RinhaRepository repository;

    public RinhaService() {
        this.repository = new RinhaRepository();
    }

    public Cliente process(Transacao transacao, Integer clienteId) throws NotFoundException {
        Cliente cliente = repository.findClienteById(clienteId);

        if (transacao.getTipo() == CREDITO) {
            return saveTransactionAndUpdateSaldoCliente(transacao, clienteId, (cliente.getSaldo() + transacao.getValor()));
        }

        if (transacao.getTipo() == DEBITO) {
            if (Math.abs(transacao.getValor() + cliente.getSaldo()) > cliente.getLimite())
                throw new InsufficientLimitException("Limite insuficiente para completar transação.");

            return saveTransactionAndUpdateSaldoCliente(transacao, clienteId, (cliente.getSaldo() - transacao.getValor()));
        }

        throw new UnsupportedOperationException();
    }

    public ExtratoResponse getExtrato(Integer clienteId) {
        Cliente cliente = repository.findClienteById(clienteId);
        ExtratoSaldoResponse extratoSaldo =
                new ExtratoSaldoResponse(cliente.getSaldo(), cliente.getLimite(), LocalDateTime.now());

        List<ExtratoTransacaoResponse> extratoTransacoes = repository.findLastTenTransacoesByClienteId(clienteId);

        return new ExtratoResponse(extratoSaldo, extratoTransacoes);
    }

    private Cliente saveTransactionAndUpdateSaldoCliente(Transacao transacao, Integer clienteId, Integer saldo) {
        repository.saveTransacao(transacao);
        return repository.updateSaldoCliente(clienteId, saldo);
    }

}

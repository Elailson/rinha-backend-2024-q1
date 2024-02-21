package com.elailson.rinha.service;

import com.elailson.rinha.dto.TransacaoRequest;
import com.elailson.rinha.entity.Cliente;
import com.elailson.rinha.exception.InsufficientLimitException;
import com.elailson.rinha.exception.NotFoundException;
import com.elailson.rinha.repository.RinhaRepository;

public class RinhaService {

    private static final char CREDITO = 'c';
    private static final char DEBITO = 'd';

    private final RinhaRepository repository;

    public RinhaService() {
        this.repository = new RinhaRepository();
    }

    public Cliente process(TransacaoRequest request, Integer clienteId) throws NotFoundException {
        Cliente cliente = repository.findClienteById(clienteId);

        if (request.getTipo() == CREDITO) {

        }

        if (request.getTipo() == DEBITO) {
            if ((request.getValor() + cliente.getSaldo()) > cliente.getLimite()) {
                throw new InsufficientLimitException("Limite insuficiente para completar transação.");
            }
        }

        return cliente;
    }

}

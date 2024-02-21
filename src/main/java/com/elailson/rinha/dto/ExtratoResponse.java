package com.elailson.rinha.dto;

import java.util.List;

public class ExtratoResponse {

    private ExtratoSaldoResponse saldo;
    private List<ExtratoTransacaoResponse> ultimasTransacoes;

    public ExtratoSaldoResponse getSaldo() {
        return saldo;
    }

    public void setSaldo(ExtratoSaldoResponse saldo) {
        this.saldo = saldo;
    }

    public List<ExtratoTransacaoResponse> getUltimasTransacoes() {
        return ultimasTransacoes;
    }

    public void setUltimasTransacoes(List<ExtratoTransacaoResponse> ultimasTransacoes) {
        this.ultimasTransacoes = ultimasTransacoes;
    }

}

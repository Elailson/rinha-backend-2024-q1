package com.elailson.rinha.dto;

public class TransacaoResponse {

    private Integer limite;
    private Integer saldo;

    public Integer getLimite() {
        return limite;
    }

    public void setLimite(Integer limite) {
        this.limite = limite;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

}

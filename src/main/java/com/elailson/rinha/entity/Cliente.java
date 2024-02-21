package com.elailson.rinha.entity;

public class Cliente {

    private Integer id;
    private Integer limite;
    private Integer saldo;

    public Cliente() {
    }

    public Cliente(Integer limite, Integer saldo) {
        this.limite = limite;
        this.saldo = saldo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

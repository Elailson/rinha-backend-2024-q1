package com.elailson.rinha.entity;

import java.time.Instant;

public class Transacao {

    private Integer valor;
    private String tipo;
    private String descricao;
    private Instant realizadaEm;

    public Transacao() {

    }

    public Transacao(Integer valor, String tipo, String descricao) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getRealizadaEm() {
        return realizadaEm;
    }

    public void setRealizadaEm(Instant realizadaEm) {
        this.realizadaEm = realizadaEm;
    }

}

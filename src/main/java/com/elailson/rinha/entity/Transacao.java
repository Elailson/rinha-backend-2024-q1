package com.elailson.rinha.entity;

import java.time.LocalDateTime;

public class Transacao {

    private Integer valor;
    private char tipo;
    private String descricao;
    private LocalDateTime realizadaEm;

    public Transacao() {

    }

    public Transacao(Integer valor, char tipo, String descricao) {
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

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getRealizadaEm() {
        return realizadaEm;
    }

    public void setRealizadaEm(LocalDateTime realizadaEm) {
        this.realizadaEm = realizadaEm;
    }

}

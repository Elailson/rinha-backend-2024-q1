package com.elailson.rinha.dto;

import java.time.LocalDateTime;

public class ExtratoTransacaoResponse {

    private Integer valor;
    private char tipo;
    private String descricao;
    private LocalDateTime realizadaEm;

    public ExtratoTransacaoResponse(Integer valor, char tipo, String descricao, LocalDateTime realizadaEm) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizadaEm = realizadaEm;
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

package com.elailson.rinha.dto;

import java.time.Instant;

public class ExtratoSaldoResponse {

    private Integer total;
    private Integer limite;
    private Instant dataExtrato;

    public ExtratoSaldoResponse(Integer total, Integer limite, Instant dataExtrato) {
        this.total = total;
        this.limite = limite;
        this.dataExtrato = dataExtrato;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getLimite() {
        return limite;
    }

    public void setLimite(Integer limite) {
        this.limite = limite;
    }

    public Instant getDataExtrato() {
        return dataExtrato;
    }

    public void setDataExtrato(Instant dataExtrato) {
        this.dataExtrato = dataExtrato;
    }

}

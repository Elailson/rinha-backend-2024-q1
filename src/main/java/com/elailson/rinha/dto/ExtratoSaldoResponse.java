package com.elailson.rinha.dto;

import java.time.LocalDateTime;

public class ExtratoSaldoResponse {

    private Integer total;
    private Integer limite;
    private LocalDateTime dataExtrato;

    public ExtratoSaldoResponse(Integer total, Integer limite, LocalDateTime dataExtrato) {
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

    public LocalDateTime getDataExtrato() {
        return dataExtrato;
    }

    public void setDataExtrato(LocalDateTime dataExtrato) {
        this.dataExtrato = dataExtrato;
    }

}

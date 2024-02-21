package com.elailson.rinha.dto;

import java.time.LocalDateTime;

public class ExtratoSaldoResponse {

    private Integer total;
    private LocalDateTime dataExtrato;
    private Integer limite;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public LocalDateTime getDataExtrato() {
        return dataExtrato;
    }

    public void setDataExtrato(LocalDateTime dataExtrato) {
        this.dataExtrato = dataExtrato;
    }

    public Integer getLimite() {
        return limite;
    }

    public void setLimite(Integer limite) {
        this.limite = limite;
    }

}

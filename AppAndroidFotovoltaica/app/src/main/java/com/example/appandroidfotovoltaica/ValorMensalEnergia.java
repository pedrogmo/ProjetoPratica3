package com.example.appandroidfotovoltaica;

public class ValorMensalEnergia {
    private String mes;
    private double valor;

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) throws Exception
    {
        this.valor = valor;
    }

    public ValorMensalEnergia(String mes, double valor) {
        this.mes = mes;
        this.valor = valor;
    }
    public ValorMensalEnergia(String mes) {
        this.mes = mes;
    }
    public String toString()
    {
        return mes + "-" + valor;
    }
}

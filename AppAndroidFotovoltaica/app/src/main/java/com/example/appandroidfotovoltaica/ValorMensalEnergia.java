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
    public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (this.getClass()!=obj.getClass())
            return false;

        ValorMensalEnergia hor = (ValorMensalEnergia)obj;

        if (this.mes!=hor.mes)
            return false;

        if (this.valor!=hor.valor)
            return false;

        return true;
    }

    public int hashCode ()
    {
        int ret=666;

        ret = ret*2 + new Integer (this.mes   ).hashCode();
        ret = ret*2 + new Double  (this.valor ).hashCode();

        return ret;
    }

    public ValorMensalEnergia (ValorMensalEnergia modelo) throws Exception
    {
        if (modelo==null)
            throw new Exception ("Modelo ausente");

        this.mes   =modelo.mes;
        this.valor =modelo.valor;
    }

    public Object clone ()
    {
        ValorMensalEnergia ret=null;

        try
        {
            ret = new ValorMensalEnergia (this);
        }
        catch (Exception erro)
        {}

        return ret;
    }

}

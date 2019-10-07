package com.example.appandroidfotovoltaica;

public class Data {
    private byte dia;
    private byte mes;
    private short ano;

    public byte getDia() {
        return dia;
    }

    public void setDia(byte dia) throws Exception {
        if (dia <= 0 || dia > 31)
            throw new Exception("Data - setDia: dia inválido");
        this.dia = dia;
    }

    public byte getMes() {
        return mes;
    }

    public void setMes(byte mes) throws Exception {
        if (mes <= 0 || mes > 12)
            throw new Exception("Data - setMes: mes inválido");
        this.mes = mes;
    }

    public short getAno() {
        return ano;
    }

    public void setAno(short ano) throws Exception {
        if (ano <= 1850 || ano > 9999)
            throw new Exception("Data - setAno: ano inválido");
        this.ano = ano;
    }
    public String toString()
    {
        return
        String.format("%2s", dia).replace(' ', '0')    + "/" +
        String.format("%2s", mes).replace(' ', '0') + "/" +
        ano;
    }
    public int compareTo(Data outra)
    {
        if (ano < outra.ano)
            return -1;

        if (ano > outra.ano)
            return 1;

        if (mes < outra.mes)
            return -1;

        if (mes > outra.mes)
            return 1;

        if (dia < outra.dia)
            return -1;

        if (dia > outra.dia)
            return 1;

        return 0;
    }
    public boolean equals(Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (this.getClass()!=obj.getClass())
            return false;

        Data data = (Data)obj;

        if (this.dia!=data.dia)
            return false;

        if (this.mes!=data.mes)
            return false;

        if (this.ano!=data.ano)
            return false;

        return true;
    }
    public int hashCode ()
    {
        int ret=1;

        ret = ret*2 + new Byte  (this.dia   ).hashCode();
        ret = ret*2 + new Byte  (this.mes   ).hashCode();
        ret = ret*2 + new Short (this.ano   ).hashCode();

        return ret;
    }

    public Data (Data modelo) throws Exception
    {
        if (modelo==null)
            throw new Exception ("Modelo ausente");

        this.dia   =modelo.dia;
        this.mes   =modelo.mes;
        this.ano   =modelo.ano;
    }

    public Object clone ()
    {
        Data ret = null;

        try
        {
            ret = new Data (this);
        }
        catch (Exception erro)
        {}

        return ret;
    }


}

package com.example.appandroidfotovoltaica.classes.produto.stringbox;

import com.example.appandroidfotovoltaica.classes.produto.Produto;

public class StringBox extends Produto
{
    private String tipo;
    private int numeroPolos;
    private float tensaoMaxima; //volts
    private float correnteNominal; //ampères

    public StringBox()
    {
        super();
        this.tipo = "";
        this.numeroPolos = 0;
        this.tensaoMaxima = 0.0f;
        this.correnteNominal = 0.0f;
    }

    public StringBox(
        int codigo,
        String nome,
        float preco,
        String descricao,
        String tipo,
        int numeroPolos,
        float tensaoMaxima,
        float correnteNominal) throws Exception
    {
        super(
            codigo,
            nome,
            preco,
            descricao);

        this.setTipo(tipo);
        this.setNumeroPolos(numeroPolos);
        this.setTensaoMaxima(tensaoMaxima);
        this.setCorrenteNominal(correnteNominal);
    }

    public StringBox(
        StringBox modelo) throws Exception
    {
        super(modelo);
        this.tipo = modelo.tipo;
        this.correnteNominal = modelo.correnteNominal;
        this.tensaoMaxima = modelo.tensaoMaxima;
        this.numeroPolos = modelo.numeroPolos;
    }

    public String getTipo()
    {
        return this.tipo;
    }

    public void setTipo(
        String tipo) throws Exception
    {
        if (tipo == null)
            throw new NullPointerException("StringBox: setTipo - tipo ausente");
        if (tipo.equals(""))
            throw new IllegalArgumentException("StringBox - setTipo: tipo inválido");
        this.tipo = tipo;
    }

    public int getNumeroPolos()
    {
        return this.numeroPolos;
    }

    public void setNumeroPolos(
        int numeroPolos) throws Exception
    {
        if (numeroPolos <= 0)
            throw new IllegalArgumentException("StringBox - setNumeroPolos: número de polos inválido");
        this.numeroPolos = numeroPolos;
    }

    public float getTensaoMaxima()
    {
        return this.tensaoMaxima;
    }

    public void setTensaoMaxima(
        float tensaoMaxima) throws Exception
    {
        if (tensaoMaxima <= 0.0f)
            throw new IllegalArgumentException("StringBox - setTensaoMaxima: tensão máxima inválida");
        this.tensaoMaxima = tensaoMaxima;
    }

    public float getCorrenteNominal()
    {
        return this.correnteNominal;
    }

    public void setCorrenteNominal(
        float correnteNominal) throws Exception
    {
        if (tensaoMaxima <= 0.0f)
            throw new IllegalArgumentException("StringBox - setCorrenteNominal: corrente nominal inválida");
        this.correnteNominal = correnteNominal;
    }

    public String toString()
    {
        return "";
    }

    public boolean equals(
        Object obj)
    {
        if (!super.equals(obj))
            return false;
        if (this.getClass() != obj.getClass())
            return false;

        StringBox sb = (StringBox) obj;

        if (this.numeroPolos != sb.numeroPolos)
            return false;
        if (!this.tipo.equals(sb.tipo))
            return false;
        if (this.tensaoMaxima != sb.tensaoMaxima)
            return false;
        if (this.correnteNominal != sb.correnteNominal)
            return false;

        return true;
    }

    public int hashCode()
    {
        int ret = super.hashCode();
        ret = ret * 2 + this.tipo.hashCode();
        ret = ret * 2 + new Integer(this.numeroPolos).hashCode();
        ret = ret * 2 + new Float(this.tensaoMaxima).hashCode();
        ret = ret * 2 + new Float(this.correnteNominal).hashCode();
        return ret;
    }

    public Object clone()
    {
        StringBox ret = null;
        try{ret = new StringBox(this);}
        catch (Exception exc){}
        return ret;
    }
}

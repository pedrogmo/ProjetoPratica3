package com.example.appandroidfotovoltaica;

public class Fixacao extends Produto
{
    public Fixacao(
        int codigo,
        String nome,
        float preco,
        String descricao) throws Exception
    {
        super(
            codigo,
            nome,
            preco,
            descricao);
    }

    public Fixacao()
    {
        super();
    }

    public Fixacao(
        Fixacao modelo) throws Exception
    {
        super(modelo);
    }

    public Object clone()
    {
        Fixacao ret = null;
        try{ret = new Fixacao(this);}
        catch(Exception exc){}
        return ret;
    }
}

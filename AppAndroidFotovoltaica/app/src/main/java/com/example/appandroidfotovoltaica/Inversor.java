package com.example.appandroidfotovoltaica;

public class Inversor extends Produto
{
    private Dimensao dimensao;
    private float peso;
    private float eficienciaMaxima;

    public Inversor()
    {
        super();
        this.dimensao = new Dimensao();
        this.peso = 0.0f;
        this.eficienciaMaxima = 0.0f;
    }

    public Inversor(
        int codigo,
        String nome,
        float preco,
        String descricao,
        Dimensao dimensao,
        float peso,
        float eficienciaMaxima) throws Exception
    {
        super(
                codigo,
                nome,
                preco,
                descricao);
        this.setDimensao(dimensao);
        this.setPeso(peso);
        this.setEficienciaMaxima(eficienciaMaxima);
    }

    public Inversor(
            Inversor modelo) throws Exception
    {
        super(modelo);
        this.dimensao = (Dimensao) modelo.dimensao.clone();
        this.peso = modelo.peso;
        this.eficienciaMaxima = modelo.eficienciaMaxima;
    }

    public Dimensao getDimensao()
    {
        return (Dimensao) dimensao.clone();
    }

    public void setDimensao(
            Dimensao dimensao) throws Exception
    {
        if (dimensao == null)
            throw new NullPointerException("Inversor - setDimensao: dimensão ausente");
        this.dimensao = (Dimensao) dimensao.clone();
    }

    public float getPeso()
    {
        return peso;
    }

    public void setPeso(
            float peso) throws Exception
    {
        if (peso < 0.0f)
            throw new IllegalArgumentException("Inversor - setPeso: peso negativo");
        this.peso = peso;
    }

    public float getEficienciaMaxima()
    {
        return this.eficienciaMaxima;
    }

    public void setEficienciaMaxima(
            float eficienciaMaxima) throws Exception
    {
        if (eficienciaMaxima < 0.0f)
            throw new IllegalArgumentException("Inversor - setEficienciaMaxima: eficiência máxima negativa");
        this.eficienciaMaxima = eficienciaMaxima;
    }

    public String toString()
    {
        return super.toString() + this.eficienciaMaxima;
    }

    public int hashCode()
    {
        int ret = super.hashCode();
        ret = ret * 2 + this.dimensao.hashCode();
        ret = ret * 2 + new Float(this.peso).hashCode();
        ret = ret * 2 + new Float(this.eficienciaMaxima).hashCode();
        return ret;
    }

    public Object clone()
    {
        Inversor ret = null;
        try{ ret = new Inversor(this); }
        catch (Exception e){}
        return ret;
    }
}

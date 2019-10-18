package com.example.appandroidfotovoltaica;

import java.io.Serializable;

public class Dimensao implements
    Cloneable, Serializable
{
    private float altura;
    private float largura;
    private float profundidade;

    public Dimensao(
        float altura,
        float largura,
        float profundidade) throws Exception
    {
        this.setAltura(altura);
        this.setLargura(largura);
        this.setProfundidade(profundidade);
    }

    public Dimensao()
    {
        this.altura = 0.0f;
        this.largura = 0.0f;
        this.profundidade = 0.0f;
    }

    public Dimensao(
        Dimensao modelo) throws Exception
    {
        if (modelo == null)
            throw new NullPointerException("Dimensao - construtor de copia: modelo ausente");
        this.altura = modelo.altura;
        this.largura = modelo.largura;
        this.profundidade = modelo.profundidade;
    }

    public float getAltura()
    {
        return this.altura;
    }

    public void setAltura(
        float altura) throws Exception
    {
        if (altura < 0.0f)
            throw new IllegalArgumentException("Dimensao - setAltura: altura da dimensão inválida");
        this.altura = altura;
    }

    public float getLargura()
    {
        return this.largura;
    }

    public void setLargura(
        float largura) throws Exception
    {
        if (largura < 0.0f)
            throw new IllegalArgumentException("Dimensao - setLargura: largura da dimensão inválida");
        this.largura = largura;
    }

    public float getProfundidade()
    {
        return this.profundidade;
    }

    public void setProfundidade(
        float profundidade) throws Exception
    {
        if (profundidade < 0.0f)
            throw new IllegalArgumentException("Dimensao - setProfundidade: profundidade da dimensão inválida");
        this.profundidade = profundidade;
    }

    public String toString()
    {
        return this.altura + " " + this.largura + " " + this.profundidade;
    }

    public int hashCode()
    {
        int ret = 0;
        ret += ret * 2 + new Float(this.altura).hashCode();
        ret += ret * 2 + new Float(this.largura).hashCode();
        ret += ret * 2 + new Float(this.profundidade).hashCode();
        return ret;
    }

    public boolean equals(
        Object obj)
    {
        if (this == obj)
            return false;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;

        Dimensao d = (Dimensao) obj;

        if (this.altura != d.altura)
            return false;
        if (this.largura != d.largura)
            return false;
        if (this.profundidade != d.profundidade)
            return false;

        return true;
    }

    public Object clone()
    {
        Dimensao ret = null;
        try{ ret = new Dimensao(this); }
        catch(Exception e){}
        return ret;
    }
}

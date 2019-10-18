package com.example.appandroidfotovoltaica;

public class Modulo extends Produto
{
    private Dimensao dimensao;
    private float peso;
    private float voltagem;

    public Modulo()
    {
        super();
        this.dimensao = new Dimensao();
        this.peso = 0.0f;
        this.voltagem = 0.0f;
    }

    public Modulo(
        int codigo,
        String nome,
        float preco,
        String descricao,
        Dimensao dimensao,
        float peso,
        float voltagem) throws Exception
    {
        super(
            codigo,
            nome,
            preco,
            descricao);
        this.setDimensao(dimensao);
        this.setPeso(peso);
        this.setVoltagem(voltagem);
    }

    public Modulo(
        Modulo modelo) throws Exception
    {
        super(modelo);
        this.dimensao = (Dimensao) modelo.dimensao.clone();
        this.peso = modelo.peso;
        this.voltagem = modelo.voltagem;
    }

    public Dimensao getDimensao()
    {
        return (Dimensao) this.dimensao.clone();
    }

    public void setDimensao(
        Dimensao dimensao) throws Exception
    {
        if (dimensao == null)
            throw new NullPointerException("Modulo - setDimensao: dimensão ausente");
        this.dimensao = (Dimensao) dimensao.clone();
    }

    public float getPeso()
    {
        return this.peso;
    }

    public void setPeso(
        float peso) throws Exception
    {
        if (peso < 0.0f)
            throw new IllegalArgumentException("Modulo - setPeso: peso negativo");
        this.peso = peso;
    }

    public float getVoltagem()
    {
        return this.voltagem;
    }

    public void setVoltagem(
        float voltagem) throws Exception
    {
        if (voltagem < 0.0f)
            throw new IllegalArgumentException("Modulo - setVoltagem: voltagem negativa");
        this.voltagem = voltagem;
    }

    public String toString()
    {
        return super.toString() + this.voltagem;
    }

    public int hashCode()
    {
        int ret = super.hashCode();
        ret = ret * 2 + this.dimensao.hashCode();
        ret = ret * 2 + new Float(this.peso).hashCode();
        ret = ret * 2 + new Float(this.voltagem).hashCode();
        return ret;
    }

    public Object clone()
    {
        Modulo ret = null;
        try{ ret = new Modulo(this); }
        catch (Exception e){}
        return ret;
    }
}

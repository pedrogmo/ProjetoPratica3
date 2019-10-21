package com.example.appandroidfotovoltaica;

public class EquipamentoFotovoltaico extends Produto
{
    protected float altura;
    protected float largura;
    protected float profundidade;
    protected float peso;

    public EquipamentoFotovoltaico(
        int codigo,
        String nome,
        float preco,
        String descricao,
        float altura,
        float largura,
        float profundidade,
        float peso) throws Exception
    {
        super(
            codigo,
            nome,
            preco,
            descricao);

        this.setAltura(altura);
        this.setLargura(largura);
        this.setProfundidade(profundidade);
        this.setPeso(peso);
    }

    public EquipamentoFotovoltaico()
    {
        super();
        this.altura = 0.0f;
        this.largura = 0.0f;
        this.profundidade = 0.0f;
        this.peso = 0.0f;
    }

    public EquipamentoFotovoltaico(
            EquipamentoFotovoltaico modelo) throws Exception
    {
        super(modelo);
        this.altura = modelo.altura;
        this.largura = modelo.largura;
        this.profundidade = modelo.profundidade;
        this.peso = modelo.peso;
    }

    public float getAltura()
    {
        return this.altura;
    }

    public void setAltura(
            float altura) throws Exception
    {
        if (altura < 0.0f)
            throw new IllegalArgumentException("EquipamentoFotovoltaico - setAltura: altura da dimensão inválida");
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
            throw new IllegalArgumentException("EquipamentoFotovoltaico - setLargura: largura da dimensão inválida");
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
            throw new IllegalArgumentException("EquipamentoFotovoltaico - setProfundidade: profundidade da dimensão inválida");
        this.profundidade = profundidade;
    }

    public float getPeso()
    {
        return peso;
    }

    public void setPeso(
            float peso) throws Exception
    {
        if (peso < 0.0f)
            throw new IllegalArgumentException("EquipamentoFotovoltaico - setPeso: peso negativo");
        this.peso = peso;
    }

    public String toString()
    {
        return "";
    }

    public int hashCode()
    {
        int ret = super.hashCode();
        ret += ret * 2 + new Float(this.altura).hashCode();
        ret += ret * 2 + new Float(this.largura).hashCode();
        ret += ret * 2 + new Float(this.profundidade).hashCode();
        ret += ret * 2 + new Float(this.peso).hashCode();
        return ret;
    }

    public boolean equals(
            Object obj)
    {
        if(!super.equals(obj))
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        EquipamentoFotovoltaico e = (EquipamentoFotovoltaico) obj;

        if (this.altura != e.altura)
            return false;
        if (this.largura != e.largura)
            return false;
        if (this.profundidade != e.profundidade)
            return false;
        if (this.peso != e.peso)
            return false;

        return true;
    }

    public Object clone()
    {
        EquipamentoFotovoltaico ret = null;
        try{ ret = new EquipamentoFotovoltaico(this); }
        catch(Exception e){}
        return ret;
    }
}

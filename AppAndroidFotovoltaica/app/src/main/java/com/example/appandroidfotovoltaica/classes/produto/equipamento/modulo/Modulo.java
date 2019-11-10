package com.example.appandroidfotovoltaica.classes.produto.equipamento.modulo;

import com.example.appandroidfotovoltaica.classes.produto.equipamento.EquipamentoFotovoltaico;

public class Modulo extends EquipamentoFotovoltaico
{
    private float potencia;

    public Modulo()
    {
        super();
        this.potencia = 0.0f;
    }

    public Modulo(
        int codigo,
        String nome,
        float preco,
        String descricao,
        int codEmpresa,
        float altura,
        float largura,
        float profundidade,
        float peso,
        float potencia) throws Exception
    {
        super(
            codigo,
            nome,
            preco,
            descricao,
            codEmpresa,
            altura,
            largura,
            profundidade,
            peso);

        this.setPotencia(potencia);
    }

    public Modulo(
        Modulo modelo) throws Exception
    {
        super(modelo);
        this.potencia = modelo.potencia;
    }

    public float getPotencia()
    {
        return this.potencia;
    }

    public void setPotencia(
        float potencia) throws Exception
    {
        if (potencia < 0.0f)
            throw new IllegalArgumentException("Modulo - setPotencia: potencia negativa");
        this.potencia = potencia;
    }

    public String toString()
    {
        return "";
    }

    public int hashCode()
    {
        int ret = super.hashCode();
        ret = ret * 2 + new Float(this.potencia).hashCode();
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

package com.example.appandroidfotovoltaica.classes.produto.equipamento.inversor;

import com.example.appandroidfotovoltaica.classes.produto.equipamento.EquipamentoFotovoltaico;

public class Inversor extends EquipamentoFotovoltaico
{
    private float eficienciaMaxima;

    public Inversor()
    {
        super();
        this.eficienciaMaxima = 0.0f;
    }

    public Inversor(
            int codigo,
            String nome,
            float preco,
            String descricao,
            int codEmpresa,
            float altura,
            float largura,
            float profundidade,
            float peso,
            float eficienciaMaxima) throws Exception
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

        this.setEficienciaMaxima(eficienciaMaxima);
    }

    public Inversor(
            Inversor modelo) throws Exception
    {
        super(modelo);
        this.eficienciaMaxima = modelo.eficienciaMaxima;
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
        return "";
    }

    public int hashCode()
    {
        int ret = super.hashCode();
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

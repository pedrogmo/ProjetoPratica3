package com.example.appandroidfotovoltaica.classes.produto.equipamento.modulo;

import com.example.appandroidfotovoltaica.classes.produto.equipamento.EquipamentoFotovoltaico;

public class Modulo extends EquipamentoFotovoltaico
{
    private float voltagem;

    public Modulo()
    {
        super();
        this.voltagem = 0.0f;
    }

    public Modulo(
        int codigo,
        String nome,
        float preco,
        String descricao,
        float altura,
        float largura,
        float profundidade,
        float peso,
        float voltagem) throws Exception
    {
        super(
            codigo,
            nome,
            preco,
            descricao,
            altura,
            largura,
            profundidade,
            peso);

        this.setVoltagem(voltagem);
    }

    public Modulo(
        Modulo modelo) throws Exception
    {
        super(modelo);
        this.voltagem = modelo.voltagem;
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
        return "";
    }

    public int hashCode()
    {
        int ret = super.hashCode();
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

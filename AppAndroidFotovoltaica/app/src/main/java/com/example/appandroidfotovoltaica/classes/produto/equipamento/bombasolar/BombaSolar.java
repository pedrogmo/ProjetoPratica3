package com.example.appandroidfotovoltaica.classes.produto.equipamento.bombasolar;

import com.example.appandroidfotovoltaica.classes.produto.equipamento.EquipamentoFotovoltaico;

public class BombaSolar extends EquipamentoFotovoltaico
{
    private float tensaoAlimentacao;
    private float temperaturaMaxima;
    private float alturaMaxima;
    private float bombeamentoMaximoDiario;
    private String diametroTubo;

    public BombaSolar()
    {
        super();
        this.tensaoAlimentacao = 0.0f;
        this.temperaturaMaxima = 0.0f;
        this.alturaMaxima = 0.0f;
        this.bombeamentoMaximoDiario = 0.0f;
        this.diametroTubo = "";
    }

    public BombaSolar(
        int codigo,
        String nome,
        float preco,
        String descricao,
        float altura,
        float largura,
        float profundidade,
        float peso,
        float tensaoAlimentacao,
        float temperaturaMaxima,
        float alturaMaxima,
        float bombeamentoMaximoDiario,
        String diametroTubo) throws Exception
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

        this.setTensaoAlimentacao(tensaoAlimentacao);
        this.setTemperaturaMaxima(temperaturaMaxima);
        this.setAlturaMaxima(alturaMaxima);
        this.setBombeamentoMaximoDiario(bombeamentoMaximoDiario);
        this.setDiametroTubo(diametroTubo);
    }

    public BombaSolar(
        BombaSolar modelo) throws Exception
    {
        super(modelo);
        this.tensaoAlimentacao = modelo.tensaoAlimentacao;
        this.temperaturaMaxima = modelo.temperaturaMaxima;
        this.alturaMaxima = modelo.alturaMaxima;
        this.bombeamentoMaximoDiario = modelo.bombeamentoMaximoDiario;
        this.diametroTubo = modelo.diametroTubo;
    }

    public float getTensaoAlimentacao()
    {
        return tensaoAlimentacao;
    }

    public void setTensaoAlimentacao(
        float tensaoAlimentacao) throws Exception
    {
        if (tensaoAlimentacao <= 0.0f)
            throw new IllegalArgumentException("BombaSolar - setTensaoAlimentacao: tensão alimentação inválida");
        this.tensaoAlimentacao = tensaoAlimentacao;
    }

    public float getTemperaturaMaxima()
    {
        return temperaturaMaxima;
    }

    public void setTemperaturaMaxima(
        float temperaturaMaxima) throws Exception
    {
        if (temperaturaMaxima <= 0.0f)
            throw new IllegalArgumentException("BombaSolar - setTemperaturaMaxima: temperatura máxima inválida");
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public float getAlturaMaxima()
    {
        return alturaMaxima;
    }

    public void setAlturaMaxima(
        float alturaMaxima)
    {
        if (alturaMaxima <= 0.0f)
            throw new IllegalArgumentException("BombaSolar - setAlturaMaxima: altura máxima inválida");
        this.alturaMaxima = alturaMaxima;
    }

    public float getBombeamentoMaximoDiario()
    {
        return bombeamentoMaximoDiario;
    }

    public void setBombeamentoMaximoDiario(
        float bombeamentoMaximoDiario)
    {
        if (bombeamentoMaximoDiario <= 0.0f)
            throw new IllegalArgumentException("BombaSolar - setBombeamentoMaximoDiario: bombeamento máximo diario inválido");
        this.bombeamentoMaximoDiario = bombeamentoMaximoDiario;
    }

    public String getDiametroTubo()
    {
        return diametroTubo;
    }

    public void setDiametroTubo(
        String diametroTubo)
    {
        if (diametroTubo == null)
            throw new NullPointerException("BombaSolar: setDiametroTubo - diâmetro ausente");
        if (diametroTubo.equals(""))
            throw new IllegalArgumentException("BombaSolar - setDiametroTubo: diâmetro inválido");
        this.diametroTubo = diametroTubo;
    }

    public String toString()
    {
        return "";
    }

    public int hashCode()
    {
        int ret = super.hashCode();
        ret = ret * 2 + new Float(this.tensaoAlimentacao).hashCode();
        ret = ret * 2 + new Float(this.temperaturaMaxima).hashCode();
        ret = ret * 2 + new Float(this.alturaMaxima).hashCode();
        ret = ret * 2 + new Float(this.bombeamentoMaximoDiario).hashCode();
        ret = ret * 2 + this.diametroTubo.hashCode();
        return ret;
    }

    public boolean equals(
        Object obj)
    {
        if (!super.equals(obj))
            return false;
        if (this.getClass() != obj.getClass())
            return false;

        BombaSolar bs = (BombaSolar) obj;

        if (this.tensaoAlimentacao != bs.tensaoAlimentacao)
            return false;
        if (this.temperaturaMaxima != bs.temperaturaMaxima)
            return false;
        if (this.alturaMaxima != bs.alturaMaxima)
            return false;
        if (this.bombeamentoMaximoDiario != bs.bombeamentoMaximoDiario)
            return false;
        if (!this.diametroTubo.equals(bs.diametroTubo))
            return false;

        return true;
    }

    public Object clone()
    {
        BombaSolar ret = null;
        try{ret = new BombaSolar(this);}
        catch(Exception exc){}
        return ret;
    }
}

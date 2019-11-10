package com.example.appandroidfotovoltaica.classes.produto.cabo;

import com.example.appandroidfotovoltaica.classes.produto.Produto;

public class Cabo extends Produto
{
    private float comprimento;
    private float diametro;
    private String conducao;

    public Cabo()
    {
        super();
        this.comprimento = 0.0f;
        this.diametro = 0.0f;
        this.conducao = "";
    }

    public Cabo(
        int codigo,
        String nome,
        float preco,
        String descricao,
        int codEmpresa,
        float comprimento,
        float diametro,
        String conducao) throws Exception
    {
        super(
            codigo,
            nome,
            preco,
            descricao,
            codEmpresa);

        this.setComprimento(comprimento);
        this.setDiametro(diametro);
        this.setConducao(conducao);
    }

    public Cabo(
        Cabo modelo) throws Exception
    {
        super(modelo);
        this.comprimento = modelo.comprimento;
        this.diametro = modelo.diametro;
        this.conducao = modelo.conducao;
    }

    public float getComprimento()
    {
        return comprimento;
    }

    public void setComprimento(
        float comprimento) throws Exception
    {
        if (comprimento <= 0.0f)
            throw new IllegalArgumentException("Cabo - setComprimento: comprimento inválido");
        this.comprimento = comprimento;
    }

    public float getDiametro()
    {
        return diametro;
    }

    public void setDiametro(
        float diametro)
    {
        if (diametro <= 0.0f)
            throw new IllegalArgumentException("Cabo - setDiametro: diâmetro inválido");
        this.diametro = diametro;
    }

    public String getConducao()
    {
        return conducao;
    }

    public void setConducao(
        String conducao) throws Exception
    {
        if (conducao == null)
            throw new NullPointerException("Cabo - setConducao: condução ausente");
        if (conducao.equals(""))
            throw new IllegalArgumentException("Cabo - setConducao: condução inválida");
        this.conducao = conducao;
    }

    public String toString()
    {
        return "";
    }

    public int hashCode()
    {
        int ret = super.hashCode();
        ret = ret * 2 + new Float(this.comprimento).hashCode();
        ret = ret * 2 + new Float(this.diametro).hashCode();
        ret = ret * 2 + this.conducao.hashCode();
        return ret;
    }

    public boolean equals(
        Object obj)
    {
        if (!super.equals(obj))
            return false;
        if (this.getClass() != obj.getClass())
            return false;

        Cabo c = (Cabo) obj;

        if (this.comprimento != c.comprimento)
            return false;
        if (this.diametro != c.diametro)
            return false;
        if (!this.conducao.equals(c.conducao))
            return false;

        return true;
    }


    public Object clone()
    {
        Cabo ret = null;
        try{ret = new Cabo(this);}
        catch(Exception exc){}
        return ret;
    }
}

package com.example.appandroidfotovoltaica.classes.kitproduto;

import java.io.Serializable;

public class KitProduto
    implements Cloneable, Serializable
{
    private int codigo;
    private int codKit;
    private int codProduto;

    public KitProduto()
    {
        this.codigo = 0;
        this.codKit = 0;
        this.codProduto = 0;
    }

    public KitProduto(
        int codigo,
        int codKit,
        int codProduto) throws Exception
    {
        this.setCodigo(codigo);
        this.setCodKit(codKit);
        this.setCodProduto(codProduto);
    }

    public KitProduto(
            KitProduto modelo) throws Exception
    {
        if (modelo == null)
            throw new NullPointerException("KitProduto - construtor de copia: modelo nulo");
        this.codigo = modelo.codigo;
        this.codKit = modelo.codKit;
        this.codProduto = modelo.codProduto;
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(
            int codigo) throws Exception
    {
        if (codigo < 0)
            throw new IllegalArgumentException("KitProduto - setCodigo: código negativo");
        this.codigo = codigo;
    }

    public int getCodKit()
    {
        return codKit;
    }

    public void setCodKit(
            int codKit) throws Exception
    {
        if (codKit < 0)
            throw new IllegalArgumentException("KitProduto - setCodKit: código negativo");
        this.codKit = codKit;
    }

    public int getCodProduto()
    {
        return codProduto;
    }

    public void setCodProduto(
            int codProduto) throws Exception
    {
        if (codProduto < 0)
            throw new IllegalArgumentException("KitProduto - setCodProduto: código negativo");
        this.codProduto = codProduto;
    }

    public int hashCode()
    {
        int ret = 1;
        ret = ret * 2 + new Integer(this.codigo).hashCode();
        ret = ret * 2 + new Integer(this.codKit).hashCode();
        ret = ret * 2 + new Integer(this.codProduto).hashCode();
        return ret;
    }

    public String toString()
    {
        return "";
    }

    public boolean equals(
            Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;

        KitProduto k = (KitProduto) obj;

        if (this.codigo != k.codigo)
            return false;
        if (this.codKit != k.codKit)
            return false;
        if (this.codProduto != k.codProduto)
            return false;

        return true;
    }

    public Object clone()
    {
        KitProduto ret = null;
        try{ret = new KitProduto(this);}
        catch(Exception exc){}
        return ret;
    }
}
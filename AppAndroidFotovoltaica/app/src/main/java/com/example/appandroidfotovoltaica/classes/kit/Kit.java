package com.example.appandroidfotovoltaica.classes.kit;

import java.io.Serializable;

public class Kit
    implements Cloneable, Serializable
{
    private int codigo;
    private int codEmpresa;
    private String nome;

    public Kit()
    {
        this.codigo = 0;
        this.codEmpresa = 0;
        this.nome = "";
    }

    public Kit(
        int codigo,
        int codEmpresa,
        String nome) throws Exception
    {
        this.setCodigo(codigo);
        this.setCodEmpresa(codEmpresa);
        this.setNome(nome);
    }

    public Kit(
        Kit modelo) throws Exception
    {
        if (modelo == null)
            throw new NullPointerException("Kit - construtor de copia: modelo nulo");
        this.codigo = modelo.codigo;
        this.codEmpresa = modelo.codEmpresa;
        this.nome = modelo.nome;
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(
        int codigo) throws Exception
    {
        if (codigo < 0)
            throw new IllegalArgumentException("Kit - setCodigo: código negativo");
        this.codigo = codigo;
    }

    public int getCodEmpresa()
    {
        return codEmpresa;
    }

    public void setCodEmpresa(
        int codEmpresa) throws Exception
    {
        if (codEmpresa < 0)
            throw new IllegalArgumentException("Kit - setCodEmpresa: código negativo");
        this.codEmpresa = codEmpresa;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(
        String nome) throws Exception
    {
        if (nome == null)
            throw new NullPointerException("Kit - setNome: nome nulo");
        if (nome.equals(""))
            throw new IllegalArgumentException("Kit - setNome: nome vazio");
        this.nome = nome;
    }

    public int hashCode()
    {
        int ret = 1;
        ret = ret * 2 + new Integer(this.codigo).hashCode();
        ret = ret * 2 + new Integer(this.codEmpresa).hashCode();
        ret = ret * 2 + this.nome.hashCode();
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

        Kit k = (Kit) obj;

        if (this.codigo != k.codigo)
            return false;
        if (this.codEmpresa != k.codEmpresa)
            return false;
        if (!this.nome.equals(k.nome))
            return false;

        return true;
    }

    public Object clone()
    {
        Kit ret = null;
        try{ret = new Kit(this);}
        catch(Exception exc){}
        return ret;
    }
}
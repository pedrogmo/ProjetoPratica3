package com.example.appandroidfotovoltaica.classes.empresa;

import com.example.appandroidfotovoltaica.classes.verificadora.Verificadora;

import java.io.Serializable;

public class Empresa implements
    Comparable<Empresa>, Cloneable, Serializable
{
    private int codigo;
    private String nome;
    private String cnpj;

    public Empresa(
        int codigo,
        String nome,
        String cnpj) throws Exception
    {
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setCnpj(cnpj);
    }

    public Empresa()
    {
        this.codigo = 0;
        this.nome = "";
        this.cnpj = "";
    }

    public Empresa(
        String cnpj) throws Exception
    {
        this.codigo = 0;
        this.nome = "";
        this.setCnpj(cnpj);
    }

    public Empresa(
        Empresa modelo) throws Exception
    {
        if (modelo == null)
            throw new NullPointerException("Empresa - construtor de cópia: modelo ausente");
        this.codigo = modelo.codigo;
        this.nome = modelo.nome;
        this.cnpj = modelo.cnpj;
    }

    public int getCodigo()
    {
        return this.codigo;
    }

    private void setCodigo(
        int codigo) throws Exception
    {
        if (codigo < 0)
            throw new IllegalArgumentException("Empresa - setCodigo: código negativo");
        this.codigo = codigo;
    }

    public String getNome()
    {
        return this.nome;
    }

    public void setNome(
        String nome) throws Exception
    {
        if (nome == null)
            throw new NullPointerException("Nome de empresa ausente");
        if (nome.equals(""))
            throw new IllegalArgumentException("Digite o nome da empresa");
        this.nome = nome;
    }

    public String getCnpj()
    {
        return this.cnpj;
    }

    public void setCnpj(
            String cnpj) throws Exception
    {
        if (!Verificadora.isCnpjValido(cnpj))
            throw new IllegalArgumentException("Cnpj da empresa inválido");
        this.cnpj = cnpj;
    }

    public String toString()
    {
        return this.codigo + " " + this.cnpj;
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
        Empresa e = (Empresa) obj;
        if (this.codigo != e.codigo)
            return false;
        if (!this.nome.equals(e.nome))
            return false;
        if (!this.cnpj.equals(e.cnpj))
            return false;
        return true;
    }

    public int hashCode()
    {
        int ret = 1;
        ret = ret * 2 + new Integer(this.codigo).hashCode();
        ret = ret * 2 + this.nome.hashCode();
        ret = ret * 2 + this.cnpj.hashCode();
        return ret;
    }

    public int compareTo(
        Empresa outra)
    {
        return this.cnpj.compareTo(outra.cnpj);
    }

    public Object clone()
    {
        Empresa ret = null;
        try
        { ret = new Empresa(this); }
        catch(Exception exc)
        {}
        return ret;
    }
}

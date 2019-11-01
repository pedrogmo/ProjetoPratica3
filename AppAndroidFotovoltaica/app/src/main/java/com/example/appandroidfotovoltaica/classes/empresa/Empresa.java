package com.example.appandroidfotovoltaica.classes.empresa;

import com.example.appandroidfotovoltaica.classes.verificadora.Verificadora;

import java.io.Serializable;

public class Empresa implements
    Comparable<Empresa>, Cloneable, Serializable
{
    private int codigo;
    private String nome;
    private String cnpj;
    private String senha;

    public Empresa(
        int codigo,
        String nome,
        String cnpj,
        String senha) throws Exception
    {
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setCnpj(cnpj);
        this.setSenha(senha);
    }

    public Empresa()
    {
        this.codigo = 0;
        this.nome = "";
        this.cnpj = "";
        this.senha = "";
    }

    public Empresa(
        String cnpj) throws Exception
    {
        this.codigo = 0;
        this.nome = "";
        this.setCnpj(cnpj);
        this.senha = "";
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
            throw new NullPointerException("Empresa - setNome: nome de empresa ausente");
        if (nome.equals(""))
            throw new IllegalArgumentException("Empresa - setNome: nome da empresa vazio");
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
            throw new IllegalArgumentException("Empresa - setCnpj: cnpj da empresa inválido");
        this.cnpj = cnpj;
    }

    public String getSenha(){return this.senha;}

    public void setSenha(
            String senha) throws Exception
    {
        if (nome == null)
            throw new NullPointerException("Empresa - setSenha: senha de empresa ausente");
        if (nome.equals(""))
            throw new IllegalArgumentException("Empresa - setSenha: senha de empresa vazio");
        this.senha = senha;
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
        if (!this.senha.equals(e.senha))
            return false;
        return true;
    }

    public int hashCode()
    {
        int ret = 1;
        ret = ret * 2 + new Integer(this.codigo).hashCode();
        ret = ret * 2 + this.nome.hashCode();
        ret = ret * 2 + this.cnpj.hashCode();
        ret = ret * 2 + this.senha.hashCode();
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

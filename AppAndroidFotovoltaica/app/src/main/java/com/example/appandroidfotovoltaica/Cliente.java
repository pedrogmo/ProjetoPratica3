package com.example.appandroidfotovoltaica;

import android.util.Log;

import java.util.Date;
import java.io.Serializable;

public class Cliente
    implements Cloneable, Comparable<Cliente>, Serializable
{
    private int codigo;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String data;
    private int codEmpresa;

    public Cliente()
    {
        this.codigo = 0;
        this.nome = "";
        this.email = "";
        this.telefone = "";
        this.cpf = "";
        this.data = "";
        this.codEmpresa = 0;
    }

    public Cliente(
        int codigo,
        String email,
        String nome,
        String telefone,
        String data,
        String cpf,
        int codEmpresa) throws Exception
    {
        this.setCodigo(codigo);
        this.setNome(nome);
        this.setEmail(email);
        this.setTelefone(telefone);
        this.setCpf(cpf);
        this.setData(data);
        this.setCodEmpresa(codEmpresa);
    }

    public Cliente(
        String email) throws Exception
    {
        this.codigo = 0;
        this.nome = "";
        this.setEmail(email);
        this.telefone = "";
        this.cpf = "";
        this.data = "";
        this.codEmpresa = 0;
    }

    public Cliente(
        Cliente modelo) throws Exception
    {
        if (modelo == null)
            throw new NullPointerException("Cliente - construtor de copia: modelo ausente");
        this.codigo = modelo.codigo;
        this.nome = modelo.nome;
        this.email = modelo.email;
        this.telefone = modelo.telefone;
        this.cpf = modelo.cpf;
        this.data = modelo.data;
        this.codEmpresa = modelo.codEmpresa;
    }

    public int getCodigo()
    {
        return this.codigo;
    }

    public void setCodigo(
        int codigo) throws Exception
    {
        if (codigo < 0)
            throw new IllegalArgumentException("Cliente - setCodigo: codigo negativo");
        this.codigo = codigo;
    }

    public String getNome()
    {
        return this.nome;
    }

    public void setNome(
        String nome) throws Exception
    {
        if (!Verificadora.isNomeValido(nome))
            throw new Exception("Nome do cliente inválido");
        this.nome = nome;
    }

    public String getEmail() 
    {
        return this.email;
    }

    public void setEmail(
        String email) throws Exception
    {
        if (!Verificadora.isEmailValido(email))
            throw new Exception("Email do cliente inválido");
        this.email = email;
    }

    public String getTelefone() 
    {
        return this.telefone;
    }

    public void setTelefone(
        String telefone) throws Exception
    {
        if (!Verificadora.isTelefoneValido(telefone))
            throw new Exception("Telefone do cliente inválido");
        this.telefone = telefone;
    }

    public String getCpf() 
    {
        return this.cpf;
    }

    public void setCpf(
        String cpf) throws Exception
    {
        if (!Verificadora.isCpfValido(cpf))
            throw new Exception("Cpf do cliente inválido");
        this.cpf = cpf;
    }

    public String getData()
    {
        return this.data;
    }

    public void setData(
        String data) throws Exception
    {
        if(!Verificadora.isDataValida(data))
            throw new Exception("Data do cliente inválida");
        this.data = data;
    }

    public int getCodEmpresa()
    {
        return this.codEmpresa;
    }

    public void setCodEmpresa(
            int codEmpresa) throws Exception
    {
        if (codEmpresa < 0)
            throw new IllegalArgumentException("Cliente - setCodEmpresa: codigo negativo");
        this.codEmpresa = codEmpresa;
    }

    public String toString()
    {
        return this.codigo + " " + this.email;
    }

    public int hashCode()
    {
        int ret = 1;
        ret = ret * 2 + new Integer(this.codigo).hashCode();
        ret = ret * 2 + this.nome.hashCode();
        ret = ret * 2 + this.email.hashCode();
        ret = ret * 2 + this.telefone.hashCode();
        ret = ret * 2 + this.cpf.hashCode();
        ret = ret * 2 + this.data.hashCode();
        ret = ret * 2 + new Integer(this.codEmpresa).hashCode();
        return ret;
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

        Cliente c = (Cliente) obj;

        if (this.codigo != c.codigo)
            return false;
        if (!this.nome.equals(c.nome))
            return false;
        if (!this.email.equals(c.email))
            return false;
        if (!this.telefone.equals(c.telefone))
            return false;
        if (!this.cpf.equals(c.cpf))
            return false;
        if (!this.data.equals(c.data))
            return false;
        if (this.codEmpresa != c.codEmpresa)
            return false;

        return true;
    }

    public int compareTo(
        Cliente c)
    {
        return this.email.compareTo(c.email);
    }

    public Object clone()
    {
        Cliente ret = null;
        try{ ret = new Cliente(this); }
        catch(Exception exc){}
        return ret;
    }
}


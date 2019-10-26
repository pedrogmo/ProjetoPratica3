package com.example.appandroidfotovoltaica.classes.usuario;

import com.example.appandroidfotovoltaica.classes.verificadora.Verificadora;

import java.io.Serializable;

public class Usuario 
	implements Cloneable, Comparable<Usuario>, Serializable
{
	private int codigo;
	private String email;
	private String nome;
	private String senha;
	private int codEmpresa;
	private String telefone;
    private String cpf;
    private String data;

	public Usuario()
	{
		this.codigo = 0;
		this.email = "";
		this.nome = "";
		this.senha = "";
		this.telefone = "";
        this.cpf = "";
        this.data = "";
		this.codEmpresa = 0;
	}

	public Usuario(
		int codigo,
		String email,
		String nome,
		String senha,
		int codEmpresa,
		String telefone,
		String cpf,
		String data) throws Exception
	{
		this.setCodigo(codigo);
		this.setEmail(email);
		this.setNome(nome);
		this.setSenha(senha);
		this.setCodEmpresa(codEmpresa);
		this.setTelefone(telefone);
		this.setData(data);
        this.setCpf(cpf);
	}

	public Usuario(
		String email) throws Exception
	{
		this.codigo = 0;
		this.setEmail(email);
		this.nome = "";
		this.senha = "";
		this.codEmpresa = 0;
		this.telefone = "";
        this.cpf = "";
        this.data = "";
	}

	public Usuario(
		Usuario modelo) throws Exception
	{
		if (modelo == null)
			throw new NullPointerException("Usuario - construtor de copia: modelo ausente");
		this.codigo = modelo.codigo;
		this.email = modelo.email;
		this.nome = modelo.nome;
		this.senha = modelo.senha;
		this.codEmpresa = modelo.codEmpresa;
		this.telefone = modelo.telefone;
        this.cpf = modelo.cpf;
        this.data = modelo.data;
	}

	public int getCodigo()
	{
		return this.codigo;
	}

	public void setCodigo(
		int codigo) throws Exception
	{
		if (codigo < 0)
			throw new IllegalArgumentException("Usuario - setCodigo: codigo negativo");
		this.codigo = codigo;
	}
	
	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(
		String email) throws Exception
	{
		if (!Verificadora.isEmailValido(email))
            throw new Exception("Usuario - setEmail: email invalido");
		this.email = email;
	}
	
	public String getNome()
	{
		return this.nome;
	}

	public void setNome(
		String nome) throws Exception
	{
		if (!Verificadora.isNomeValido(nome))
            throw new Exception("Usuario - setNome: nome invalido");
		this.nome = nome;
	}
	
	public String getSenha()
	{
		return this.senha;
	}

	public void setSenha(
		String senha) throws Exception
	{
		if (senha == null)
			throw new NullPointerException("Usuario - setSenha: senha nula");
		if (senha.equals(""))
			throw new IllegalArgumentException("Usuario - setSenha: senha está vazia");
		this.senha = senha;
	}

	public int getCodEmpresa()
	{
		return this.codEmpresa;
	}

	public void setCodEmpresa(
		int codEmpresa) throws Exception
	{
		if (codEmpresa < 0)
			throw new IllegalArgumentException("Usuario - setCodEmpresa: codigo negativo");
		this.codEmpresa = codEmpresa;
	}

	public String getTelefone() 
    {
        return this.telefone;
    }

    public void setTelefone(
        String telefone) throws Exception
    {
        if (!Verificadora.isTelefoneValido(telefone))
            throw new Exception("Usuario - setTelefone: telefone invalido");
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
            throw new Exception("Usuario - setCpf: cpf invalido");
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
            throw new Exception("Usuario - setData: data invalida");
        this.data = data;
    }

	public String toString()
	{
		return this.codigo + " " + this.email;
	}

	public int hashCode()
	{
		int ret = 1;
		ret = ret * 2 + new Integer(this.codigo).hashCode();
		ret = ret * 2 + this.email.hashCode();
		ret = ret * 2 + this.nome.hashCode();
		ret = ret * 2 + this.senha.hashCode();
		ret = ret * 2 + new Integer(this.codEmpresa).hashCode();
		ret = ret * 2 + this.telefone.hashCode();
        ret = ret * 2 + this.cpf.hashCode();
        ret = ret * 2 + this.data.hashCode();
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

		Usuario u = (Usuario) obj;

		if (this.codigo != u.codigo)
			return false;
		if (!this.email.equals(u.email))
			return false;
		if (!this.nome.equals(u.nome))
			return false;
		if (!this.senha.equals(u.senha))
			return false;
		if (this.codEmpresa != u.codEmpresa)
			return false;
		if (!this.telefone.equals(u.telefone))
            return false;
        if (!this.cpf.equals(u.cpf))
            return false;
        if (!this.data.equals(u.data))
            return false;

		return true;
	}

	public int compareTo(
		Usuario outro)
	{
		return this.email.compareTo(outro.email);
	}

	public Object clone()
	{
		Usuario ret = null;
		try{ret = new Usuario(this);}
		catch(Exception exc){}
		return ret;
	}
}
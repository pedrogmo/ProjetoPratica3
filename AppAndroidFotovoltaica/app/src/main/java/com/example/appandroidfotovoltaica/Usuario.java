package com.example.appandroidfotovoltaica;

import java.io.Serializable;

public class Usuario 
	implements Cloneable, Comparable<Usuario>, Serializable
{
	private int codigo;
	private String email;
	private String nome;
	private String senha;
	private int codEmpresa;

	public Usuario()
	{
		this.codigo = 0;
		this.email = "";
		this.nome = "";
		this.senha = "";
		this.codEmpresa = 0;
	}

	public Usuario(
		int codigo,
		String email,
		String nome,
		String senha,
		int codEmpresa) throws Exception
	{
		this.setCodigo(codigo);
		this.setEmail(email);
		this.setNome(nome);
		this.setSenha(senha);
		this.setCodEmpresa(codEmpresa);
	}

	public Usuario(
		String email) throws Exception
	{
		this.codigo = 0;
		this.setEmail(email);
		this.nome = "";
		this.senha = "";
		this.codEmpresa = 0;
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
            throw new Exception("Email de usuário inválido");
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
            throw new Exception("Nome de usuário inválido");
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
			throw new NullPointerException("Senha de usuário ausente");
		if (senha.equals(""))
			throw new IllegalArgumentException("Digite a senha de usuário");
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
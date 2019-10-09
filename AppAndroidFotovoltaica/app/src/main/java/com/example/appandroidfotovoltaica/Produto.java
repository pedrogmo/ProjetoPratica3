package com.example.appandroidfotovoltaica;

import java.io.Serializable;

public class Produto 
	implements Cloneable, Comparable<Produto>, Serializable
{
	protected int codigo;
	protected String nome;
	protected float preco;
	protected String descricao;	

	public Produto(
		int pCodigo,
		String pNome,
		float pPreco,
		String pDescricao) throws Exception
	{
		this.setCodigo(pCodigo);
		this.setNome(pNome);
		this.setPreco(pPreco);
		this.setDescricao(pDescricao);
	}

	public Produto(
		String pNome) throws Exception
	{
		this.codigo = 0;
		this.setNome(pNome);
		this.preco = 0.0f;
		this.descricao = "";
	}

	public Produto(
		Produto modelo) throws Exception
	{
		if (modelo == null)
			throw new NullPointerException("Produto - construtor de copia: modelo ausente");
		this.codigo = modelo.codigo;
		this.nome = modelo.nome;
		this.preco = modelo.preco;
		this.descricao = modelo.descricao;
	}

	public int getCodigo()
	{
		return this.codigo;
	}

	protected void setCodigo(
		int pCodigo) throws Exception
	{
		if (pCodigo < 0)
			throw new IllegalArgumentException("Produto - setCodigo : codigo negativo");
		this.codigo = pCodigo;
	}

	public String getNome()
	{
		return this.nome;
	}

	protected void setNome(
		String pNome) throws Exception
	{
		if (pNome == null)
			throw new NullPointerException("Produto - setNome : nome nulo");
		if (pNome.equals(""))
			throw new IllegalArgumentException("Produto - setNome : nome vazio");
		this.nome = pNome;
	}

	public float getPreco()
	{
		return this.preco;
	}

	public void setPreco(
		float pPreco)
	{
		if (pPreco <= 0)
			throw new IllegalArgumentException("Produto - setPreco : preco negativo");
		this.preco = pPreco;
	}

	public String getDescricao()
	{
		return this.descricao;
	}

	public void setDescricao(
		String pDescricao) throws Exception
	{
		if (pDescricao == null)
			throw new NullPointerException("Produto - setDescricao : descricao nula");
		if (pDescricao.equals(""))
			throw new IllegalArgumentException("Produto - setDescricao : descricao vazia");
		this.descricao = pDescricao;
	}

	public String toString()
	{
		return this.codigo + " - " + this.nome;
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
		Produto p = (Produto) obj;
		if (this.codigo != p.codigo)
			return false;
		if (!this.nome.equals(p.nome))
			return false;
		if (this.preco != p.preco)
			return false;
		if (!this.descricao.equals(p.descricao))
			return false;
		return true;
	}

	public int hashCode()
	{
		int ret = 1;
		ret += 2 * ret + new Integer(this.codigo).hashCode();
		ret += 2 * ret + this.nome.hashCode();
		ret += 2 * ret + new Float(this.preco).hashCode();
		ret += 2 * ret + this.descricao.hashCode();
		return ret;		
	}

	public int compareTo(
		Produto outro)
	{
		return this.nome.compareTo(outro.nome);
	}

	public Object clone()
	{
		Produto p = null;
		try{p = new Produto(this);}
		catch(Exception exc){}
		return p;
	}
}
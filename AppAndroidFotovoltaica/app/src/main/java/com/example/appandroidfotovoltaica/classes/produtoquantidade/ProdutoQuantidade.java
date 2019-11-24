package com.example.appandroidfotovoltaica.classes.produtoquantidade;

import com.example.appandroidfotovoltaica.classes.produto.Produto;

import java.io.Serializable;

public class ProdutoQuantidade
    implements Cloneable, Serializable
{
    private Produto produto;
    private int quantidade;

    public ProdutoQuantidade(
        Produto produto,
        int quantidade) throws Exception
    {
        this.setProduto(produto);
        this.setQuantidade(quantidade);
    }

    public ProdutoQuantidade(
        ProdutoQuantidade modelo) throws Exception
    {
        if (modelo == null)
            throw new Exception("ProdutoQuantidade - construtor de copia: modelo ausente");
        this.produto = (Produto) modelo.produto.clone();
        this.quantidade = modelo.quantidade;
    }

    public Produto getProduto()
    {
        return this.produto;
    }

    public void setProduto(
        Produto produto) throws Exception
    {
        if (produto == null)
            throw new Exception("ProdutoQuantidade - setProduto: produto ausente");
        this.produto = produto;
    }

    public int getQuantidade()
    {
        return this.quantidade;
    }

    public void setQuantidade(
        int quantidade) throws Exception
    {
        if (quantidade <= 0)
            throw new Exception("ProdutoQuantidade - setQuantidade: quantidade inválida");
        this.quantidade = quantidade;
    }

    public String toString()
    {
        return this.produto.toString() + " " + this.quantidade;
    }

    public boolean equals(
        Object obj)
    {
        if (this == obj)return  true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        ProdutoQuantidade pq = (ProdutoQuantidade) obj;
        if (!this.produto.equals(pq))
            return false;
        if (this.quantidade != pq.quantidade)
            return false;
        return true;
    }

    public int hashCode()
    {
        int ret = 1;
        ret = ret * 2 + this.produto.hashCode();
        ret = ret * 2 + new Integer(this.quantidade).hashCode();
        return ret;
    }

    public Object clone()
    {
        ProdutoQuantidade pq = null;
        try{pq = new ProdutoQuantidade(this);}
        catch(Exception exc){}
        return pq;
    }
}

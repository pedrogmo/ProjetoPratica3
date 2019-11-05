package com.example.appandroidfotovoltaica.classes.proposta;

public class Proposta
{
    private int codigo;
    private int codUsuario;
    private int codCliente;
    private int codKit;


    public Proposta()
    {
        this.codigo = 0;
        this.codKit = 0;
        this.codCliente = 0;
        this.codUsuario = 0;
    }

    public Proposta(
        int codigo,
        int codKit,
        int codCliente,
        int codUsuario) throws Exception
    {
        this.setCodigo(codigo);
        this.setCodKit(codKit);
        this.setCodCliente(codCliente);
        this.setCodUsuario(codUsuario);
    }

    public Proposta(
            Proposta modelo) throws Exception
    {
        if (modelo == null)
            throw new NullPointerException("Proposta - construtor de copia: modelo nulo");
        this.codigo = modelo.codigo;
        this.codKit = modelo.codKit;
        this.codCliente = modelo.codCliente;
        this.codUsuario = modelo.codUsuario;
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(
            int codigo) throws Exception
    {
        if (codigo < 0)
            throw new IllegalArgumentException("Proposta - setCodigo: código negativo");
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
            throw new IllegalArgumentException("Proposta - setCodKit: código negativo");
        this.codKit = codKit;
    }

    public int getCodUsuario()
    {
        return codUsuario;
    }

    public void setCodUsuario(
            int codUsuario) throws Exception
    {
        if (codUsuario < 0)
            throw new IllegalArgumentException("Proposta - setCodUsuario: código negativo");
        this.codUsuario = codUsuario;
    }

    public int getCodCliente()
    {
        return codCliente;
    }

    public void setCodCliente(
            int codCliente) throws Exception
    {
        if (codCliente < 0)
            throw new IllegalArgumentException("Proposta - setCodCliente: código negativo");
        this.codCliente = codCliente;
    }

    public int hashCode()
    {
        int ret = 1;
        ret = ret * 2 + new Integer(this.codigo).hashCode();
        ret = ret * 2 + new Integer(this.codKit).hashCode();
        ret = ret * 2 + new Integer(this.codCliente).hashCode();
        ret = ret * 2 + new Integer(this.codUsuario).hashCode();
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

        Proposta k = (Proposta) obj;

        if (this.codigo != k.codigo)
            return false;
        if (this.codKit != k.codKit)
            return false;
        if (this.codUsuario != k.codUsuario)
            return false;
        if (this.codCliente != k.codCliente)
            return false;

        return true;
    }

    public Object clone()
    {
        Proposta ret = null;
        try{ret = new Proposta(this);}
        catch(Exception exc){}
        return ret;
    }
}

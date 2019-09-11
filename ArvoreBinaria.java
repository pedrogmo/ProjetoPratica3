import java.lang.reflect.*;

public class ArvoreBinaria<T extends Comparable<T>> 
	implements Cloneable
{
	private class No<T extends Comparable<T>> 
		implements Cloneable, Comparable<No<T>>
	{
		private T dado = null;
		public No<T> esquerda = null, direita = null;
		private int altura = 0;

		public No(
			T info, 
			No<T> esq,
			No<T> dir,
			int alt) throws Exception
		{
			this.setDado(info);
			this.esquerda = esq;
			this.direita = dir;
			this.setAltura(alt);
		}

		public No(
			No<T> modelo)
		{
			if (modelo.dado instanceof Cloneable)
				this.dado = this.cloneDeT(modelo.dado);
			else
				this.dado = modelo.dado;
			if (modelo.esquerda != null)
				this.esquerda = new No<T>(modelo.esquerda);
			if (modelo.direita != null)
				this.direita = new No<T>(modelo.direita);
			this.altura = modelo.altura;
		}

		public Object clone(){
			No<T> ret = null;
			try{ ret = new No<T>(this); }
			catch(Exception exc){}
			return ret;
		}

		public T getDado(){
			if (this.dado instanceof Cloneable)
				return this.cloneDeT(this.dado);
			return this.dado;
		}

		public void setDado(
			T info) throws Exception
		{
			if (info==null)
				throw new NullPointerException("No<T>: Dado nulo");
			if (info instanceof Cloneable)
				this.dado = this.cloneDeT(info);
			else
				this.dado = info;
		}

		public int getAltura(){
			return this.altura;
		}

		public void setAltura(
			int alt) throws Exception
		{
			if (alt < 0)
				throw new IllegalArgumentException("No<T>: Altura negativa");
			this.altura = alt;
		}

		public boolean isFolha(){
			return 
				this.esquerda == null &&
				this.direita == null;
		}

		public String toString(){
			String ret = "";
			if (this.esquerda != null)
				ret += this.esquerda.toString();
			ret += "(" + this.dado.toString() + ")";
			if (this.direita != null)
				ret += this.direita.toString();
			return ret;
		}

		public int hashCode(){
			int ret = 1;

			ret += 2 * this.dado.hashCode();
			ret += 2 * new Integer(this.altura).hashCode();
			if (this.esquerda != null)
				ret += 2 * this.esquerda.hashCode();
			if (this.direita != null)
				ret += 2 * this.direita.hashCode();

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

			No<T> n = (No<T>) obj;

			if(this.altura != n.altura)
				return false;
			if (!this.dado.equals(n.dado))
				return false;

			if ((this.esquerda == null) != (n.esquerda == null) ||
				(this.direita == null) != (n.direita == null))
			{
				return false;
			}

			if (isFolha())
				return true;
			if (this.esquerda != null &&
				this.direita != null)
			{
				return 
					this.esquerda.equals(n.esquerda) &&
					this.direita.equals(n.direita);
			}
			else if(this.esquerda != null)
				return this.esquerda.equals(n.esquerda);
			return this.direita.equals(n.direita);
		}

		public int compareTo(
			No<T> outro)
		{
			return this.compT(this.dado, outro.dado);
		}

		private T cloneDeT(T t)
		{
			T ret = null;
			try{
				Class<?> classe = t.getClass();
				Class<?>[] tiposFormais = null;
				Method metodo = classe.getMethod("clone", tiposFormais);
				Object[] pametrosReais = null;
				ret = (T) metodo.invoke(t, pametrosReais);
			}
			catch(Exception erro){}
			return ret;
		}

		private int compT(
			T t0,
			T t1)
		{
			int ret = 0;
			try
			{
				Class<?> classe = t0.getClass();
				Class<?>[] tiposParametrosFormais = new Class<?>[1];
				tiposParametrosFormais[0] = classe;
				Method metodo = classe.getMethod("compareTo", tiposParametrosFormais);
				Object[] parametrosReais = new Object[1];
				parametrosReais[0] = t1;
				ret = (int) metodo.invoke(t0,parametrosReais);
			}
			catch (Exception erro){}
			return ret;
		}
	}

	private No<T> raiz = null;
	private int qtd = 0;

	public ArvoreBinaria()
	{}

	public ArvoreBinaria(
		ArvoreBinaria modelo)
	{
		this.qtd = modelo.qtd;
		if (modelo.raiz != null)
			this.raiz = new No<T>(modelo.raiz);
	}

	public Object clone(){
		ArvoreBinaria<T> ret = null;
		try{ ret = new ArvoreBinaria<T>(this); }
		catch(Exception erro){}
		return ret;
	}

	public int getQuantidade(){
		return this.qtd;
	}

	public boolean isVazia(){
		return this.raiz == null;
	}

	public void adicionar(
		T info) throws Exception
	{

	}

	public void remover(
		T info) throws Exception
	{

	}

	public T buscar(
		T info) throws Exception
	{
		if (this.isVazia())
			return null;
		No<T> noProcurado = 
			new No<T>(
				info,
				null,
				null,
				0);
		return buscar(
			noProcurado, 
			this.raiz);
	}
	private T buscar(
		No<T> procurado,
		No<T> atual)
	{
		int comp = procurado.compareTo(atual);
		if (comp == 0)
			return atual.getDado();

		else if (comp < 0){
			if (atual.esquerda == null)
				return null;
			return buscar(
				procurado, 
				atual.esquerda);
		}

		if (atual.direita == null)
			return null;
		return buscar(
			procurado, 
			atual.direita);
	}

	public String toString(){
		if (this.isVazia())
			return "Arvore vazia";
		return this.raiz.toString();
	}

	public int hashCode(){
		int ret = 1;

		ret += 2 * new Integer(this.qtd).hashCode();
		if (!this.isVazia())
			ret += 2 * this.raiz.hashCode();

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

		ArvoreBinaria a = (ArvoreBinaria) obj;

		if (this.qtd != a.qtd)
			return false;

		if (this.isVazia())
			return true;

		return this.raiz.equals(a.raiz);
	}
}
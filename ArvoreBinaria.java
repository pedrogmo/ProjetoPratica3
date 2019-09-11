import java.lang.reflect.*;

public class ArvoreBinaria<T extends Comparable<T>> implements Cloneable{
	private class No<T extends Comparable<T>> implements Cloneable, Comparable<T>{
		private T dado;
		public No<T> esquerda, direita;
		private int altura=0;

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
			catch(Exception a){}
			return ret;
		}

		public No(
			T info, 
			No<T> esq,
			No<T> dir,
			int alt) throws Exception
		{
			this.setDado(info);
			this.esquerda = esq;
			this.direita = dir;
			this.altura = alt;
		}

		public No(
			No<T> modelo)
		{
			if (modelo.dado instanceof Cloneable)
				this.dado = cloneDeT(modelo.dado);
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
			catch(Exception exc){  }
			return ret;
		}

		public T getDado(){
			if (this.dado instanceof Cloneable)
				return cloneDeT(this.dado);
			return this.dado;
		}

		public void setDado(
			T info) throws Exception
		{
			if (info==null)
				throw new NullPointerException("Dado nulo");
			if (info instanceof Cloneable)
				this.dado = cloneDeT(info);
			else
				this.dado = info;
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
			return this.dado.compareTo(outro.dado);
		}
	}

	private No<T> raiz;
	private int qtd=0;

	public ArvoreBinaria(){

	}

	public ArvoreBinaria(
		ArvoreBinaria modelo)
	{

	}

	public Object clone(){

	}

	public void adicionar(
		T dado) throws Exception
	{

	}

	public void remover(
		T dado) throws Exception
	{

	}

	public T buscar(
		T dado) throws Exception
	{

	}

	public String toString(){

	}

	public int hashCode(){

	}

	public boolean equals(
		Object obj)
	{

	}
}
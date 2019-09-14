import java.lang.reflect.*;
import java.lang.Math;

public class ArvoreBinaria<T extends Comparable<T>> 
	implements Cloneable
{
	private class No<T extends Comparable<T>> 
		implements Cloneable, Comparable<No<T>>
	{
		private T dado = null;
		public No<T> esquerda = null, direita = null;
		private int altura = 0;
		public boolean removido = false;

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
			T info) throws Exception
		{
			this.setDado(info);			
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
			this.removido = modelo.removido;
		}

		public Object clone()
		{
			No<T> ret = null;
			try{ ret = new No<T>(this); }
			catch(Exception exc){}
			return ret;
		}

		public T getDado()
		{
			if (this.dado instanceof Cloneable)
				return this.cloneDeT(this.dado);
			return this.dado;
		}

		public void setDado(
			T info) throws Exception
		{
			if (info==null)
				throw new NullPointerException("No - setDado: info nula");
			if (info instanceof Cloneable)
				this.dado = this.cloneDeT(info);
			else
				this.dado = info;
		}

		public int getAltura()
		{
			return this.altura;
		}

		public void setAltura(
			int alt) throws Exception
		{
			if (alt < 0)
				throw new IllegalArgumentException("No - setAltura: altura negativa");
			this.altura = alt;
		}

		public boolean isFolha()
		{
			return 
				(this.esquerda == null || this.esquerda.removido && this.esquerda.isFolha()) &&
				(this.direita == null || this.direita.removido && this.direita.isFolha());
		}

		public String toString()
		{
			String ret = "";
			if (this.esquerda != null)
			{
				ret += this.esquerda.toString();
			}
			if (!this.removido)
				ret += "(" + this.dado.toString() + ")";
			if (this.direita != null)
			{
				ret += this.direita.toString();
			}
			return ret;
		}

		public int hashCode()
		{
			int ret = 1;

			ret += 2 * this.dado.hashCode();
			ret += 2 * new Integer(this.altura).hashCode();
			ret += 2 * new Boolean(this.removido).hashCode();

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

			if (this.removido != n.removido)
				return false;
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
			T t0, T t1)
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
		if(!modelo.isVazia())
			this.raiz = new No<T>(modelo.raiz);
	}

	public Object clone()
	{
		ArvoreBinaria<T> ret = null;
		try{ ret = new ArvoreBinaria<T>(this); }
		catch(Exception erro){}
		return ret;
	}

	private int alturaDe(
		No<T> no)
	{
		if (no == null)
			return -1;
		if (no.removido){
			if (no.esquerda != null &&
				no.direita != null)
			{
				return Math.max(
					no.esquerda.getAltura(),
					no.direita.getAltura());
			}
			else if (no.esquerda != null)
				return no.esquerda.getAltura();
			else if (no.direita != null)
				return no.direita.getAltura();
			return -1;
		}
		return no.altura;
	}

	public int getQuantidade()
	{
		return this.qtd;
	}

	public boolean isVazia()
	{
		return 
			this.raiz == null ||
			this.raiz.removido;
	}

	public void adicionar(
		T info) throws Exception
	{
		if (info == null)
			throw new NullPointerException("ArvoreBinaria - adicionar: info nula");
		this.raiz = inserir(
			new No<T>(info),
			this.raiz);
	}

	private No<T> inserir(
		No<T> item,
		No<T> pai) throws Exception
    	{
        	if (pai == null || 
        		pai.removido)
        	{
            		pai = new No<T>(item);
            		++this.qtd;
        	}
        	else
		{
            		if (item.compareTo(pai) < 0)
			{
                		pai.esquerda = inserir(item, pai.esquerda);                
                		if (alturaDe(pai.esquerda) - alturaDe(pai.direita) > 1)
                    			if (item.compareTo(pai.esquerda) < 0)
                        			pai = girarComFilhoEsquerdo(pai);
                    			else
                        			pai = duploComFilhoEsquerdo(pai);
            		}
            		else if (item.compareTo(pai) > 0)
			{
                		pai.direita = inserir(item, pai.direita);                
                		if (alturaDe(pai.direita) - alturaDe(pai.esquerda) > 1)
                    			if (item.compareTo(pai.direita) > 0)
                        			pai = girarComFilhoDireito(pai);
                    			else
                        			pai = duploComFilhoDireito(pai);
            		}
            		pai.setAltura(
            			Math.max(
            				alturaDe(pai.esquerda),
            				alturaDe(pai.direita)) 
            			+ 1);
        	}
        	return pai;
    	}

    	private No<T> girarComFilhoEsquerdo(
    		No<T> no) throws Exception
    	{
        	No<T> temp = no;

        	temp = no.esquerda;
        	no.esquerda = temp.direita;
        	temp.direita = no;

        	no.setAltura(
        		Math.max(
        			alturaDe(no.esquerda), 
        			alturaDe(no.direita))
        		+ 1);

        	temp.setAltura(
        		Math.max(
        			alturaDe(temp.esquerda),
        			alturaDe(no))
        		+ 1);

        	return temp;
    	}

    	private No<T> girarComFilhoDireito(
    		No<T> no) throws Exception
    	{
        	No<T> temp = no;

        	temp = no.direita;
        	no.direita = temp.esquerda;
        	temp.esquerda = no;

        	no.setAltura(
        		Math.max(
        			alturaDe(no.esquerda),
        			alturaDe(no.direita))
        		+ 1);
      	  	temp.setAltura(
      	  		Math.max(
        			alturaDe(temp.direita),
        			alturaDe(no))
        		+ 1);

        	return temp;
    	}

    	private No<T> duploComFilhoEsquerdo(
    		No<T> no) throws Exception
    	{
        	no.esquerda = girarComFilhoDireito(no.esquerda);
        	return girarComFilhoEsquerdo(no);
    	}

    	private No<T> duploComFilhoDireito(
    		No<T> no) throws Exception
    	{
        	no.direita = girarComFilhoEsquerdo(no.direita);
        	return girarComFilhoDireito(no);
    	}

	public void remover(
		T info) throws Exception
	{
		if (!this.isVazia())
			remover(
				new No<T>(info),
				this.raiz);
	}

	private void remover(
		No<T> procurado,
		No<T> atual) throws Exception
	{
		int comp = procurado.compareTo(atual);
		if (comp == 0){
			if (!atual.removido)
				--this.qtd;
			atual.removido = true;			
		}
		else if (comp < 0){
			if (atual.esquerda != null)
			{
				remover(
					procurado,
					atual.esquerda);
			}
		}
		else{
			if (atual.direita != null)
			{
				remover(
					procurado,
					atual.direita);
			}
		}
	}

	public T buscar(
		T info) throws Exception
	{
		if (this.isVazia())
			return null;
		No<T> noProcurado = 
			new No<T>(info);
		return buscar(
			noProcurado, 
			this.raiz);
	}
	
	private T buscar(
		No<T> procurado,
		No<T> atual)
	{		
		int comp = procurado.compareTo(atual);
		if (comp == 0){
			if (atual.removido)
				return null;
			return atual.getDado();
		}

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

	public String toString()
	{
		if (this.isVazia())
			return "Arvore vazia";
		return this.raiz.toString();
	}

	public int hashCode()
	{
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
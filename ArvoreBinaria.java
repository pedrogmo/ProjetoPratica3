public class ArvoreBinaria<T extends Comparable<T>> implements Cloneable{
	private class No<T extends Comparable<T>> implements Cloneable{
		private T dado;
		public No<T> esquerda, direita;

		public No(
			T dado, 
			No<T> esquerda,
			No<T> direita) throws Exception
		{

		}

		public No(
			No<T> modelo)
		{

		}

		public Object clone(){

		}

		public T getDado(){

		}

		public void setDado(
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
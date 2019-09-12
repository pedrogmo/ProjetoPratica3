public class Teste{
	public static void main(
		String[] args)
	{
		try{
			ArvoreBinaria<Integer> ab = new ArvoreBinaria<Integer>();

			ab.adicionar(1);
			ab.adicionar(2);
			ab.adicionar(3);
			ab.adicionar(4);
			ab.adicionar(5);

			ab.remover(1);

			
		}
		catch(
			Exception erro)
		{
			erro.printStackTrace();
		}
	}
}
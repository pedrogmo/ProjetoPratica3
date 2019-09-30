public class Teste
{
	public static void main(
		String[] args)
	{
		try
		{
			ArvoreBinaria<Produto> ab = new ArvoreBinaria<Produto>();
			ab.adicionar(new Produto(
				0,
				"belVita",
				15.50f,
				"Bolacha do MEIRA"
			));
			Produto p = ab.buscar(new Produto("belVita"));
			System.out.println(p.getDescricao());
		}
		catch(
			Exception erro)
		{
			erro.printStackTrace();
		}
	}
}
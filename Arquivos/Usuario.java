import java.io.Serializable;

public class Usuario 
	implements Cloneable, Comparable<Usuario>
{
	private int codigo;
	public int getCodigo()
	{
		return this.codigo;
	}

	public void setCodigo(
		int pCodigo) throws Exception
	{
		if (pCodigo < 0)
			throw new IllegalArgumentException("Usuario - setCodigo: codigo negativo");
		this.codigo = pCodigo;
	}

	private String email;
	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(
		String pEmail) throws Exception
	{
		if (pEmail == null)
			throw new NullPointerException("Usuario - setEmail: email ausente");
		if (pEmail.equals(""))
			throw new IllegalArgumentException("Usuario - setEmail: email inválido");
		this.email = pEmail;
	}

	private String nome;
	public String getNome()
	{
		return this.nome;
	}

	public void setNome(
		String pNome) throws Exception
	{
		if (pNome == null)
			throw new NullPointerException("Usuario - setNome: nome ausente");
		if (pNome.equals(""))
			throw new IllegalArgumentException("Usuario - setNome: nome inválido");
		this.nome = pNome;
	}

	private String senha;
	public String getSenha()
	{
		return this.senha;
	}

	public void setSenha(
		String pSenha) throws Exception
	{
		if (pSenha == null)
			throw new NullPointerException("Usuario - setSenha: senha ausente");
		if (pSenha.equals(""))
			throw new IllegalArgumentException("Usuario - setSenha: senha inválido");
		this.senha = pSenha;
	}

	private int codEmpresa;
	public int getCodEmpresa()
	{
		return this.codEmpresa;
	}

	public void setCodEmpresa(
		int pCodEmpresa) throws Exception
	{
		if (pCodEmpresa < 0)
			throw new IllegalArgumentException("Usuario - setCodEmpresa: codigo negativo");
		this.codEmpresa = pCodEmpresa;
	}

	public Usuario()
	{
		this.codigo = 0;
		this.email = "";
		this.nome = "";
		this.senha = "";
		this.codEmpresa = 0;
	}

	public Usuario(
		String pEmail) throws Exception
	{
		this.codigo = 0;
		this.setEmail(pEmail);
		this.nome = "";
		this.senha = "";
		this.codEmpresa = 0;
	}

	public Usuario(
		int pCodigo,
		String pEmail,
		String pNome,
		String pSenha,
		int pCodEmpresa) throws Exception
	{
		this.setCodigo(pCodigo);
		this.setEmail(pEmail);
		this.setNome(pNome);
		this.setSenha(pSenha);
		this.setCodEmpresa(pCodEmpresa);
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

	public String toString()
	{
		return this.codigo + " " + this.nome;
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
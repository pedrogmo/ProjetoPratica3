package com.example.appandroidfotovoltaica.classes.verificadora;

public class Verificadora
{
	public static boolean isNomeValido(
        String nome)
    {
        if (nome == null)
            return false;
        if (nome.length() <= 0 || nome.length() > 50)
            return false;

        for(char c : nome.toCharArray())
            if (!Character.isLetter(c) &&  c != ' ')
                return false;

        return true;
    }

    public static boolean isEmailValido(
        String email)
    {
        if (email == null)
            return false;
        if (email.length() <= 0)
            return false;
        if (!email.contains("@"))
            return false;
        if (!email.contains(".com"))
            return false;

        return true;
    }

    public static boolean isTelefoneValido(
        String telefone)
    {
        if (telefone == null)
            return false;
        if (telefone.length() < 9)
            return false;
        for (int i = 0; i < telefone.length(); i++)
            if (!Character.isDigit(telefone.charAt(i)))
                return false;

        return true;
    }

    public static boolean isCpfValido(
        String cpf)
    {
        if (cpf == null)
            return false;
        if(!cpf.matches("[0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}[-]?[0-9]{2}"))
            return false;
        return true;
    }
    public static boolean isCNPJValido(
            String cnpj)
    {
        if (cnpj == null)
            return false;
        if(!cnpj.matches("[0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}[/]?[0-9]{4}[-]?[0-9]{2}"))
            return false;
        return true;
    }

    public static boolean isDataValida(
        String data)
    {
        if (data == null)
            return false;
        return data.matches("[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]");
    }

    public static boolean isCnpjValido(
        String cnpj)
    {
        if (cnpj == null)
            return false;
        return cnpj.matches("[0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9]/[0-9][0-9][0-9][0-9]-[0-9][0-9]");
    }
    public static boolean isSenhaValida(String senha1)
    {
        if (senha1.equals("") || senha1.contains(" ") || senha1.length() < 6 || senha1.length() > 22)
            return false;
        return true;
    }
    public static boolean isSenhaIguais(String senha1, String senha2)
    {
        return senha1.equals(senha2);
    }
    public static boolean isQtdValida(int qtd)
    {
        if (qtd >= 9999999 || qtd <= 0)
        return false;

        return true;
    }
}
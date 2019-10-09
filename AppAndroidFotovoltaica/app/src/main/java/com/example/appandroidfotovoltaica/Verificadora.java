package com.example.appandroidfotovoltaica;

public class Verificadora
{
	public static boolean isNomeValido(
        String nome)
    {
        if (nome == null)
            return false;
        if (nome.length() <= 0)
            return false;

        for (int i = 0; i < nome.length(); i++)
            if (!Character.isLetter(nome.charAt(i)))
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
        if(!cpf.matches("[0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}[-]?[0-9]{2}"))
            return false;
        return true;
    }
}
package com.example.appandroidfotovoltaica;

public class Criptografia
{
    private static char CHAVE = 0x3E;

    public static String criptografar(
        String string)
    {
        String ret = "";
        for(char c : string.toCharArray())
            ret += c ^ CHAVE;
        return ret;
    }
}

package com.example.appandroidfotovoltaica;

public class Criptografia
{
    public static String criptografar(
        String string)
    {
        String ret = "";
        for(char c : string.toCharArray())
            ret += ~c;
        return ret;
    }
}

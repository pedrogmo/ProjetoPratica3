package com.example.appandroidfotovoltaica.classes.criptografia;

import java.security.MessageDigest;

public class Criptografia
{
    private static MessageDigest messageDigest;

    public static String criptografar(
        String str)
    {
        try
        {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();

            byte[] digested = messageDigest.digest(str.getBytes());

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; i++)
                sb.append(Integer.toHexString(0xff & digested[i]));

            return sb.toString();
        }
        catch(Exception exc)
        {
            return "";
        }
    }
}

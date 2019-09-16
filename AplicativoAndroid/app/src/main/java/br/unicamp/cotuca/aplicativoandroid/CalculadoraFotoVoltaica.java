package br.unicamp.cotuca.aplicativoandroid;

import java.text.DecimalFormat;

public class CalculadoraFotoVoltaica {

    public static double numeroPlacas(double media, float irradiacao, double watts)
    {
        double numeroPlacas = ((((1000 * media)/30)/irradiacao) / 0.80)/watts;
        return Math.round(Math.round(numeroPlacas));
    }
    public static double inversor(double media, double irradiacao)
    {
        DecimalFormat f = new DecimalFormat("##.00");
        double inversor =(((media)/30)/(irradiacao * 0.80));

        return  Double.parseDouble(String.format("%.2f",inversor));
    }
    public static double inversorMais(double media, double irradiacao)
    {
        return  inversor(media, irradiacao) * 1.2;
    }
    public static double inversorMenos(double media, double irradiacao)
    {
        double valorInversor = inversor(media,irradiacao);//para não chamar duas vezes a função
        return  valorInversor - 0.2*valorInversor;
    }
}
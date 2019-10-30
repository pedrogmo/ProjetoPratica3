package com.example.appandroidfotovoltaica.classes.categoria;

import com.example.appandroidfotovoltaica.classes.produto.Produto;
import com.example.appandroidfotovoltaica.classes.produto.cabo.Cabo;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.bombasolar.BombaSolar;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.inversor.Inversor;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.modulo.Modulo;
import com.example.appandroidfotovoltaica.classes.produto.fixacao.Fixacao;
import com.example.appandroidfotovoltaica.classes.produto.stringbox.StringBox;

public class Categoria
{
    public static final String[] OPCOES_SPINNER =
    {
        "Módulo", "Inversor", "StringBox", "Fixação", "BombaSolar", "Cabo"
    };

    public static Class<? extends Produto> getClasse(
        int indiceCategoria)
    {
        if (indiceCategoria == 0)
            return Modulo.class;
        else if (indiceCategoria == 1)
            return Inversor.class;
        else if (indiceCategoria == 2)
            return StringBox.class;
        else if (indiceCategoria == 3)
            return Fixacao.class;
        else if (indiceCategoria == 4)
            return BombaSolar.class;
        else
            return Cabo.class;
    }
}

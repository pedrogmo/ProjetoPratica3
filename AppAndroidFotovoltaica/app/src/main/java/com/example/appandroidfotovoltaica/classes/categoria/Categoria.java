package com.example.appandroidfotovoltaica.classes.categoria;

import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.produto.Produto;
import com.example.appandroidfotovoltaica.classes.produto.cabo.Cabo;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.bombasolar.BombaSolar;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.inversor.Inversor;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.modulo.Modulo;
import com.example.appandroidfotovoltaica.classes.produto.fixacao.Fixacao;
import com.example.appandroidfotovoltaica.classes.produto.stringbox.StringBox;

public class Categoria
{
    public static final String[] NOMES_CATEGORIAS =
    {
        "Módulo", "Inversor", "StringBox", "Fixação", "BombaSolar", "Cabo"
    };

    public static final String[] ROTAS_GET_PRODUTO =
    {
        Enderecos.GET_MODULO,
        Enderecos.GET_INVERSOR,
        Enderecos.GET_STRINGBOX,
        Enderecos.GET_FIXACAO,
        Enderecos.GET_BOMBASOLAR,
        Enderecos.GET_CABO,
    };

    public static final String[] ROTAS_POST_PRODUTO =
    {
        Enderecos.POST_MODULO,
        Enderecos.POST_INVERSOR,
        Enderecos.POST_STRINGBOX,
        Enderecos.POST_FIXACAO,
        Enderecos.POST_BOMBASOLAR,
        Enderecos.POST_CABO,
    };

    public static final String[] ROTAS_PATCH_PRODUTO =
    {
        Enderecos.PATCH_MODULO,
        Enderecos.PATCH_INVERSOR,
        Enderecos.PATCH_STRINGBOX,
        Enderecos.PATCH_FIXACAO,
        Enderecos.PATCH_BOMBASOLAR,
        Enderecos.PATCH_CABO,
    };

    public static final String[] ROTAS_DELETE_PRODUTO =
    {
        Enderecos.DELETE_MODULO,
        Enderecos.DELETE_INVERSOR,
        Enderecos.DELETE_STRINGBOX,
        Enderecos.DELETE_FIXACAO,
        Enderecos.DELETE_BOMBASOLAR,
        Enderecos.DELETE_CABO,
    };

    public static final String[] ROTAS_GET_KITPRODUTO =
    {
        Enderecos.GET_KITMODULO,
        Enderecos.GET_KITINVERSOR,
        Enderecos.GET_KITSTRINGBOX,
        Enderecos.GET_KITFIXACAO,
        Enderecos.GET_KITBOMBASOLAR,
        Enderecos.GET_KITCABO,
    };

    public static final String[] ROTAS_POST_KITPRODUTO =
    {
        Enderecos.POST_KITMODULO,
        Enderecos.POST_KITINVERSOR,
        Enderecos.POST_KITSTRINGBOX,
        Enderecos.POST_KITFIXACAO,
        Enderecos.POST_KITBOMBASOLAR,
        Enderecos.POST_KITCABO,
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

    public static int getIndice(
        Class<? extends Produto> categoria)
    {
        if (categoria == Modulo.class)
            return 0;
        if (categoria == Inversor.class)
            return 1;
        if (categoria == StringBox.class)
            return 2;
        if (categoria == Fixacao.class)
            return 3;
        if (categoria == BombaSolar.class)
            return 4;
        if (categoria == Cabo.class)
            return 5;
        return -1;
    }

    public static String getCategoria(
        Produto p)
    {
        Class<? extends Produto> categoria = p.getClass();

        if (categoria == Modulo.class)
            return "Modulo";
        if (categoria == Inversor.class)
            return "Inversor";
        if (categoria == StringBox.class)
            return "StringBox";
        if (categoria == Fixacao.class)
            return "Fixacao";
        if (categoria == BombaSolar.class)
            return "BombaSolar";
        if (categoria == Cabo.class)
            return "Cabo";

        return "";
    }
}

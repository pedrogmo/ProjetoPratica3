package com.example.appandroidfotovoltaica.classes.kitfotovoltaico;

import com.example.appandroidfotovoltaica.classes.produto.Produto;

import java.util.ArrayList;

public class KitFotovoltaico extends ArrayList<Produto>
{
    public KitFotovoltaico()
    {
        super();
    }

    public KitFotovoltaico(
        KitFotovoltaico modelo)
    {
        super(modelo);
    }

    public Object clone()
    {
        KitFotovoltaico ret = new KitFotovoltaico(this);
        return ret;
    }
}

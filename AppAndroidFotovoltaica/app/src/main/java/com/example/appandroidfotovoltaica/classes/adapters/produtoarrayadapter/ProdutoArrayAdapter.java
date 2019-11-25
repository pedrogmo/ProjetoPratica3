package com.example.appandroidfotovoltaica.classes.adapters.produtoarrayadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.appandroidfotovoltaica.classes.produto.Produto;
import com.example.appandroidfotovoltaica.R;
import java.util.ArrayList;

public class ProdutoArrayAdapter extends ArrayAdapter<Produto>
{
    private ArrayList<Produto> listaProdutos;
    private Context context;

    public ProdutoArrayAdapter(
            Context context,
            ArrayList<Produto> listaProdutos)
    {
        super(
            context,
            0,
            listaProdutos);

        this.context = context;
        this.listaProdutos = listaProdutos;
    }

    @Override
    public View getView(
            int position,
            View viewAtual,
            ViewGroup parent)
    {
        Produto produto = this.listaProdutos.get(position);

        if(viewAtual == null)
        {
            viewAtual =
                    LayoutInflater.from(context).inflate(R.layout.lista_produtos, null);

            TextView tvNomeProduto =
                    (TextView) viewAtual.findViewById(R.id.tvNomeProduto);
            tvNomeProduto.setText(produto.getNome());

            TextView tvPrecoProduto =
                    (TextView) viewAtual.findViewById(R.id.tvPrecoProduto);
            tvPrecoProduto.setText("R$" + produto.getPreco());
        }
        return viewAtual;
    }
}
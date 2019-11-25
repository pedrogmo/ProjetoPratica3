package com.example.appandroidfotovoltaica.classes.adapters.produtoquantidadearrayadpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.categoria.Categoria;
import com.example.appandroidfotovoltaica.classes.produtoquantidade.ProdutoQuantidade;
import java.util.ArrayList;

public class ProdutoQuantidadeArrayAdapter extends ArrayAdapter<ProdutoQuantidade>
{
    private ArrayList<ProdutoQuantidade> listaPQ;
    private Context context;

    public ProdutoQuantidadeArrayAdapter(
            Context context,
            ArrayList<ProdutoQuantidade> listaPQ)
    {
        super(
            context,
            0,
            listaPQ);

        this.context = context;
        this.listaPQ = listaPQ;
    }

    @Override
    public View getView(
            int position,
            View viewAtual,
            ViewGroup parent)
    {
        ProdutoQuantidade pq = this.listaPQ.get(position);

        if(viewAtual == null)
        {
            viewAtual =
                    LayoutInflater.from(context).inflate(R.layout.lista_produtosquantidade, null);

            String categoria = Categoria.getCategoria(pq.getProduto());

            TextView tvNomeProduto =
                    (TextView) viewAtual.findViewById(R.id.tvNomeProdutoKit);
            tvNomeProduto.setText(categoria + ": " + pq.getProduto().getNome());

            TextView tvQtdProduto =
                    (TextView) viewAtual.findViewById(R.id.tvQuantidadeProdutoKit);
            tvQtdProduto.setText(pq.getQuantidade() + "");

            TextView tvPrecoProduto =
                    (TextView) viewAtual.findViewById(R.id.tvPrecoProdutoKit);
            tvPrecoProduto.setText("R$" + pq.getProduto().getPreco() + " por unidade");
        }
        return viewAtual;
    }
}
package com.example.appandroidfotovoltaica.ui.clientes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appandroidfotovoltaica.classes.cliente.Cliente;
import com.example.appandroidfotovoltaica.R;

import java.util.ArrayList;

public class ClienteArrayAdapter extends ArrayAdapter<Cliente> {

    private ArrayList<Cliente> listaClientes;
    private Context context;

    public ClienteArrayAdapter(
            Context context,
            ArrayList<Cliente> listaClientes)
    {
        super(
                context,
                0,
                listaClientes);

        this.context = context;
        this.listaClientes = listaClientes;
    }

    @Override
    public View getView(
            int position,
            View viewAtual,
            ViewGroup parent)
    {
        Cliente cliente = listaClientes.get(position);

        if(viewAtual==null)
        {
            viewAtual =
                    LayoutInflater.from(context).inflate(R.layout.lista_clientes, null);

            TextView tvEmailCliente =
                    (TextView) viewAtual.findViewById(R.id.tvEmailCliente);
            tvEmailCliente.setText(cliente.getEmail());

            TextView tvNomeCliente =
                    (TextView) viewAtual.findViewById(R.id.tvNomeCliente);
            tvNomeCliente.setText(cliente.getNome());
        }
        return viewAtual;
    }
}
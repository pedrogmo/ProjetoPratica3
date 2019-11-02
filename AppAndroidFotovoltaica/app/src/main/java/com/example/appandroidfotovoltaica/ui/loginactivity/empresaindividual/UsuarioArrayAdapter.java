package com.example.appandroidfotovoltaica.ui.loginactivity.empresaindividual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.usuario.Usuario;

import java.util.ArrayList;

public class UsuarioArrayAdapter extends ArrayAdapter<Usuario>
{
    private ArrayList<Usuario> listaUsuarios;
    private Context context;

    public UsuarioArrayAdapter(
        Context context,
        ArrayList<Usuario> listaUsuarios)
    {
        super(
                context,
                0,
                listaUsuarios);

        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @Override
    public View getView(
        int position,
        View viewAtual,
        ViewGroup parent)
    {
        Usuario usuario = listaUsuarios.get(position);

        if(viewAtual==null)
        {
            viewAtual =
                    LayoutInflater.from(context).inflate(R.layout.lista_pessoas, null);

            TextView tvEmailUsuario =
                    (TextView) viewAtual.findViewById(R.id.tvEmailPessoa);
            tvEmailUsuario.setText(usuario.getEmail());

            TextView tvNomeUsuario =
                    (TextView) viewAtual.findViewById(R.id.tvNomePessoa);
            tvNomeUsuario.setText(usuario.getNome());
        }
        return viewAtual;
    }
}

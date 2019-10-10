package com.example.appandroidfotovoltaica.ui.principalClientes;

import com.example.appandroidfotovoltaica.Cliente;
import com.example.appandroidfotovoltaica.ClienteWS;
import com.example.appandroidfotovoltaica.Enderecos;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.ui.adicionarcliente.AdicionarClienteFragment;
import com.example.appandroidfotovoltaica.ui.adicionarcliente.AdicionarClienteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PrincipalClientesFragment extends Fragment{

    private PrincipalClientesViewModel galleryViewModel;

    private EditText etBuscarCliente;
    private ListView lvListaClientes;
    private FloatingActionButton fabNovoCliente;
    private ArrayList<Cliente> listaClientes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(PrincipalClientesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_clientes, container, false);

        etBuscarCliente = root.findViewById(R.id.etBuscarCliente);
        lvListaClientes = root.findViewById(R.id.lvListaClientes);
        fabNovoCliente = root.findViewById(R.id.fabNovoCliente);
        TextView tvteste = root.findViewById(R.id.tvteste);
        this.listaClientes = new ArrayList<Cliente>();

        try
        {
             Cliente[] cl = (Cliente[]) ClienteWS.getObjeto(Cliente[].class, Enderecos.GET_CLIENTES);
             /*while (cl[0] == null)
             {
                 TextView tvteste = root.findViewById(R.id.tvteste);
                 tvteste.append("a");
             }
             for(Cliente c : cl)
                 this.listaClientes.add(c);*/
        }
        catch (Exception e)
        {
            tvteste.setText(e.toString());
            //Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }



        fabNovoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_principalClientes, new AdicionarClienteFragment());
                fragmentTransaction.commit();
                fabNovoCliente.hide();
            }
        });

        return root;
    }
    private class MyTask extends AsyncTask<String,String,Cliente[]>
    {

        @Override
        protected Cliente[] doInBackground(String... param) {//enquanto a thread está funcionando
            Cliente[] cl = null;
            try{
                cl = (Cliente[]) ClienteWS.getObjeto(Cliente[].class, Enderecos.GET_CLIENTES);
                lvListaClientes.setAdapter(
                        new ClienteArrayAdapter(
                                getActivity().getApplicationContext(),
                                listaClientes)
                );
            }
            catch(Exception e)
            {
                Toast.makeText(getActivity().getApplicationContext(),"Erro ao buscar clientes", Toast.LENGTH_SHORT).show();
            }
            return cl;
        }


    }
}

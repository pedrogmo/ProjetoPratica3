package com.example.appandroidfotovoltaica.ui.principalClientes;

import com.android.volley.toolbox.StringRequest;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
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
        this.listaClientes = new ArrayList<Cliente>();

        exibirClientes();




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
    private void exibirClientes()
    {
        StringRequest s = null;
        try
        {
            final String url = Enderecos.GET_CLIENTES;
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            s = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray clientesJSON = new JSONArray(response);

                                for (int i = 0; i < clientesJSON.length(); i++)
                                {
                                    JSONObject c = clientesJSON.getJSONObject(i);
                                    Cliente cl = new Cliente(c.getInt("codigo"), c.getString("nome"),
                                            c.getString("email"), c.getString("telefone"),
                                            c.getString("cpf"), c.getString("data"));
                                    listaClientes.add(cl);
                                }
                            }
                            catch (Exception e){
                                Toast.makeText(getActivity().getApplicationContext(),"Não foi possível buscar os clientes", Toast.LENGTH_SHORT);
                            }

                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity().getApplicationContext(), "Erro ao buscar clientes", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            queue.add(s);
        }
        catch (Exception e)
        {
        }
        lvListaClientes.setAdapter(new ClienteArrayAdapter(getActivity().getApplicationContext(), listaClientes));
    }
    /*private void buscarDados(String s) {
        MyTask task = new MyTask();
        task.execute(s);
    }
    private class MyTask extends AsyncTask<String,Void,Cliente[]>
    {

        @Override
        protected Cliente[] doInBackground(String... param) {//enquanto a thread está funcionando
            Cliente[] cl = null;
            try{
                cl = (Cliente[]) ClienteWS.getObjeto(Cliente[].class, Enderecos.GET_CLIENTES);
            }
            catch(Exception e)
            {
                Toast.makeText(getActivity().getApplicationContext(),"Erro ao buscar clientes", Toast.LENGTH_SHORT).show();
            }
            return cl;
        }

        @Override
        protected void onPostExecute(Cliente[] cl) {
            for (int i = 0; i < cl.length; i++)
            listaClientes.set(i, cl[i]);

            lvListaClientes.setAdapter(
                    new ClienteArrayAdapter(
                            getActivity().getApplicationContext(),
                            listaClientes));

        }

    }*/
}

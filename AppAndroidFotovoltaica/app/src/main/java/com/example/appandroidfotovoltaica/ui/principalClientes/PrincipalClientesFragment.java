package com.example.appandroidfotovoltaica.ui.principalClientes;

import com.example.appandroidfotovoltaica.ClienteWS;
import com.example.appandroidfotovoltaica.Enderecos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.HashMap;

public class PrincipalClientesFragment extends Fragment{

    private PrincipalClientesViewModel galleryViewModel;

    private EditText etBuscarCliente;
    private ListView lvListaClientes;
    private FloatingActionButton fabNovoCliente;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(PrincipalClientesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_clientes, container, false);

        etBuscarCliente = root.findViewById(R.id.etBuscarCliente);
        lvListaClientes = root.findViewById(R.id.lvListaClientes);
        fabNovoCliente = root.findViewById(R.id.fabNovoCliente);


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
}

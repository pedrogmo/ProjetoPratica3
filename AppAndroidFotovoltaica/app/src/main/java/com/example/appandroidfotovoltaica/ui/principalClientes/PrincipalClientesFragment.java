package com.example.appandroidfotovoltaica.ui.principalClientes;

import com.example.appandroidfotovoltaica.Cliente;
import com.example.appandroidfotovoltaica.Enderecos;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.appandroidfotovoltaica.MainActivity;
import com.example.appandroidfotovoltaica.MyTask;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.ui.adicionarcliente.AdicionarClienteFragment;
import com.example.appandroidfotovoltaica.ui.principalClientes.clienteIndividual.ClienteIndividualFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PrincipalClientesFragment extends Fragment {

    private PrincipalClientesViewModel galleryViewModel;

    private EditText etBuscarCliente;
    private ListView lvListaClientes;
    private FloatingActionButton fabNovoCliente;
    private Cliente[] clientesTotal;
    private ArrayList<Cliente> clientesBusca;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(PrincipalClientesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_principalclientes, container, false);

        etBuscarCliente = root.findViewById(R.id.etBuscarCliente);
        lvListaClientes = root.findViewById(R.id.lvListaClientes);
        fabNovoCliente = root.findViewById(R.id.fabNovoCliente);
        this.clientesBusca = new ArrayList<Cliente>();


        MyTask task = new MyTask(Cliente[].class);
        task.execute(Enderecos.GET_CLIENTES + "/" + ((MainActivity)getActivity()).getUsuario().getCodEmpresa());
        while (task.isTrabalhando()) ;
        this.clientesTotal = (Cliente[]) task.getDados();
        for (Cliente c : this.clientesTotal)
            this.clientesBusca.add(c);

        this.lvListaClientes.setAdapter(new ClienteArrayAdapter(
                getActivity().getApplicationContext(), this.clientesBusca
        ));

        fabNovoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_principalclientes, new AdicionarClienteFragment());
                fragmentTransaction.commit();
                fabNovoCliente.hide();
            }
        });

        lvListaClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    AdapterView<?> adapterView,
                    View view,
                    int i,
                    long l) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("cliente", clientesBusca.get(i));
                ClienteIndividualFragment fragment = new ClienteIndividualFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_principalclientes, fragment);
                fragmentTransaction.commit();
            }
        });

        etBuscarCliente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                clientesBusca.clear();
                for(Cliente c : clientesTotal)
                    if (c.getNome().contains(charSequence))
                        clientesBusca.add(c);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return root;
    }
}
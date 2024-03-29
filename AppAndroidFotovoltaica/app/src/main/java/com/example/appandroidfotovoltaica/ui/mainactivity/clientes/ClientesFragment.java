package com.example.appandroidfotovoltaica.ui.mainactivity.clientes;

import com.example.appandroidfotovoltaica.classes.adapters.clientearrayadapter.ClienteArrayAdapter;
import com.example.appandroidfotovoltaica.classes.cliente.Cliente;
import com.example.appandroidfotovoltaica.classes.constantesdetransicao.ConstantesDeTransicao;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appandroidfotovoltaica.ui.mainactivity.MainActivity;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.ui.mainactivity.clientes.adicionarcliente.AdicionarClienteFragment;
import com.example.appandroidfotovoltaica.ui.mainactivity.clientes.clienteIndividual.ClienteIndividualFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ClientesFragment extends Fragment {

    private EditText etBuscarCliente;
    private ListView lvListaClientes;
    private FloatingActionButton fabNovoCliente;
    private Cliente[] clientesTotal;
    private ArrayList<Cliente> clientesBusca;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_clientes, container, false);

        etBuscarCliente = root.findViewById(R.id.etBuscarCliente);
        lvListaClientes = root.findViewById(R.id.lvListaClientes);
        fabNovoCliente = root.findViewById(R.id.fabNovoCliente);

        fabNovoCliente.show();
        this.clientesBusca = new ArrayList<Cliente>();



        MyTask task = new MyTask(Cliente[].class);
        task.execute(Enderecos.GET_CLIENTE + "/" + ((MainActivity)getActivity()).getUsuario().getCodEmpresa());
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
                fragmentTransaction.replace(R.id.fragment_clientes, new AdicionarClienteFragment(), ConstantesDeTransicao.F_ADICIONAR_CLIENTE).addToBackStack(ConstantesDeTransicao.M_ADICIONAR_CLIENTE).commit();
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
                fragmentTransaction.replace(R.id.fragment_clientes, fragment, ConstantesDeTransicao.F_CLIENTE_INDIVIDUAL).addToBackStack(ConstantesDeTransicao.M_CLIENTE_INDIVIDUAL).commit();
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
                    if (c.getNome().toUpperCase().contains(String.valueOf(charSequence).toUpperCase()))
                    {
                        clientesBusca.add(c);
                    }

                lvListaClientes.setAdapter(new ClienteArrayAdapter(
                        getActivity().getApplicationContext(), clientesBusca
                ));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return root;
    }
}
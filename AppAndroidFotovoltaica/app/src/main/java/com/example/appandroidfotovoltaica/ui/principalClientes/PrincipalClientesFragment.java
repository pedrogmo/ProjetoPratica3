package com.example.appandroidfotovoltaica.ui.principalClientes;

import com.example.appandroidfotovoltaica.Cliente;
import com.example.appandroidfotovoltaica.Enderecos;

import android.os.Bundle;
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
    private ArrayList<Cliente> listaClientes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(PrincipalClientesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_principalclientes, container, false);

        etBuscarCliente = root.findViewById(R.id.etBuscarCliente);
        lvListaClientes = root.findViewById(R.id.lvListaClientes);
        fabNovoCliente = root.findViewById(R.id.fabNovoCliente);
        this.listaClientes = new ArrayList<Cliente>();


            MyTask task = new MyTask(Cliente[].class);
            //MyTask<Cliente[]> task = new MyTask<Cliente[]>(Cliente[].class);
            task.execute(Enderecos.GET_CLIENTES + "/2");
            while (task.isTrabalhando()) ;
            for (Cliente c : (Cliente[]) task.getDados())
                this.listaClientes.add(c);

            this.lvListaClientes.setAdapter(new ClienteArrayAdapter(
                    getActivity().getApplicationContext(), this.listaClientes
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
                bundle.putSerializable("cliente", listaClientes.get(i));
                ClienteIndividualFragment fragment = new ClienteIndividualFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_principalclientes, fragment);
                fragmentTransaction.commit();
            }
        });


        return root;
    }
}
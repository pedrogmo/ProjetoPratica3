package com.example.appandroidfotovoltaica.ui.mainactivity.clientes.clienteIndividual;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appandroidfotovoltaica.classes.cliente.Cliente;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.verificadora.mensagenserro.mensagenserrocliente.MensagensErroCliente;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.ui.mainactivity.clientes.ClientesFragment;

import java.util.HashMap;
import java.util.Map;

public class ClienteIndividualFragment extends Fragment {

    private ClienteIndividualViewModel mViewModel;
    private Cliente clienteAtual;
    private EditText
        etEmail,
        etNome,
        etTelefone,
        etData,
        etCpf;
    private Button
        btnAlterar,
        btnExcluir;
    private TextView
        tvExceptionNome,
        tvExceptionData,
        tvExceptionEmail,
        tvExceptionTelefone,
        tvExceptionCpf;
    private MensagensErroCliente mensagens;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(ClienteIndividualViewModel.class);
        View root = inflater.inflate(R.layout.fragment_clienteindividual, container, false);

        Bundle bundle = getArguments();
        this.clienteAtual = (Cliente) bundle.getSerializable("cliente");

        this.etEmail = (EditText) root.findViewById(R.id.etEmailClienteAlt);
        this.etNome = (EditText) root.findViewById(R.id.etNomeClienteAlt);
        this.etTelefone = (EditText) root.findViewById(R.id.etTelefoneClienteAlt);
        this.etData = (EditText) root.findViewById(R.id.etDataClienteAlt);
        this.etCpf = (EditText) root.findViewById(R.id.etCpfClienteAlt);

        this.etEmail.setText(this.clienteAtual.getEmail());
        this.etNome.setText(this.clienteAtual.getNome());
        this.etTelefone.setText(this.clienteAtual.getTelefone());
        this.etData.setText(this.clienteAtual.getData());
        this.etCpf.setText(this.clienteAtual.getCpf());

        this.tvExceptionNome = (TextView) root.findViewById(R.id.tvExceptionNomeClienteAlt);
        this.tvExceptionData = (TextView) root.findViewById(R.id.tvExceptionDataClienteAlt);
        this.tvExceptionEmail = (TextView) root.findViewById(R.id.tvExceptionEmailClienteAlt);
        this.tvExceptionTelefone = (TextView) root.findViewById(R.id.tvExceptionTelefoneClienteAlt);
        this.tvExceptionCpf = (TextView) root.findViewById(R.id.tvExceptionCpfClienteAlt);

        this.btnAlterar = (Button) root.findViewById(R.id.btnAlterarCliente);
        this.btnExcluir = (Button) root.findViewById(R.id.btnExcluirCliente);

        this.mensagens = new MensagensErroCliente(
            this.tvExceptionNome,
            this.tvExceptionData,
            this.tvExceptionEmail,
            this.tvExceptionTelefone,
            this.tvExceptionCpf);

        this.btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());

                final String nome = etNome.getText().toString().trim();
                final String data = etData.getText().toString().trim();
                final String email = etEmail.getText().toString().trim();
                final String telefone = etTelefone.getText().toString().trim();
                final String cpf = etCpf.getText().toString().trim();

                if (mensagens.teveMensagensDeErro(nome, data, email, telefone, cpf))
                    return;

                MyTask task = new MyTask(Cliente[].class);
                task.execute(Enderecos.GET_CLIENTE + "_email/" + email);
                while (task.isTrabalhando()) ;
                Cliente[] resultClientes = (Cliente[]) task.getDados();
                if (resultClientes.length > 0 &&
                    !resultClientes[0].equals(clienteAtual))
                {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Email já cadastrado",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                StringRequest putRequest = new StringRequest(
                    Request.Method.PATCH,
                    Enderecos.PATCH_CLIENTE + "/" + clienteAtual.getCodigo(),
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Cliente alterado",
                                    Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Erro ao alterar",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                )
                {

                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("email", email);
                        params.put("nome", nome);
                        params.put("telefone", telefone);
                        params.put("data", data);
                        params.put("cpf", cpf);
                        return params;
                    }

                };

                QUEUE.add(putRequest);
            }
        });

        this.btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Deseja mesmo excluir?.");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());

                                StringRequest dr = new StringRequest(
                                        Request.Method.DELETE,
                                        Enderecos.DELETE_CLIENTE + "/" + clienteAtual.getCodigo(),
                                        new Response.Listener<String>()
                                        {
                                            @Override
                                            public void onResponse(String response) {
                                                // response
                                                Toast.makeText(
                                                    getActivity().getApplicationContext(),
                                                    "Cliente excluído",
                                                    Toast.LENGTH_SHORT).show();
                                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                                fragmentTransaction.replace(R.id.fragment_clienteindividual, new ClientesFragment());
                                                fragmentTransaction.commit();
                                            }
                                        },
                                        new Response.ErrorListener()
                                        {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(
                                                        getActivity().getApplicationContext(),
                                                        "Erro ao excluir",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                );
                                QUEUE.add(dr);

                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "Não",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        return root;
    }
}

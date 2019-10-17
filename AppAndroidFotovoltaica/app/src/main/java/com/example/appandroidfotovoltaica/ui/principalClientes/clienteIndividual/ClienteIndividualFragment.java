package com.example.appandroidfotovoltaica.ui.principalClientes.clienteIndividual;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.example.appandroidfotovoltaica.Cliente;
import com.example.appandroidfotovoltaica.Enderecos;
import com.example.appandroidfotovoltaica.MyTask;
import com.example.appandroidfotovoltaica.R;

import java.util.HashMap;
import java.util.Map;

public class ClienteIndividualFragment extends Fragment {

    private ClienteIndividualViewModel mViewModel;
    private Cliente clienteAtual;

    private TextView tvCodigo;
    private EditText
        etEmail,
        etNome,
        etTelefone,
        etData,
        etCpf;
    private Button
        btnAlterar,
        btnExcluir;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(ClienteIndividualViewModel.class);
        View root = inflater.inflate(R.layout.cliente_individual_fragment, container, false);

        Bundle bundle = getArguments();
        this.clienteAtual = (Cliente) bundle.getSerializable("cliente");

        this.tvCodigo = (TextView) root.findViewById(R.id.tvCodigo);
        this.etEmail = (EditText) root.findViewById(R.id.etEmail);
        this.etNome = (EditText) root.findViewById(R.id.etNome);
        this.etTelefone = (EditText) root.findViewById(R.id.etTelefone);
        this.etData = (EditText) root.findViewById(R.id.etData);
        this.etCpf = (EditText) root.findViewById(R.id.etCpf);

        this.tvCodigo.setText(
                this.tvCodigo.getText().toString() + this.clienteAtual.getCodigo());
        this.etEmail.setText(this.clienteAtual.getEmail());
        this.etNome.setText(this.clienteAtual.getNome());
        this.etTelefone.setText(this.clienteAtual.getTelefone());
        this.etData.setText(this.clienteAtual.getData());
        this.etCpf.setText(this.clienteAtual.getCpf());

        this.btnAlterar = (Button) root.findViewById(R.id.btnAlterar);
        this.btnExcluir = (Button) root.findViewById(R.id.btnExcluir);



        this.btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());

                final String nome = etNome.getText().toString().trim();
                final String data = etData.getText().toString();
                final String email = etEmail.getText().toString();
                final String telefone = etTelefone.getText().toString();
                final String cpf = etCpf.getText().toString();

                try
                {
                    Cliente c = new Cliente(0, nome, email, telefone, cpf, data, 2);
                }
                catch(Exception exc)
                {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            exc.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                MyTask task = new MyTask(Cliente[].class);
                task.execute(Enderecos.GET_CLIENTES + "_email/" + email);
                while (task.isTrabalhando()) ;
                Cliente[] resultClientes = (Cliente[]) task.getDados();
                if (resultClientes.length > 0)
                {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Email já cadastrado",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                StringRequest putRequest = new StringRequest(
                        Request.Method.PATCH,
                        Enderecos.PATCH_CLIENTES + "/" + clienteAtual.getCodigo(),
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
                final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());

                StringRequest dr = new StringRequest(
                        Request.Method.DELETE,
                        Enderecos.DELETE_CLIENTES + "/" + clienteAtual.getCodigo(),
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        "Cliente excluído",
                                        Toast.LENGTH_SHORT).show();
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
            }
        });

        return root;
    }
}

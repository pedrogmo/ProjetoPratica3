package com.example.appandroidfotovoltaica.ui.login.cadastrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.appandroidfotovoltaica.Cliente;
import com.example.appandroidfotovoltaica.Empresa;
import com.example.appandroidfotovoltaica.Enderecos;
import com.example.appandroidfotovoltaica.MyTask;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.ui.home.HomeFragment;
import com.example.appandroidfotovoltaica.ui.login.LoginViewModel;

import java.util.HashMap;
import java.util.Map;

public class Cadastrar extends Fragment {
    private CadastrarViewModel cadastrarViewModel;
    private Spinner spEmpresa;
    private Empresa[] empresas;
    private Button btnCadastrarUsuario;
    private EditText etNome, etEmail, etSenhaUm, etSenhaConfirmada, etTelefone, etCpf, etDataNascimento;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cadastrarViewModel =
                ViewModelProviders.of(this).get(CadastrarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cadastrar, container, false);
        etNome = root.findViewById(R.id.etNomeUsuario);
        etEmail = root.findViewById(R.id.etEmailUsuario);
        etSenhaUm = root.findViewById(R.id.etSenhaUmUsuario);
        etSenhaConfirmada = root.findViewById(R.id.etSenhaConfirmadaUsuario);
        etTelefone = root.findViewById(R.id.etTelefoneUsuario);
        etCpf = root.findViewById(R.id.etCpfUsuario);
        etDataNascimento = root.findViewById(R.id.etDataNascimentoUsuario);
        spEmpresa = root.findViewById(R.id.spEmpresa);
        btnCadastrarUsuario = root.findViewById(R.id.btnCadastrarUsuario);

        MyTask task = new MyTask(Empresa[].class);
        task.execute(Enderecos.GET_EMPRESAS);
        while (task.isTrabalhando()) ;
        empresas = (Empresa[]) task.getDados();
        String[] nomesEmpresas = new String[empresas.length];
        for (int i = 0; i < empresas.length; i++)
            nomesEmpresas[i] = empresas[i].getNome();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, nomesEmpresas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmpresa.setAdapter(adapter);

        this.btnCadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nome = etNome.getText().toString().trim();
                final String data = etDataNascimento.getText().toString();
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
                task.execute(Enderecos.GET_USUARIOS + "_email/" + email);
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


                HashMap<String, String> params = new HashMap<String,String>();

                StringRequest postRequest = new StringRequest(
                        Request.Method.POST,
                        Enderecos.POST_USUARIOS,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        "Cliente inserido",
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
                                        "Erro ao inserir cliente",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", email);
                        params.put("nome", nome);
                        params.put("telefone", telefone);
                        params.put("data", data);
                        params.put("cpf", cpf);
                        params.put("codEmpresa", "2");
                        return params;
                    }
                };
                QUEUE.add(postRequest);
            }
        });

       return root;
    }

}


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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appandroidfotovoltaica.ArvoreBinaria;
import com.example.appandroidfotovoltaica.Cliente;
import com.example.appandroidfotovoltaica.Empresa;
import com.example.appandroidfotovoltaica.Enderecos;
import com.example.appandroidfotovoltaica.MyTask;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.Usuario;
import com.example.appandroidfotovoltaica.Verificadora;
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
    private TextView tvExceptionNome, tvExceptionEmail, tvExceptionSenhaUm, tvExceptionSenhaConfirmada,
            tvExceptionTelefone, tvExceptionDataNascimento, tvExceptionCpf;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cadastrarViewModel =
                ViewModelProviders.of(this).get(CadastrarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cadastrar, container, false);
        etNome = root.findViewById(R.id.etNomeUsuario);
        tvExceptionNome = root.findViewById(R.id.tvExceptionNomeUsuario);
        etEmail = root.findViewById(R.id.etEmailUsuario);
        tvExceptionEmail = root.findViewById(R.id.tvExceptionEmailUsuario);
        etSenhaUm = root.findViewById(R.id.etSenhaUmUsuario);
        tvExceptionSenhaUm = root.findViewById(R.id.tvExceptionSenhaUmUsuario);
        etSenhaConfirmada = root.findViewById(R.id.etSenhaConfirmadaUsuario);
        tvExceptionSenhaConfirmada = root.findViewById(R.id.tvExceptionSenhaConfirmadaUsuario);
        etTelefone = root.findViewById(R.id.etTelefoneUsuario);
        tvExceptionTelefone = root.findViewById(R.id.tvExceptionTelefoneUsuario);
        etCpf = root.findViewById(R.id.etCpfUsuario);
        tvExceptionCpf = root.findViewById(R.id.tvExceptionCpfUsuario);
        etDataNascimento = root.findViewById(R.id.etDataNascimentoUsuario);
        tvExceptionDataNascimento = root.findViewById(R.id.tvExceptionDataNascimentoUsuario);
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
                    final String senhaUm = etSenhaUm.getText().toString();
                    final String senhaConfirmada = etSenhaConfirmada.getText().toString();
                    final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());

                    if (!Verificadora.isNomeValido(nome))


                    try
                    {
                        Usuario u = new Usuario(0, email, nome, senhaConfirmada, 2, telefone, cpf, data);
                    }
                    catch(Exception exc)
                    {
                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                exc.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    MyTask task = new MyTask(Usuario[].class);
                    task.execute(Enderecos.GET_USUARIOS + "_email/" + email);
                    while (task.isTrabalhando()) ;
                    //ArvoreBinaria<String> arvoreBinaria = new ArvoreBinaria<String>();
                    Usuario[] resultUsuarios = (Usuario[]) task.getDados();
                    /*for (int i = 0; i < resultUsuarios.length; i++)
                    {
                        try{
                            arvoreBinaria.adicionar(resultUsuarios[i].getEmail());
                        }
                        catch (Exception e){}
                    }
                    try{
                        if (arvoreBinaria.buscar(email) != null)
                        {
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Email já cadastrado",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    catch(Exception e){Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);}
                    */



                    if (resultUsuarios.length > 0)
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
                                            "Usario inserido",
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
                                            "Erro ao inserir usuario",
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
                            params.put("senha", senhaConfirmada);
                            params.put("codEmpresa", "2");
                            params.put("telefone", telefone);
                            params.put("cpf", cpf);
                            params.put("data", data);
                            return params;
                        }
                    };
                    QUEUE.add(postRequest);


            }
        });

       return root;
    }

}


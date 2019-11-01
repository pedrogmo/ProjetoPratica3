package com.example.appandroidfotovoltaica.ui.login.cadastrarusuario;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appandroidfotovoltaica.classes.arvorebinaria.ArvoreBinaria;
import com.example.appandroidfotovoltaica.classes.criptografia.Criptografia;
import com.example.appandroidfotovoltaica.classes.empresa.Empresa;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.verificadora.mensagenserro.mensagenserrousuario.MensagensErroUsuario;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.usuario.Usuario;
import com.example.appandroidfotovoltaica.ui.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class CadastrarUsuarioFragment extends Fragment {
    private CadastrarUsuarioViewModel cadastrarUsuarioViewModel;
    private Spinner spEmpresa;
    private Empresa[] empresas;
    private Button btnCadastrarUsuario;
    private EditText etNome, etEmail, etSenhaUm, etSenhaConfirmada, etTelefone, etCpf, etDataNascimento;
    private TextView tvExceptionNome, tvExceptionEmail, tvExceptionSenhaUm, tvExceptionSenhaConfirmada,
            tvExceptionTelefone, tvExceptionDataNascimento, tvExceptionCpf;
    private MensagensErroUsuario mensagens;
    private int indEmpresa = 0;
    private ArvoreBinaria<Usuario> usuariosCadastrados;
    private String textoAnterior = "";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cadastrarUsuarioViewModel =
                ViewModelProviders.of(this).get(CadastrarUsuarioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cadastrarusuario, container, false);
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
        etDataNascimento.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String texto = charSequence.toString();
                if (textoAnterior.length() < texto.length()){
                    if (texto.length() == 2 || texto.length() == 5)
                        etDataNascimento.append("/");
                }
                else
                    if (texto.length() >=1 && texto.charAt(texto.length() - 1) == '/'){
                        texto = texto.substring(0,texto.length() - 1);
                        etDataNascimento.setText(texto);
                        etDataNascimento.setSelection(texto.length());
                    }

                textoAnterior = texto;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tvExceptionDataNascimento = root.findViewById(R.id.tvExceptionDataNascimentoUsuario);
        spEmpresa = root.findViewById(R.id.spEmpresa);
        btnCadastrarUsuario = root.findViewById(R.id.btnCadastrarUsuario);

        mensagens = new MensagensErroUsuario(
            tvExceptionNome,
            tvExceptionDataNascimento,
            tvExceptionEmail,
            tvExceptionTelefone,
            tvExceptionCpf,
            tvExceptionSenhaUm,
            tvExceptionSenhaConfirmada);

        Bundle bundle = getArguments();
        usuariosCadastrados = (ArvoreBinaria<Usuario>) bundle.getSerializable("usuarios");

        MyTask task = new MyTask(Empresa[].class);
        task.execute(Enderecos.GET_EMPRESA);
        while (task.isTrabalhando()) ;
        empresas = (Empresa[]) task.getDados();
        String[] nomesEmpresas = new String[empresas.length];
        for (int i = 0; i < empresas.length; i++)
            nomesEmpresas[i] = empresas[i].getNome();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, nomesEmpresas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmpresa.setAdapter(adapter);

        spEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                indEmpresa = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        this.btnCadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nome = etNome.getText().toString().trim();
                final String data = etDataNascimento.getText().toString().trim();

                final String email = etEmail.getText().toString().trim();
                final String telefone = etTelefone.getText().toString().trim();
                final String cpf = etCpf.getText().toString().trim();
                final String senhaUm = etSenhaUm.getText().toString();
                final String senhaConfirmada = etSenhaConfirmada.getText().toString();
                final String codEmpresa = empresas[indEmpresa].getCodigo() + "";

                final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());

                if (mensagens.teveMensagensDeErro(nome, data, email, telefone, cpf, senhaUm, senhaConfirmada))
                    return;

                final String senhaCriptografada = Criptografia.criptografar(senhaConfirmada);

                Usuario u = null;
                try
                {
                    u = usuariosCadastrados.buscar(new Usuario(email));
                }
                catch(Exception exc){}

                if (u != null)
                {
                    Toast.makeText(
                        getActivity().getApplicationContext(),
                        "Email já cadastrado",
                        Toast.LENGTH_SHORT).show();
                    return;
                }


                StringRequest postRequest = new StringRequest(
                    Request.Method.POST,
                    Enderecos.POST_USUARIO,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Usario inserido",
                                Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                            startActivity(i);
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
                        params.put("senha", senhaCriptografada);
                        params.put("codEmpresa", codEmpresa);
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


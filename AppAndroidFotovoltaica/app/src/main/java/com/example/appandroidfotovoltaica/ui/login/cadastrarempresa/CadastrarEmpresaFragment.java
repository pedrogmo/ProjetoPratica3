package com.example.appandroidfotovoltaica.ui.login.cadastrarempresa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.arvorebinaria.ArvoreBinaria;
import com.example.appandroidfotovoltaica.classes.constantesdetransicao.ConstantesDeTransicao;
import com.example.appandroidfotovoltaica.classes.criptografia.Criptografia;
import com.example.appandroidfotovoltaica.classes.empresa.Empresa;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.verificadora.mensagenserro.mensagenserroempresa.MensagensErroEmpresa;
import com.example.appandroidfotovoltaica.ui.login.telaEmpresa.TelaEmpresaFragment;

import java.util.HashMap;
import java.util.Map;

public class CadastrarEmpresaFragment extends Fragment {
    private CadastrarEmpresaViewModel cadastrarUsuarioViewModel;
    private Button btnCadastrarEmpresa;
    private EditText etNomeEmpresa, etCNPJEmpresa, etSenhaUm, etSenhaConfirmada;
    private TextView tvExceptionNome, tvExceptionCNPJ, tvExceptionSenhaUm, tvExceptionSenhaConfirmada;
    private MensagensErroEmpresa mensagens;
    private ArvoreBinaria<Empresa> empresasCadastradas;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cadastrarUsuarioViewModel =
                ViewModelProviders.of(this).get(CadastrarEmpresaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cadastrarempresa, container, false);
        etNomeEmpresa = root.findViewById(R.id.etNomeEmpresaCadastrar);
        etCNPJEmpresa = root.findViewById(R.id.etCNPJEmpresaCadastrar);
        etSenhaUm = root.findViewById(R.id.etSenhaUmEmpresa);
        etSenhaConfirmada = root.findViewById(R.id.etSenhaConfirmadaEmpresa);
        tvExceptionNome = root.findViewById(R.id.tvExceptionNomeEmpresa);
        tvExceptionCNPJ =  root.findViewById(R.id.tvExceptionCNPJEmpresa);
        tvExceptionSenhaUm = root.findViewById(R.id.tvExceptionSenhaUmEmpresa);
        tvExceptionSenhaConfirmada = root.findViewById(R.id.tvExceptionSenhaConfirmadaEmpresa);
        btnCadastrarEmpresa = root.findViewById(R.id.btnCadastrarEmpresa);

        Bundle bundle = getArguments();
        empresasCadastradas = (ArvoreBinaria<Empresa>) bundle.getSerializable("empresas");
        mensagens = new MensagensErroEmpresa(tvExceptionNome, tvExceptionCNPJ, tvExceptionSenhaUm, tvExceptionSenhaConfirmada);

        btnCadastrarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nome = etNomeEmpresa.getText().toString().trim();
                final String cnpj = etCNPJEmpresa.getText().toString().trim();
                final String senhaUm = etSenhaUm.getText().toString();
                final String senhaConfirmada = etSenhaConfirmada.getText().toString();

                final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());

                if (mensagens.teveMensagensDeErro(nome, cnpj, senhaUm, senhaConfirmada))
                    return;



                final String senhaCriptografada = Criptografia.criptografar(senhaConfirmada);

                Empresa e = null;
                try
                {
                    e = empresasCadastradas.buscar(new Empresa(cnpj));
                }
                catch(Exception exc){}

                if (e != null)
                {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Uma empresa com esse CNPJ já foi cadastrada",
                            Toast.LENGTH_LONG).show();
                    return;
                }


                StringRequest postRequest = new StringRequest(
                        Request.Method.POST,
                        Enderecos.POST_EMPRESA,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        "Empresa inserida",
                                        Toast.LENGTH_SHORT).show();

                                FragmentManager f = getFragmentManager();
                                f.popBackStack(ConstantesDeTransicao.M_CADASTRO_EMPRESA, 0);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        "Erro ao inserir empresa",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                )
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("nome", nome);
                        params.put("cnpj", cnpj);
                        params.put("senha", senhaCriptografada);
                        return params;
                    }
                };
                QUEUE.add(postRequest);

            }
        });


       return root;
    }
}


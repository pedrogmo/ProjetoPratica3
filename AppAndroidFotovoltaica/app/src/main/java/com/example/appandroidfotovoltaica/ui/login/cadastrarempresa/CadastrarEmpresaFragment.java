package com.example.appandroidfotovoltaica.ui.login.cadastrarempresa;

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
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.arvorebinaria.ArvoreBinaria;
import com.example.appandroidfotovoltaica.classes.criptografia.Criptografia;
import com.example.appandroidfotovoltaica.classes.empresa.Empresa;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.classes.usuario.Usuario;
import com.example.appandroidfotovoltaica.classes.verificadora.mensagenserro.mensagenserroempresa.MensagensErroEmpresa;
import com.example.appandroidfotovoltaica.classes.verificadora.mensagenserro.mensagenserrousuario.MensagensErroUsuario;
import com.example.appandroidfotovoltaica.ui.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class CadastrarEmpresaFragment extends Fragment {
    private CadastrarEmpresaViewModel cadastrarUsuarioViewModel;
    private Empresa[] empresas;
    private Button btnCadastrarEmpresa;
    private EditText etNomeEmpresa, etCNPJEmpresa, etSenhaUm, etSenhaConfirmada;
    private TextView tvExceptionNome, tvExceptionCNPJ, tvExceptionSenhaUm, tvExceptionSenhaConfirmada;
    private MensagensErroEmpresa mensagens;


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
        btnCadastrarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nome = etNomeEmpresa.getText().toString().trim();
                final String cnpj = etCNPJEmpresa.getText().toString();
                final String senhaUm = etSenhaUm.getText().toString();
                final String senhaConfirmada = etSenhaConfirmada.getText().toString();

                final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());

                if (mensagens.teveMensagensDeErro(nome, cnpj, senhaUm, senhaConfirmada))
                    return;

            }
        });


       return root;
    }
}


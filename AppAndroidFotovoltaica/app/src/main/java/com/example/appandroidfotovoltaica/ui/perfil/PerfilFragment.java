package com.example.appandroidfotovoltaica.ui.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.appandroidfotovoltaica.CalculadoraFotoVoltaica;
import com.example.appandroidfotovoltaica.Cliente;
import com.example.appandroidfotovoltaica.Empresa;
import com.example.appandroidfotovoltaica.Enderecos;
import com.example.appandroidfotovoltaica.MensagensErroAlterarPerfil;
import com.example.appandroidfotovoltaica.MensagensErroUsuario;
import com.example.appandroidfotovoltaica.MyTask;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.Usuario;
import com.example.appandroidfotovoltaica.ValorMensalEnergia;
import com.example.appandroidfotovoltaica.Verificadora;
import com.example.appandroidfotovoltaica.ui.login.LoginActivity;
import com.example.appandroidfotovoltaica.ui.principalClientes.PrincipalClientesFragment;

import java.util.HashMap;
import java.util.Map;

public class PerfilFragment extends Fragment {

    private PerfilViewModel perfilViewModel;
    EditText etNome, etTelefone, etData, etCpf, etSenhaUm, etSenhaConfirmada;
    TextView tvEmail, tvEmpresa, tvExceptionNome, tvExceptionSenhaUm, tvExceptionSenhaConfirmada, tvExceptionTelefone, tvExceptionData, tvExceptionCpf;
    Button btnAlterar, btnExcluir;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel =
                ViewModelProviders.of(this).get(PerfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        etNome = root.findViewById(R.id.etNomePerfil);
        etTelefone = root.findViewById(R.id.etTelefonePerfil);
        etData = root.findViewById(R.id.etDataPerfil);
        etCpf = root.findViewById(R.id.etCpfPerfil);
        etSenhaUm = root.findViewById(R.id.etSenhaUmPerfil);
        etSenhaConfirmada = root.findViewById(R.id.etSenhaConfirmadaPerfil);
        tvEmail = root.findViewById(R.id.tvEmailPerfil);
        tvEmpresa = root.findViewById(R.id.tvEmpresaPerfil);
        tvExceptionNome = root.findViewById(R.id.tvExceptionNomePerfil);
        tvExceptionTelefone = root.findViewById(R.id.tvExceptionTelefonePerfil);
        tvExceptionCpf = root.findViewById(R.id.tvExceptionCpfPerfil);
        tvExceptionData = root.findViewById(R.id.tvExceptionDataPerfil);
        tvExceptionSenhaUm = root.findViewById(R.id.tvExceptionSenhaUmPerfil);
        tvExceptionSenhaConfirmada = root.findViewById(R.id.tvExceptionSenhaConfirmadaPerfil);
        btnAlterar = root.findViewById(R.id.btnAlterarPerfil);
        btnExcluir = root.findViewById(R.id.btnExcluirPerfil);
        Intent intent = getActivity().getIntent();
        final Usuario logado = (Usuario)intent.getSerializableExtra("usuario");
        tvEmail.setText(logado.getEmail());
        etSenhaUm.setText(logado.getSenha());
        etSenhaConfirmada.setText(logado.getSenha());

        MyTask task = new MyTask(Empresa[].class);
        task.execute(Enderecos.GET_EMPRESAS + "/codigo/" + logado.getCodEmpresa());
        while (task.isTrabalhando()) ;
        Empresa[] resultEmpresas = (Empresa[]) task.getDados();

        String nomeEmpresa = resultEmpresas[0].getNome();
        tvEmpresa.setText(nomeEmpresa);

        this.btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());

                final String nome = etNome.getText().toString().trim();
                final String data = etData.getText().toString();
                final String telefone = etTelefone.getText().toString();
                final String cpf = etCpf.getText().toString();
                final String senhaUm = etSenhaUm.getText().toString();
                final String senhaConfirmada = etSenhaConfirmada.getText().toString();
                MensagensErroAlterarPerfil m = new MensagensErroAlterarPerfil(tvExceptionNome, tvExceptionData,
                        tvExceptionTelefone, tvExceptionCpf, tvExceptionSenhaUm, tvExceptionSenhaConfirmada);

                if (m.teveMensagensDeErro(nome, data, telefone, cpf, senhaUm, senhaConfirmada))
                    return;

                StringRequest putRequest = new StringRequest(
                        Request.Method.PATCH,
                        Enderecos.PATCH_USUARIOS + "/" + logado.getCodigo(),
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        "Perfil alterado",
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
                        params.put("nome", nome);
                        params.put("senha", senhaConfirmada);
                        params.put("telefone", telefone);
                        params.put("cpf", cpf);
                        params.put("data", data);
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
                        Enderecos.DELETE_USUARIOS + "/" + logado.getCodigo(),
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        "Usuário excluído",
                                        Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                                startActivity(i);
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
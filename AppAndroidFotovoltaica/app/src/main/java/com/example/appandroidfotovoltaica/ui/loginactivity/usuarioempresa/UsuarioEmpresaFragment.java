package com.example.appandroidfotovoltaica.ui.loginactivity.usuarioempresa;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.usuario.Usuario;
import com.example.appandroidfotovoltaica.ui.loginactivity.empresaindividual.EmpresaIndividualFragment;

import java.util.HashMap;
import java.util.Map;

public class UsuarioEmpresaFragment extends Fragment {

    private UsuarioEmpresaViewModel mViewModel;

    private TextView
        tvEmail,
        tvNome,
        tvTelefone,
        tvCpf,
        tvData;
    private LinearLayout llBotoes;

    private Usuario usuarioAtual;

    public static UsuarioEmpresaFragment newInstance() {
        return new UsuarioEmpresaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.mViewModel = ViewModelProviders.of(this).get(UsuarioEmpresaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_usuarioempresa, container, false);

        Bundle b = getArguments();
        this.usuarioAtual = (Usuario) b.getSerializable("usuario");

        this.tvEmail = (TextView) root.findViewById(R.id.tvEmailUsuarioEmpresa);
        this.tvNome = (TextView) root.findViewById(R.id.tvNomeUsuarioEmpresa);
        this.tvTelefone = (TextView) root.findViewById(R.id.tvTelefoneUsuarioEmpresa);
        this.tvCpf = (TextView) root.findViewById(R.id.tvCpfUsuarioEmpresa);
        this.tvData = (TextView) root.findViewById(R.id.tvDataUsuarioEmpresa);
        this.llBotoes = (LinearLayout) root.findViewById(R.id.llUsuarioEmpresa);

        this.tvEmail.setText("Email: " + usuarioAtual.getEmail());
        this.tvNome.setText("Nome: " + usuarioAtual.getNome());
        this.tvTelefone.setText("Telefone: " + usuarioAtual.getTelefone());
        this.tvCpf.setText("Cpf: " + usuarioAtual.getCpf());
        this.tvData.setText("Nascimento: " + usuarioAtual.getData());


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        if (this.usuarioAtual.isPermitido())
        {
            Button btnDesassociar = new Button(getActivity().getApplicationContext());
            btnDesassociar.setText("Desassociar");
            btnDesassociar.setLayoutParams(params);

            btnDesassociar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Deseja mesmo desassociar?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                        "Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());

                                StringRequest putRequest = new StringRequest(
                                        Request.Method.PATCH,
                                        Enderecos.DESASSOCIAR_USUARIO + "/" + usuarioAtual.getCodigo(),
                                        new Response.Listener<String>()
                                        {
                                            @Override
                                            public void onResponse(String response) {
                                                // response
                                                Toast.makeText(
                                                    getActivity().getApplicationContext(),
                                                    "Usuário desassociado",
                                                    Toast.LENGTH_SHORT).show();

                                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                                fragmentTransaction.replace(R.id.fragment_usuarioempresa, new EmpresaIndividualFragment());
                                                fragmentTransaction.commit();
                                            }
                                        },
                                        new Response.ErrorListener()
                                        {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                // error
                                                Toast.makeText(
                                                    getActivity().getApplicationContext(),
                                                    "Erro ao desassociar",
                                                    Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                )
                                {
                                    @Override
                                    protected Map<String, String> getParams()
                                    {
                                        Map<String, String>  params = new HashMap<String, String>();
                                        return params;
                                    }

                                };
                                QUEUE.add(putRequest);

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

            this.llBotoes.addView(btnDesassociar);
        }

        else
        {
            Button btnAssociar = new Button(getActivity().getApplicationContext());
            btnAssociar.setText("Associar");
            btnAssociar.setLayoutParams(params);

            btnAssociar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());

                    StringRequest putRequest = new StringRequest(
                            Request.Method.PATCH,
                            Enderecos.ASSOCIAR_USUARIO + "/" + usuarioAtual.getCodigo(),
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Toast.makeText(
                                            getActivity().getApplicationContext(),
                                            "Usuário associado",
                                            Toast.LENGTH_SHORT).show();

                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_usuarioempresa, new EmpresaIndividualFragment());
                                    fragmentTransaction.commit();
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(
                                            getActivity().getApplicationContext(),
                                            "Erro ao associar",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                    )
                    {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            return params;
                        }

                    };
                    QUEUE.add(putRequest);
                }
            });

            this.llBotoes.addView(btnAssociar);

            Button btnRejeitar = new Button(getActivity().getApplicationContext());
            btnRejeitar.setText("Rejeitar");
            btnRejeitar.setLayoutParams(params);

            btnRejeitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Deseja mesmo rejeitar?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Sim",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());

                                    StringRequest dr = new StringRequest(
                                        Request.Method.DELETE,
                                        Enderecos.DELETE_USUARIO + "/" + usuarioAtual.getCodigo(),
                                        new Response.Listener<String>()
                                        {
                                            @Override
                                            public void onResponse(String response) {
                                                // response
                                                Toast.makeText(
                                                    getActivity().getApplicationContext(),
                                                    "Usuário rejeitado",
                                                    Toast.LENGTH_SHORT).show();

                                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                                fragmentTransaction.replace(R.id.fragment_usuarioempresa, new EmpresaIndividualFragment());
                                                fragmentTransaction.commit();
                                            }
                                        },
                                        new Response.ErrorListener()
                                        {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(
                                                    getActivity().getApplicationContext(),
                                                    "Erro ao rejeitar",
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

            this.llBotoes.addView(btnRejeitar);
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UsuarioEmpresaViewModel.class);
        // TODO: Use the ViewModel
    }

}

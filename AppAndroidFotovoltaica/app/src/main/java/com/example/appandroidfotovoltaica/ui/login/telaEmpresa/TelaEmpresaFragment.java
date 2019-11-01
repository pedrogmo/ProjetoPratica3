package com.example.appandroidfotovoltaica.ui.login.telaEmpresa;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appandroidfotovoltaica.MainActivity;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.cliente.Cliente;
import com.example.appandroidfotovoltaica.classes.constantesdetransicao.ConstantesDeTransicao;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.classes.verificadora.mensagenserro.mensagenserrocliente.MensagensErroCliente;
import com.example.appandroidfotovoltaica.ui.clientes.ClientesFragment;
import com.example.appandroidfotovoltaica.ui.login.cadastrarempresa.CadastrarEmpresaFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

public class TelaEmpresaFragment extends Fragment {

    private TelaEmpresaViewModel galleryViewModel;

    private EditText
        etCNPJEmpresa,
        etSenhaEmpresa;
    private TextView tvCadastrarEmpresa;

    private Button btnLogarEmpresa;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(TelaEmpresaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_telaempresa, container, false);

        etCNPJEmpresa = root.findViewById(R.id.etCNPJEmpresa);
        etSenhaEmpresa = root.findViewById(R.id.etSenhaEmpresa);
        tvCadastrarEmpresa = root.findViewById(R.id.tvCadastrarEmpresa);
        tvCadastrarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager f = getFragmentManager();
                f.beginTransaction().replace(R.id.fragmentTelaEmpresa, new CadastrarEmpresaFragment(), ConstantesDeTransicao.F_CADASTRO).addToBackStack( ConstantesDeTransicao.M_CADASTRO).commit();
            }
        });
        btnLogarEmpresa = root.findViewById(R.id.btnLogarEmpresa);
        btnLogarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return root;
    }


}
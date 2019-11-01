package com.example.appandroidfotovoltaica.ui.login.telaEmpresa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.arvorebinaria.ArvoreBinaria;
import com.example.appandroidfotovoltaica.classes.constantesdetransicao.ConstantesDeTransicao;
import com.example.appandroidfotovoltaica.classes.empresa.Empresa;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.ui.login.cadastrarempresa.CadastrarEmpresaFragment;

public class TelaEmpresaFragment extends Fragment {

    private TelaEmpresaViewModel galleryViewModel;

    private EditText
        etCNPJEmpresa,
        etSenhaEmpresa;
    private TextView tvCadastrarEmpresa;

    private Button btnLogarEmpresa;
    private Empresa[] empresas;
    private ArvoreBinaria<Empresa> arvoreEmpresas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(TelaEmpresaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_telaempresa, container, false);

        etCNPJEmpresa = root.findViewById(R.id.etCNPJEmpresa);
        etSenhaEmpresa = root.findViewById(R.id.etSenhaEmpresa);
        tvCadastrarEmpresa = root.findViewById(R.id.tvCadastrarEmpresa);

        MyTask task = new MyTask(Empresa[].class);
        task.execute(Enderecos.GET_EMPRESA);
        while (task.isTrabalhando()) ;
        empresas = (Empresa[]) task.getDados();

        try{
            for (int i = 0; i < empresas.length; i++)
                arvoreEmpresas.adicionar(empresas[i]);
        }catch (Exception e){}


        tvCadastrarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("empresas", arvoreEmpresas);
                Fragment cadastro = new CadastrarEmpresaFragment();
                cadastro.setArguments(bundle);
                FragmentManager f = getFragmentManager();
                f.beginTransaction().replace(R.id.fragmentTelaEmpresa, cadastro, ConstantesDeTransicao.F_CADASTRO_EMPRESA).addToBackStack( ConstantesDeTransicao.M_CADASTRO_EMPRESA).commit();
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
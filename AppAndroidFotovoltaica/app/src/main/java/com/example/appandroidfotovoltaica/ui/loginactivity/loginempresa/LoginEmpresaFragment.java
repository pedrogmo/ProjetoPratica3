package com.example.appandroidfotovoltaica.ui.loginactivity.loginempresa;

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

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.arvorebinaria.ArvoreBinaria;
import com.example.appandroidfotovoltaica.classes.constantesdetransicao.ConstantesDeTransicao;
import com.example.appandroidfotovoltaica.classes.criptografia.Criptografia;
import com.example.appandroidfotovoltaica.classes.empresa.Empresa;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.ui.loginactivity.cadastrarempresa.CadastrarEmpresaFragment;
import com.example.appandroidfotovoltaica.ui.loginactivity.empresaindividual.EmpresaIndividualFragment;

public class LoginEmpresaFragment extends Fragment {


    private EditText
        etCNPJEmpresa,
        etSenhaEmpresa;
    private TextView tvCadastrarEmpresa;

    private Button btnLogarEmpresa;
    private Empresa[] empresas;
    private ArvoreBinaria<Empresa> arvoreEmpresas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_loginempresa, container, false);

        etCNPJEmpresa = root.findViewById(R.id.etCNPJEmpresaLogin);
        etSenhaEmpresa = root.findViewById(R.id.etSenhaEmpresaLogin);
        tvCadastrarEmpresa = root.findViewById(R.id.tvCadastrarEmpresa);
        this.arvoreEmpresas = new ArvoreBinaria<Empresa>();

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
                f.beginTransaction().replace(R.id.fragment_loginempresa, cadastro, ConstantesDeTransicao.F_CADASTRO_EMPRESA).addToBackStack( ConstantesDeTransicao.M_CADASTRO_EMPRESA).commit();
            }
        });
        btnLogarEmpresa = root.findViewById(R.id.btnLogarEmpresa);
        btnLogarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cnpj =  etCNPJEmpresa.getText().toString().trim();
                final String senha = etSenhaEmpresa.getText().toString();

                if(cnpj.equals("") || senha.equals(""))
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Digite os dados de login", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String senhaCriptografada = Criptografia.criptografar(senha);

                Empresa busca = null;
                try
                {
                    busca = arvoreEmpresas.buscar(new Empresa(cnpj));
                }
                catch(Exception exc)
                { }

                if (busca == null)
                {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Empresa não existe",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!busca.getSenha().equals(senhaCriptografada))
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Senha incorreta", Toast.LENGTH_SHORT).show();
                    return;
                }


                Toast.makeText(getActivity().getApplicationContext(),"Login efetuado com sucesso",Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putSerializable("empresa", busca);
                Fragment empresaf = new EmpresaIndividualFragment();
                empresaf.setArguments(bundle);
                FragmentManager f = getFragmentManager();
                f.beginTransaction().replace(R.id.fragment_loginempresa, empresaf, ConstantesDeTransicao.F_EMPRESA_INDIVIDUAL).addToBackStack( ConstantesDeTransicao.M_EMPRESA_INDIVIDUAL).commit();
            }
        });


        return root;
    }


}
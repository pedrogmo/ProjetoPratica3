package com.example.appandroidfotovoltaica.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appandroidfotovoltaica.Enderecos;
import com.example.appandroidfotovoltaica.MyTask;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.Usuario;
import com.example.appandroidfotovoltaica.ui.adicionarcliente.AdicionarClienteFragment;
import com.example.appandroidfotovoltaica.ui.home.HomeFragment;
import com.example.appandroidfotovoltaica.ui.home.HomeViewModel;
import com.example.appandroidfotovoltaica.ui.login.cadastrar.Cadastrar;
import com.example.appandroidfotovoltaica.ui.principalClientes.PrincipalClientesFragment;

public class Login extends Fragment {
    private LoginViewModel loginViewModel;
    private TextView tvCadastrarLogin;
    private EditText etEmailLogin, etSenhaLogin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        loginViewModel =
                ViewModelProviders.of(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        etEmailLogin = root.findViewById(R.id.etEmailLogin);
        etSenhaLogin = root.findViewById(R.id.etSenhaLogin);



        tvCadastrarLogin = root.findViewById(R.id.tvCadastrarLogin);
        tvCadastrarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_login, new Cadastrar());
                fragmentTransaction.commit();
            }
        });

        final Button btnLogar = root.findViewById(R.id.btnLogar);
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etEmailLogin.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Digite os dados de login", Toast.LENGTH_SHORT).show();
                    return;
                }

                MyTask task = new MyTask(Usuario[].class);
                task.execute(Enderecos.GET_USUARIOS + "_email/" + etEmailLogin.getText().toString());
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



                if (resultUsuarios.length <= 0)
                {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Usuário não existe",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    boolean loginCorreto = false;
                    for (int i = 0; i < resultUsuarios.length; i++)
                    {
                        if (resultUsuarios[i].getSenha() == etSenhaLogin.getText().toString())
                            loginCorreto = true;
                    }
                    if (loginCorreto == false){
                        Toast.makeText(getActivity().getApplicationContext(),"Dados incorretos",Toast.LENGTH_SHORT).show();
                    }
                }

                Toast.makeText(getActivity().getApplicationContext(),"Login efetuado com sucesso",Toast.LENGTH_SHORT).show();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_login, new HomeFragment());
                fragmentTransaction.commit();


            }
        });
        return root;
    }

}

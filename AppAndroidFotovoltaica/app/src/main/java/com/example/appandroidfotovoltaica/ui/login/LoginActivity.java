package com.example.appandroidfotovoltaica.ui.login;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appandroidfotovoltaica.ArvoreBinaria;
import com.example.appandroidfotovoltaica.Enderecos;
import com.example.appandroidfotovoltaica.MainActivity;
import com.example.appandroidfotovoltaica.MyTask;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.Usuario;
import com.example.appandroidfotovoltaica.ui.home.HomeFragment;
import com.example.appandroidfotovoltaica.ui.login.cadastrar.Cadastrar;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity {

    private ArvoreBinaria<Usuario> arvoreUsuarios;
    private EditText etEmailLogin, etSenhaLogin;
    private Button btnLogar;
    private TextView tvCadastrarLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmailLogin = findViewById(R.id.etEmailLogin);
        etSenhaLogin = findViewById(R.id.etSenhaLogin);
        btnLogar = findViewById(R.id.btnLogar);
        tvCadastrarLogin = findViewById(R.id.tvCadastrarLogin);

        MyTask task = new MyTask(Usuario[].class);
        task.execute(Enderecos.GET_USUARIOS);
        while (task.isTrabalhando()) ;
        for(Usuario u : (Usuario[]) task.getDados())
        {
            try
            {
                arvoreUsuarios.adicionar(u);
            }
            catch(Exception exc)
            {
                Log.d("ERRO", exc.getMessage());
            }
        }

        tvCadastrarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment cadastro = new Cadastrar();
                FragmentManager f = getSupportFragmentManager();
                f.beginTransaction().replace(R.id.container, cadastro).commit();
            }
        });

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email =  etEmailLogin.getText().toString().trim();
                final String senha = etSenhaLogin.getText().toString().trim();
                if(email.equals("") || senha.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Digite os dados de login", Toast.LENGTH_SHORT).show();
                    return;
                }

                Usuario busca = null;
                try
                {
                    busca = arvoreUsuarios.buscar(new Usuario(email)); //ERRO AQUI
                }
                catch(Exception exc)
                {
                    Log.d("ERRO", exc.getMessage());
                }

                if (busca == null)
                {
                    Toast.makeText(
                            getApplicationContext(),
                            "Usuário não existe",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (busca.getSenha().equals(senha))
                {
                    Toast.makeText(getApplicationContext(),"Login efetuado com sucesso",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(),"Senha incorreta",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

/*
    Código anterior - vetor de usuários

    MyTask task = new MyTask(Usuario[].class);
    task.execute(Enderecos.GET_USUARIOS + "_email/" + etEmailLogin.getText().toString());
    while (task.isTrabalhando()) ;
    Usuario[] resultUsuarios = (Usuario[]) task.getDados();

    if (resultUsuarios.length <= 0)
    {
        Toast.makeText(
            getApplicationContext(),
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
            Toast.makeText(getApplicationContext(),"Dados incorretos",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    Toast.makeText(getApplicationContext(),"Login efetuado com sucesso",Toast.LENGTH_SHORT).show();
    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    startActivity(intent);
*/

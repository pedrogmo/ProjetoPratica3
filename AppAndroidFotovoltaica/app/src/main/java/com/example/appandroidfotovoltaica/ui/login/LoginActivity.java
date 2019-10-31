package com.example.appandroidfotovoltaica.ui.login;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appandroidfotovoltaica.classes.arvorebinaria.ArvoreBinaria;
import com.example.appandroidfotovoltaica.classes.criptografia.Criptografia;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.MainActivity;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.usuario.Usuario;
import com.example.appandroidfotovoltaica.ui.login.cadastrar.CadastrarFragment;
import android.content.Intent;

import java.io.Serializable;

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

        this.arvoreUsuarios = new ArvoreBinaria<Usuario>();

        MyTask task = new MyTask(Usuario[].class);
        task.execute(Enderecos.GET_USUARIO);
        while (task.isTrabalhando());

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
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuarios", arvoreUsuarios);
                Fragment cadastro = new CadastrarFragment();
                cadastro.setArguments(bundle);
                FragmentManager f = getSupportFragmentManager();
                f.beginTransaction().replace(R.id.container, cadastro, MainActivity.F_CADASTRO).addToBackStack(MainActivity.M_CADASTRO).commit();
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

                final String senhaCriptografada = Criptografia.criptografar(senha);

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

                if (!busca.getSenha().equals(senhaCriptografada))
                {
                    Toast.makeText(getApplicationContext(), "Senha incorreta", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!busca.isPermitido())
                {
                    Toast.makeText(
                        getApplicationContext(),
                        "Usuário ainda não aceito pela empresa",
                        Toast.LENGTH_SHORT).show();
                    return;
                }


                Toast.makeText(getApplicationContext(),"Login efetuado com sucesso",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("usuario", (Serializable) busca);
                startActivity(intent);

            }
        });
    }
}
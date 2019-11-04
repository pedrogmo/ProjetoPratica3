package com.example.appandroidfotovoltaica.ui.loginactivity;

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
import com.example.appandroidfotovoltaica.classes.constantesdetransicao.ConstantesDeTransicao;
import com.example.appandroidfotovoltaica.classes.criptografia.Criptografia;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.ui.mainactivity.MainActivity;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.usuario.Usuario;
import com.example.appandroidfotovoltaica.ui.loginactivity.cadastrarusuario.CadastrarUsuarioFragment;
import com.example.appandroidfotovoltaica.ui.loginactivity.loginempresa.LoginEmpresaFragment;

import android.content.Intent;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {

    private ArvoreBinaria<Usuario> arvoreUsuarios;
    private EditText etEmailLogin, etSenhaLogin;
    private Button btnLogar;
    private TextView tvCadastrarLogin, tvTelaEmpresa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmailLogin = findViewById(R.id.etEmailLogin);
        etSenhaLogin = findViewById(R.id.etSenhaLogin);
        btnLogar = findViewById(R.id.btnLogar);
        tvCadastrarLogin = findViewById(R.id.tvCadastrarLogin);
        tvTelaEmpresa = findViewById(R.id.tvTelaEmpresa);

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
                Fragment cadastro = new CadastrarUsuarioFragment();
                cadastro.setArguments(bundle);
                FragmentManager f = getSupportFragmentManager();
                f.beginTransaction().replace(R.id.fragment_telaLogin, cadastro, ConstantesDeTransicao.F_CADASTRO_USUARIO).addToBackStack( ConstantesDeTransicao.M_CADASTRO_USUARIO).commit();
            }
        });
        tvTelaEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager f = getSupportFragmentManager();
                f.beginTransaction().replace(R.id.fragment_telaLogin, new LoginEmpresaFragment(), ConstantesDeTransicao.F_TELAEMPRESA).addToBackStack(ConstantesDeTransicao.M_TELAEMPRESA).commit();
            }
        });

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email =  etEmailLogin.getText().toString().trim();
                final String senha = etSenhaLogin.getText().toString();

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

    @Override
    public void onBackPressed()
    {
        FragmentManager fm = getSupportFragmentManager();

        if (fm.findFragmentByTag(ConstantesDeTransicao.F_CADASTRO_USUARIO) != null)
        {
            super.onBackPressed();
        }
        else if (fm.findFragmentByTag(ConstantesDeTransicao.F_USUARIO_EMPRESA) != null){
            Toast.makeText(this, "Usuario Empresa", Toast.LENGTH_SHORT).show();
            //fm.popBackStack(ConstantesDeTransicao.M_USUARIO_EMPRESA, 0);
            super.onBackPressed();
        }
        else if (fm.findFragmentByTag(ConstantesDeTransicao.F_EMPRESA_INDIVIDUAL) != null){
            Toast.makeText(this, "Empresa Individual", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
        else if (fm.findFragmentByTag(ConstantesDeTransicao.F_TELAEMPRESA) != null)
        {
            Toast.makeText(this, "Tela Empresa", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
        else if (fm.findFragmentByTag(ConstantesDeTransicao.F_CADASTRO_EMPRESA) != null){
            Toast.makeText(this, "Cadastro Empresa", Toast.LENGTH_SHORT).show();
            fm.popBackStack(ConstantesDeTransicao.M_CADASTRO_EMPRESA, 0);
        }

    }
}
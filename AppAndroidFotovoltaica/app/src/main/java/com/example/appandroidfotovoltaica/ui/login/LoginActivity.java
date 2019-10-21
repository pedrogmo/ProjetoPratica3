package com.example.appandroidfotovoltaica.ui.login;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appandroidfotovoltaica.Enderecos;
import com.example.appandroidfotovoltaica.MyTask;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.Usuario;
import com.example.appandroidfotovoltaica.ui.home.HomeFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etEmailLogin = findViewById(R.id.etEmailLogin);
        final EditText etSenhaLogin = findViewById(R.id.etSenhaLogin);
        final Button btnLogar = findViewById(R.id.btnLogar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etEmailLogin.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Digite os dados de login", Toast.LENGTH_SHORT).show();
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
                            getApplicationContext(),
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
                        Toast.makeText(getApplicationContext(),"Dados incorretos",Toast.LENGTH_SHORT).show();
                    }
                }

                Toast.makeText(getApplicationContext(),"Login efetuado com sucesso",Toast.LENGTH_SHORT).show();



            }
        });


    }
}

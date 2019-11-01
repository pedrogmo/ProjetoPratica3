package com.example.appandroidfotovoltaica;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.appandroidfotovoltaica.classes.constantesdetransicao.ConstantesDeTransicao;
import com.example.appandroidfotovoltaica.classes.usuario.Usuario;
import com.example.appandroidfotovoltaica.ui.clientes.ClientesFragment;
import com.example.appandroidfotovoltaica.ui.clientes.adicionarcliente.AdicionarClienteFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Usuario logado;
    private TextView tvNomeLogado, tvEmailLogado;
    private Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();
        logado = (Usuario)intent.getSerializableExtra("usuario");



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        //ABAIXO SÂO OS COMANDOS PARA TORNAR OS ITENS CLICÀVEIS
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_calculadora, R.id.nav_clientes, R.id.nav_propostas,
                R.id.nav_produtos, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        //AQUI VEM OS CÓDIGOS PARA NOME E EMAIL DO USUÁRIO NO NAVIGATION
        tvNomeLogado = findViewById(R.id.tvNomeLogado);
        tvEmailLogado = findViewById(R.id.tvEmailLogado);

        tvNomeLogado.setText(logado.getNome());
        tvEmailLogado.setText(logado.getEmail());

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        FragmentManager fm = getSupportFragmentManager();

        if (fm.findFragmentByTag(ConstantesDeTransicao.F_ADICIONAR_CLIENTE) != null)
            fm.popBackStack(ConstantesDeTransicao.M_ADICIONAR_CLIENTE, 0);

        else if (fm.findFragmentByTag(ConstantesDeTransicao.F_CLIENTE_INDIVIDUAL) != null)
            fm.popBackStack(ConstantesDeTransicao.M_CLIENTE_INDIVIDUAL, 0);

        else if (fm.findFragmentByTag(ConstantesDeTransicao.F_ADICIONAR_PRODUTO) != null)
            fm.popBackStack(ConstantesDeTransicao.M_ADICIONAR_PRODUTO, 0);

        else if (fm.findFragmentByTag(ConstantesDeTransicao.F_PRODUTO_INDIVIDUAL) != null)
            fm.popBackStack(ConstantesDeTransicao.M_PRODUTO_INDIVIDUAL, 0);

        else if (fm.findFragmentByTag(ConstantesDeTransicao.F_CADASTRO) != null)
            fm.popBackStack(ConstantesDeTransicao.M_CADASTRO, 0);

        else
            super.onBackPressed();
    }

    public Usuario getUsuario()
    {
        return this.logado;
    }
}

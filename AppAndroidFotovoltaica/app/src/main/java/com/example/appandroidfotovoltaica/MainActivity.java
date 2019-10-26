package com.example.appandroidfotovoltaica;

import android.content.Intent;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Usuario logado;
    private TextView tvNomeLogado, tvEmailLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
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
        moveTaskToBack(false);

        /*int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }*/

    }

    public Usuario getUsuario()
    {
        return this.logado;
    }
}

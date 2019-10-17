package com.example.appandroidfotovoltaica.ui.login.cadastrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.appandroidfotovoltaica.Cliente;
import com.example.appandroidfotovoltaica.Empresa;
import com.example.appandroidfotovoltaica.Enderecos;
import com.example.appandroidfotovoltaica.MyTask;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.ui.home.HomeFragment;
import com.example.appandroidfotovoltaica.ui.login.LoginViewModel;

public class Cadastrar extends Fragment {
    private CadastrarViewModel cadastrarViewModel;
    private Spinner spEmpresa;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cadastrarViewModel =
                ViewModelProviders.of(this).get(CadastrarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cadastrar, container, false);
        spEmpresa = root.findViewById(R.id.spEmpresa);
        MyTask task = new MyTask(Empresa[].class);
        task.execute(Enderecos.GET_EMPRESAS);
        while (task.isTrabalhando()) ;

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, task.getDados());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmpresa.setAdapter(adapter);*/





       return root;
    }

}


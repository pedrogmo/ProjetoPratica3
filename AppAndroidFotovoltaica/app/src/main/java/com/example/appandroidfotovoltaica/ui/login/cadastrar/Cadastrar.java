package com.example.appandroidfotovoltaica.ui.login.cadastrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.ui.home.HomeFragment;
import com.example.appandroidfotovoltaica.ui.login.LoginViewModel;

public class Cadastrar extends Fragment {
    private CadastrarViewModel cadastrarViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cadastrarViewModel =
                ViewModelProviders.of(this).get(CadastrarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cadastrar, container, false);


        return root;
    }

}


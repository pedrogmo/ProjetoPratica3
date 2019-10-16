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

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.ui.adicionarcliente.AdicionarClienteFragment;
import com.example.appandroidfotovoltaica.ui.home.HomeFragment;
import com.example.appandroidfotovoltaica.ui.home.HomeViewModel;
import com.example.appandroidfotovoltaica.ui.principalClientes.PrincipalClientesFragment;

public class Login extends Fragment {
    private LoginViewModel loginViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel =
                ViewModelProviders.of(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        final Button btnLogar = root.findViewById(R.id.btnLogar);
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_login, new HomeFragment());
                fragmentTransaction.commit();


            }
        });
        return root;
    }

}

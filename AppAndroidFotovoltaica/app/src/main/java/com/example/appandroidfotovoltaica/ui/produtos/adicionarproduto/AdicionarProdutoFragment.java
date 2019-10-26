package com.example.appandroidfotovoltaica.ui.produtos.adicionarproduto;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.ui.produtos.Categoria;

public class AdicionarProdutoFragment extends Fragment {

    private AdicionarProdutoViewModel mViewModel;
    private int categoriaProduto;

    public static AdicionarProdutoFragment newInstance() {
        return new AdicionarProdutoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(AdicionarProdutoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_adicionarproduto, container, false);

        Bundle bundle = getArguments();
        this.categoriaProduto = (int) bundle.getSerializable("categoria");

        Toast.makeText(
            getActivity().getApplicationContext(),
            Categoria.OPCOES_SPINNER[categoriaProduto],
            Toast.LENGTH_SHORT
        ).show();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AdicionarProdutoViewModel.class);
        // TODO: Use the ViewModel
    }

}

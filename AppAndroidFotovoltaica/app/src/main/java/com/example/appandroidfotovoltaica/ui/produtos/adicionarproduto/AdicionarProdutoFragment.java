package com.example.appandroidfotovoltaica.ui.produtos.adicionarproduto;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.ui.produtos.Categoria;
import com.example.appandroidfotovoltaica.ui.produtos.ProdutosFragment;

public class AdicionarProdutoFragment extends Fragment {

    private AdicionarProdutoViewModel mViewModel;
    private int categoriaProduto;
    private EditText
        etNome,
        etPreco,
        etDescricao;
    private TextView
        tvExcNome,
        tvExcPreco,
        tvExcDescricao;
    private Button btnAdicionar;

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

        this.etNome = (EditText) root.findViewById(R.id.etNomeProdutoAdd);
        this.etPreco = (EditText) root.findViewById(R.id.etPrecoProdutoAdd);
        this.etDescricao = (EditText) root.findViewById(R.id.etDescricaoProdutoAdd);
        this.tvExcNome = (TextView) root.findViewById(R.id.tvExceptionNomeProdutoAdd);
        this.tvExcPreco = (TextView) root.findViewById(R.id.tvExceptionPrecoProdutoAdd);
        this.tvExcDescricao = (TextView) root.findViewById(R.id.tvExceptionDescricaoProdutoAdd);
        this.btnAdicionar = (Button) root.findViewById(R.id.btnAdicionarProduto);

        Toast.makeText(
            getActivity().getApplicationContext(),
            Categoria.OPCOES_SPINNER[categoriaProduto],
            Toast.LENGTH_SHORT
        ).show();

        this.btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_adicionarproduto, new ProdutosFragment());
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AdicionarProdutoViewModel.class);
        // TODO: Use the ViewModel
    }

}

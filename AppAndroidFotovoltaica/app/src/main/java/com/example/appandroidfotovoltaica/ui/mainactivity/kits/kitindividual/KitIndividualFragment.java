package com.example.appandroidfotovoltaica.ui.mainactivity.kits.kitindividual;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.kit.Kit;

public class KitIndividualFragment extends Fragment {

    private KitIndividualViewModel mViewModel;

    private TextView tvNome;

    private Kit kitAtual;

    public static KitIndividualFragment newInstance() {
        return new KitIndividualFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(KitIndividualViewModel.class);
        View root = inflater.inflate(R.layout.fragment_kitindividual, container, false);

        tvNome = root.findViewById(R.id.tvNomeKit);

        Bundle bundle = getArguments();
        kitAtual = (Kit) bundle.getSerializable("kit");

        tvNome.setText(kitAtual.getNome());

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(KitIndividualViewModel.class);
        // TODO: Use the ViewModel
    }

}

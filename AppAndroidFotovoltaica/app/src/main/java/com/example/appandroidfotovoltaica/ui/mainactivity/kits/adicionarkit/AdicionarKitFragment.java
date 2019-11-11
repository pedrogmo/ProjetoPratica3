package com.example.appandroidfotovoltaica.ui.mainactivity.kits.adicionarkit;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.ui.mainactivity.kits.kitindividual.KitIndividualViewModel;

public class AdicionarKitFragment extends Fragment {

    private AdicionarKitViewModel mViewModel;

    public static AdicionarKitFragment newInstance() {
        return new AdicionarKitFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(AdicionarKitViewModel.class);
        View root = inflater.inflate(R.layout.fragment_adicionarkit, container, false);



        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AdicionarKitViewModel.class);
        // TODO: Use the ViewModel
    }

}

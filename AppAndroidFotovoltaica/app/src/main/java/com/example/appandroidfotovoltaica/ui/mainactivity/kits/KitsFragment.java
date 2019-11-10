package com.example.appandroidfotovoltaica.ui.mainactivity.kits;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appandroidfotovoltaica.R;

public class KitsFragment extends Fragment {

    private KitsViewModel mViewModel;

    public static KitsFragment newInstance() {
        return new KitsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(KitsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_kits, container, false);



        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(KitsViewModel.class);
        // TODO: Use the ViewModel
    }

}

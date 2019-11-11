package com.example.appandroidfotovoltaica.ui.mainactivity.kits;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.kit.Kit;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.ui.mainactivity.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class KitsFragment extends Fragment {

    private KitsViewModel mViewModel;
    private ListView lvKits;
    private FloatingActionButton fabAdicionar;

    private Kit[] kits;

    public static KitsFragment newInstance() {
        return new KitsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(KitsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_kits, container, false);

        lvKits = root.findViewById(R.id.lvListaKits);
        fabAdicionar = root.findViewById(R.id.fabNovoKit);

        MyTask task = new MyTask(Kit[].class);
        int codEmp = ((MainActivity)getActivity()).getUsuario().getCodEmpresa();
        task.execute(Enderecos.GET_KIT + "/" + codEmp);
        while(task.isTrabalhando()) ;
        kits = (Kit[]) task.getDados();

        ArrayList<String> alNomeKits = new ArrayList<String>();
        for(Kit k : kits)
            alNomeKits.add(k.getNome());

        ArrayAdapter<String> adapterKits = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item,
                alNomeKits);
        adapterKits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lvKits.setAdapter(adapterKits);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(KitsViewModel.class);
        // TODO: Use the ViewModel
    }

}

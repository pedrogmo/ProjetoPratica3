package com.example.appandroidfotovoltaica.ui.mainactivity.kits;

import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.constantesdetransicao.ConstantesDeTransicao;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.kit.Kit;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.ui.mainactivity.MainActivity;
import com.example.appandroidfotovoltaica.ui.mainactivity.kits.adicionarkit.AdicionarKitFragment;
import com.example.appandroidfotovoltaica.ui.mainactivity.kits.kitindividual.KitIndividualFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class KitsFragment extends Fragment {
    private ListView lvKits;
    private FloatingActionButton fabAdicionar;

    private Kit[] kits;

    public static KitsFragment newInstance() {
        return new KitsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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

        lvKits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                AdapterView<?> adapterView,
                View view,
                int i,
                long l)
            {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("kit", kits[i]);
                KitIndividualFragment fragment = new KitIndividualFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_kits, fragment, ConstantesDeTransicao.F_KIT_INDIVIDUAL).addToBackStack(ConstantesDeTransicao.M_KIT_INDIVIDUAL).commit();
            }
        });

        fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_kits, new AdicionarKitFragment(), ConstantesDeTransicao.F_ADICIONAR_KIT).addToBackStack(ConstantesDeTransicao.M_ADICIONAR_KIT).commit();
            }
        });

        return root;
    }
}

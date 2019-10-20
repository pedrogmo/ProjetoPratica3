package com.example.appandroidfotovoltaica.ui.produtos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appandroidfotovoltaica.Enderecos;
import com.example.appandroidfotovoltaica.Inversor;
import com.example.appandroidfotovoltaica.Modulo;
import com.example.appandroidfotovoltaica.MyTask;
import com.example.appandroidfotovoltaica.R;

public class ProdutosFragment extends Fragment {

    private ProdutosViewModel toolsViewModel;

    private static String[] opcoesSpinner =
    {
        "Módulo", "Inversor"
    };

    private Spinner spCategoria;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ProdutosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_produtos, container, false);
        /*final TextView textView = root.findViewById(R.id.text_tools);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        spCategoria = (Spinner) root.findViewById(R.id.spCategoria);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, opcoesSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapter);

        MyTask task = new MyTask(Modulo[].class);
        task.execute(Enderecos.GET_MODULOS);
        while(task.isTrabalhando()) ;
        Modulo[] arrModulo = (Modulo[]) task.getDados();

        Log.d("MSG", arrModulo[0].toString());

        task = new MyTask(Inversor[].class);
        task.execute(Enderecos.GET_INVERSORES);
        while(task.isTrabalhando()) ;
        Inversor[] arrInversor = (Inversor[]) task.getDados();

        Log.d("MSG", arrInversor[0].toString());

        return root;
    }
}
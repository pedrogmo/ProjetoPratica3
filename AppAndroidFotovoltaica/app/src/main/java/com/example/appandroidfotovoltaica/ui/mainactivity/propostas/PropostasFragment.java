package com.example.appandroidfotovoltaica.ui.mainactivity.propostas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.adapters.clientearrayadapter.ClienteArrayAdapter;
import com.example.appandroidfotovoltaica.classes.cliente.Cliente;
import com.example.appandroidfotovoltaica.classes.constantesdetransicao.ConstantesDeTransicao;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.classes.proposta.Proposta;
import com.example.appandroidfotovoltaica.ui.mainactivity.MainActivity;
import com.example.appandroidfotovoltaica.ui.mainactivity.propostas.visualizarproposta.VisualizarPropostaFragment;

import java.util.ArrayList;


public class PropostasFragment extends Fragment {

    private static final int STORAGE_CODE = 1000;

    private Button btnGerar;
    private ListView lvProposta;
    Proposta[] arrProposta;
    EditText etBuscarProposta;
    private ArrayList<String> alNomes;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_propostas, container, false);

        this.lvProposta = (ListView) root.findViewById(R.id.lvListaPropostas);
        etBuscarProposta = root.findViewById(R.id.etBuscarProposta);

        /* BUSCA A PROPOSTA*/
        MyTask task = new MyTask(Proposta[].class);
        task.execute(Enderecos.GET_PROPOSTA + "/" + ((MainActivity) getActivity()).getUsuario().getCodigo());
        while(task.isTrabalhando()) ;
        arrProposta = (Proposta[]) task.getDados();

        alNomes = new ArrayList<String>();
        for(Proposta p : arrProposta){
            alNomes.add(p.getNome());
        }

        this.lvProposta.setAdapter(
            new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                alNomes)
        );

        this.lvProposta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("proposta", arrProposta[i]);
                VisualizarPropostaFragment fragment = new VisualizarPropostaFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_propostas,
                    fragment,
                    ConstantesDeTransicao.F_PROPOSTAS_VISUALIZAR)
                    .addToBackStack(ConstantesDeTransicao.M_PROPOSTAS_VISUALIZAR).commit();
            }

        });
        etBuscarProposta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                alNomes.clear();
                for(Proposta p : arrProposta)
                    if (p.getNome().toUpperCase().contains(String.valueOf(charSequence).toUpperCase()))
                    {
                        alNomes.add(p.getNome());
                    }

                lvProposta.setAdapter(new ArrayAdapter<String>(
                        getActivity().getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        alNomes)
                );
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /*this.btnGerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                {
                    if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);
                    }
                    else
                    {
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_propostas, new VisualizarPropostaFragment(), ConstantesDeTransicao.F_PROPOSTAS_VISUALIZAR).addToBackStack(ConstantesDeTransicao.M_PROPOSTAS_VISUALIZAR).commit();
                    }
                }
                else
                {

                }

            }


        });*/
        return root;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case STORAGE_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                }
                else
                {
                    Toast.makeText(
                        getActivity().getApplicationContext(),
                        "Acesso negado.",
                        Toast.LENGTH_SHORT).show();
                }
            }break;
        }
    }
}
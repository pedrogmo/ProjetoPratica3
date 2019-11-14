package com.example.appandroidfotovoltaica.ui.mainactivity.kits.adicionarkit;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.categoria.Categoria;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.classes.produto.cabo.Cabo;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.bombasolar.BombaSolar;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.inversor.Inversor;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.modulo.Modulo;
import com.example.appandroidfotovoltaica.classes.produto.fixacao.Fixacao;
import com.example.appandroidfotovoltaica.classes.produto.stringbox.StringBox;
import com.example.appandroidfotovoltaica.ui.mainactivity.MainActivity;
import com.example.appandroidfotovoltaica.ui.mainactivity.kits.kitindividual.KitIndividualViewModel;

import java.util.ArrayList;

public class AdicionarKitFragment extends Fragment {

    private AdicionarKitViewModel mViewModel;

    private Spinner spCategoria;
    private AutoCompleteTextView actvProduto;
    private EditText etQtd;
    private Button btnAdicionarProduto;
    private EditText etNomeKit;
    private Button btnConcluir;
    private ImageButton btnMais, btnMenos;

    private Modulo[] modulos;
    private Inversor[] inversores;
    private StringBox[] stringBoxes;
    private Fixacao[] fixacoes;
    private BombaSolar[] bombasSolares;
    private Cabo[] cabos;

    private ArrayList<String> nomesProdutos;

    public static AdicionarKitFragment newInstance() {
        return new AdicionarKitFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(AdicionarKitViewModel.class);
        View root = inflater.inflate(R.layout.fragment_adicionarkit, container, false);

        spCategoria = root.findViewById(R.id.spCategoriaKit);
        actvProduto = root.findViewById(R.id.actvProdutoKit);
        etQtd = root.findViewById(R.id.etQtd);
        btnAdicionarProduto = root.findViewById(R.id.btnAdicionarProduto);
        etNomeKit = root.findViewById(R.id.etNomeKitAdicionar);
        btnConcluir = root.findViewById(R.id.btnConcluirKit);
        btnMais = root.findViewById(R.id.btnMais);
        btnMenos = root.findViewById(R.id.btnMenos);

        btnMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    int qtd = Integer.parseInt(etQtd.getText().toString());
                    if (qtd >= Integer.MAX_VALUE){
                        btnMais.setVisibility(View.INVISIBLE);
                    }
                    else{
                        qtd++;
                        etQtd.setText(new Integer(qtd).toString());
                        btnMenos.setVisibility(View.VISIBLE);
                    }
                }catch(Exception e){}
            }
        });
        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int qtd = Integer.parseInt(etQtd.getText().toString());
                    if (qtd <= 1) {
                        btnMais.setVisibility(View.INVISIBLE);
                    } else {
                        qtd--;
                        etQtd.setText(new Integer(qtd).toString());
                        btnMais.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e){}
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, Categoria.OPCOES_SPINNER);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapter);

        fazerBuscas();

        nomesProdutos = new ArrayList<String>();

        for(Modulo m : modulos)
            nomesProdutos.add(m.getNome());

        atualizaLista();

        this.spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                actvProduto.setText("");
                switch(position)
                {
                    case 0:
                        nomesProdutos.clear();
                        for(Modulo m : modulos)
                            nomesProdutos.add(m.getNome());
                        atualizaLista();
                        break;

                    case 1:
                        nomesProdutos.clear();
                        for(Inversor i : inversores)
                            nomesProdutos.add(i.getNome());
                        atualizaLista();
                        break;

                    case 2:
                        nomesProdutos.clear();
                        for(StringBox s : stringBoxes)
                            nomesProdutos.add(s.getNome());
                        atualizaLista();
                        break;

                    case 3:
                        nomesProdutos.clear();
                        for(Fixacao f : fixacoes)
                            nomesProdutos.add(f.getNome());
                        atualizaLista();
                        break;

                    case 4:
                        nomesProdutos.clear();
                        for(BombaSolar b : bombasSolares)
                            nomesProdutos.add(b.getNome());
                        atualizaLista();
                        break;

                    case 5:
                        nomesProdutos.clear();
                        for(Cabo c : cabos)
                            nomesProdutos.add(c.getNome());
                        atualizaLista();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AdicionarKitViewModel.class);
        // TODO: Use the ViewModel
    }

    private void atualizaLista()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            getActivity().getApplicationContext(),
            android.R.layout.simple_list_item_1,
            nomesProdutos
        );
        actvProduto.setAdapter(adapter);
    }

    private void fazerBuscas()
    {
        int codEmpresa = ((MainActivity) getActivity()).getUsuario().getCodEmpresa();

        MyTask task = new MyTask(Modulo[].class);
        task.execute(Enderecos.GET_MODULO + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        modulos = (Modulo[]) task.getDados();

        task = new MyTask(Inversor[].class);
        task.execute(Enderecos.GET_INVERSOR + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        inversores = (Inversor[]) task.getDados();

        task = new MyTask(StringBox[].class);
        task.execute(Enderecos.GET_STRINGBOX + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        stringBoxes = (StringBox[]) task.getDados();

        task = new MyTask(Fixacao[].class);
        task.execute(Enderecos.GET_FIXACAO + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        fixacoes = (Fixacao[]) task.getDados();

        task = new MyTask(BombaSolar[].class);
        task.execute(Enderecos.GET_BOMBASOLAR + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        bombasSolares = (BombaSolar[]) task.getDados();

        task = new MyTask(Cabo[].class);
        task.execute(Enderecos.GET_CABO + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        cabos = (Cabo[]) task.getDados();
    }
}

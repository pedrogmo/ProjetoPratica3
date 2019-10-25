package com.example.appandroidfotovoltaica.ui.produtos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appandroidfotovoltaica.BombaSolar;
import com.example.appandroidfotovoltaica.Cabo;
import com.example.appandroidfotovoltaica.Enderecos;
import com.example.appandroidfotovoltaica.Fixacao;
import com.example.appandroidfotovoltaica.Inversor;
import com.example.appandroidfotovoltaica.Modulo;
import com.example.appandroidfotovoltaica.MyTask;
import com.example.appandroidfotovoltaica.Produto;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.StringBox;

import java.util.ArrayList;

public class ProdutosFragment extends Fragment {

    private ProdutosViewModel toolsViewModel;
    private ArrayList<Produto> listaProdutos;
    private ListView lvProdutos;
    private Button btnAdicionar;
    private Spinner spCategoria;
    private int indOpcaoProduto;

    private static String[] opcoesSpinner =
    {
        "Módulo", "Inversor", "StringBox", "Fixação", "BombaSolar", "Cabo"
    };



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

        this.spCategoria = (Spinner) root.findViewById(R.id.spCategoria);
        this.lvProdutos = (ListView) root.findViewById(R.id.lvProdutos);
        this.btnAdicionar = (Button) root.findViewById(R.id.btnAdicionarProduto);

        this.listaProdutos = new ArrayList<Produto>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, opcoesSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapter);

        MyTask task1 = new MyTask(Modulo[].class);
        task1.execute(Enderecos.GET_MODULO);
        while(task1.isTrabalhando()) ;
        Modulo[] arr1 = (Modulo[]) task1.getDados();
        listaProdutos.clear();
        for(Modulo m : arr1)
            listaProdutos.add(m);

        this.spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                indOpcaoProduto = position;

                switch(indOpcaoProduto)
                {
                    case 0:
                        MyTask task1 = new MyTask(Modulo[].class);
                        task1.execute(Enderecos.GET_MODULO);
                        while(task1.isTrabalhando()) ;
                        Modulo[] arr1 = (Modulo[]) task1.getDados();
                        listaProdutos.clear();
                        for(Modulo m : arr1)
                            listaProdutos.add(m);
                        atualizaLista();
                        break;
                    case 1:
                        MyTask task2 = new MyTask(Inversor[].class);
                        task2.execute(Enderecos.GET_INVERSOR);
                        while(task2.isTrabalhando()) ;
                        Inversor[] arr2 = (Inversor[]) task2.getDados();
                        listaProdutos.clear();
                        for(Inversor i : arr2) {
                            Log.d("MSG", i.getNome());
                            listaProdutos.add(i);
                        }
                        atualizaLista();
                        break;
                    case 2:
                        MyTask task3 = new MyTask(StringBox[].class);
                        task3.execute(Enderecos.GET_STRINGBOX);
                        while(task3.isTrabalhando()) ;
                        StringBox[] arr3 = (StringBox[]) task3.getDados();
                        listaProdutos.clear();
                        for(StringBox s : arr3)
                            listaProdutos.add(s);
                        atualizaLista();
                        break;
                    case 3:
                        MyTask task4 = new MyTask(Fixacao[].class);
                        task4.execute(Enderecos.GET_FIXACAO);
                        while(task4.isTrabalhando()) ;
                        Fixacao[] arr4 = (Fixacao[]) task4.getDados();
                        listaProdutos.clear();
                        for(Fixacao f : arr4)
                            listaProdutos.add(f);
                        atualizaLista();
                        break;
                    case 4:
                        MyTask task5 = new MyTask(BombaSolar[].class);
                        task5.execute(Enderecos.GET_BOMBASOLAR);
                        while(task5.isTrabalhando()) ;
                        BombaSolar[] arr5 = (BombaSolar[]) task5.getDados();
                        listaProdutos.clear();
                        for(BombaSolar b : arr5)
                            listaProdutos.add(b);
                        atualizaLista();
                        break;
                    case 5:
                        MyTask task6 = new MyTask(Cabo[].class);
                        task6.execute(Enderecos.GET_CABO);
                        while(task6.isTrabalhando()) ;
                        Cabo[] arr6 = (Cabo[]) task6.getDados();
                        listaProdutos.clear();
                        for(Cabo c : arr6)
                            listaProdutos.add(c);
                        atualizaLista();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        this.lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                AdapterView<?> adapterView,
                View view,
                int i,
                long l)
            {

            }
        });

        this.btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        this.lvProdutos.setAdapter(new ProdutoArrayAdapter(
                getActivity().getApplicationContext(), this.listaProdutos
        ));

        return root;
    }

    private void atualizaLista()
    {
        this.lvProdutos.setAdapter(new ProdutoArrayAdapter(
                getActivity().getApplicationContext(), this.listaProdutos
        ));
    }
}
package com.example.appandroidfotovoltaica.ui.produtos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.appandroidfotovoltaica.classes.produto.equipamento.bombasolar.BombaSolar;
import com.example.appandroidfotovoltaica.classes.produto.cabo.Cabo;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.produto.fixacao.Fixacao;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.inversor.Inversor;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.modulo.Modulo;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.classes.produto.Produto;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.produto.stringbox.StringBox;
import com.example.appandroidfotovoltaica.ui.produtos.adicionarproduto.AdicionarProdutoFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProdutosFragment extends Fragment {

    private ProdutosViewModel toolsViewModel;
    private ArrayList<Produto> listaProdutos;
    private ListView lvProdutos;
    private FloatingActionButton fabAdicionar;
    private Spinner spCategoria;
    private int indOpcaoProduto;

    private Modulo[] arrModulo;
    private Inversor[] arrInversor;
    private StringBox[] arrStringBox;
    private Fixacao[] arrFixacao;
    private BombaSolar[] arrBombaSolar;
    private Cabo[] arrCabo;

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
        this.lvProdutos = (ListView) root.findViewById(R.id.lvListaProdutos);
        this.fabAdicionar = (FloatingActionButton) root.findViewById(R.id.fabNovoProduto);

        this.listaProdutos = new ArrayList<Produto>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, Categoria.OPCOES_SPINNER);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapter);

        fazerBuscas();

        listaProdutos.clear();
        for(Modulo m : arrModulo)
            listaProdutos.add(m);

        this.lvProdutos.setAdapter(new ProdutoArrayAdapter(
            getActivity().getApplicationContext(), this.listaProdutos
        ));

        this.spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                indOpcaoProduto = position;

                switch(indOpcaoProduto)
                {
                    case 0:
                        listaProdutos.clear();
                        for(Modulo m : arrModulo)
                            listaProdutos.add(m);
                        atualizaLista();
                        break;

                    case 1:
                        listaProdutos.clear();
                        for(Inversor i : arrInversor)
                            listaProdutos.add(i);
                        atualizaLista();
                        break;

                    case 2:
                        listaProdutos.clear();
                        for(StringBox s : arrStringBox)
                            listaProdutos.add(s);
                        atualizaLista();
                        break;

                    case 3:
                        listaProdutos.clear();
                        for(Fixacao f : arrFixacao)
                            listaProdutos.add(f);
                        atualizaLista();
                        break;

                    case 4:
                        listaProdutos.clear();
                        for(BombaSolar b : arrBombaSolar)
                            listaProdutos.add(b);
                        atualizaLista();
                        break;

                    case 5:
                        listaProdutos.clear();
                        for(Cabo c : arrCabo)
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

        this.fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("categoria", indOpcaoProduto);
                AdicionarProdutoFragment fragment = new AdicionarProdutoFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_produtos, fragment);
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    private void atualizaLista()
    {
        this.lvProdutos.setAdapter(new ProdutoArrayAdapter(
            getActivity().getApplicationContext(), this.listaProdutos
        ));
    }

    private void fazerBuscas()
    {
        MyTask task = new MyTask(Modulo[].class);
        task.execute(Enderecos.GET_MODULO);
        while(task.isTrabalhando()) ;
        arrModulo = (Modulo[]) task.getDados();

        task = new MyTask(Inversor[].class);
        task.execute(Enderecos.GET_INVERSOR);
        while(task.isTrabalhando()) ;
        arrInversor = (Inversor[]) task.getDados();

        task = new MyTask(StringBox[].class);
        task.execute(Enderecos.GET_STRINGBOX);
        while(task.isTrabalhando()) ;
        arrStringBox = (StringBox[]) task.getDados();

        task = new MyTask(Fixacao[].class);
        task.execute(Enderecos.GET_FIXACAO);
        while(task.isTrabalhando()) ;
        arrFixacao = (Fixacao[]) task.getDados();

        task = new MyTask(BombaSolar[].class);
        task.execute(Enderecos.GET_BOMBASOLAR);
        while(task.isTrabalhando()) ;
        arrBombaSolar = (BombaSolar[]) task.getDados();

        task = new MyTask(Cabo[].class);
        task.execute(Enderecos.GET_CABO);
        while(task.isTrabalhando()) ;
        arrCabo = (Cabo[]) task.getDados();
    }
}
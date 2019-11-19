package com.example.appandroidfotovoltaica.ui.mainactivity.kits.adicionarkit;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.categoria.Categoria;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.kit.Kit;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.classes.produto.Produto;
import com.example.appandroidfotovoltaica.classes.produto.cabo.Cabo;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.bombasolar.BombaSolar;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.inversor.Inversor;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.modulo.Modulo;
import com.example.appandroidfotovoltaica.classes.produto.fixacao.Fixacao;
import com.example.appandroidfotovoltaica.classes.produto.stringbox.StringBox;
import com.example.appandroidfotovoltaica.classes.produtoquantidade.ProdutoQuantidade;
import com.example.appandroidfotovoltaica.classes.verificadora.Verificadora;
import com.example.appandroidfotovoltaica.ui.mainactivity.MainActivity;
import com.example.appandroidfotovoltaica.ui.mainactivity.kits.KitsFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdicionarKitFragment extends Fragment {

    private AdicionarKitViewModel mViewModel;

    private Spinner spCategoria;
    private AutoCompleteTextView actvProduto;
    private EditText etQtd;
    private Button btnAdicionar, btnConcluir;
    private EditText etNomeKit;
    private ImageButton btnMais, btnMenos;

    private Modulo[] modulos;
    private Inversor[] inversores;
    private StringBox[] stringBoxes;
    private Fixacao[] fixacoes;
    private BombaSolar[] bombasSolares;
    private Cabo[] cabos;

    private int indCategoria = 0;

    private ArrayList<ProdutoQuantidade> produtosAdicionados;

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
        btnAdicionar = root.findViewById(R.id.btnAdicionarProdutoKit);
        etNomeKit = root.findViewById(R.id.etNomeKitAdicionar);
        btnConcluir = root.findViewById(R.id.btnConcluirKit);
        btnMais = root.findViewById(R.id.btnMais);
        btnMenos = root.findViewById(R.id.btnMenos);

        produtosAdicionados = new ArrayList<ProdutoQuantidade>();

        if (Integer.parseInt(etQtd.getText().toString()) > 1)
            btnMenos.setVisibility(View.VISIBLE);
        btnMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    int qtd = Integer.parseInt(etQtd.getText().toString());

                    qtd++;
                    if (Verificadora.isQtdValida(qtd))
                    {
                        etQtd.setText(new Integer(qtd).toString());
                    }

                }catch(Exception e){}
            }
        });
        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int qtd = Integer.parseInt(etQtd.getText().toString());
                    qtd--;
                    if (Verificadora.isQtdValida(qtd))
                    {
                        etQtd.setText(new Integer(qtd).toString());
                    }
                }catch (Exception e){}
            }
        });

        etQtd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int qtd;
                try{
                    qtd = Integer.parseInt(charSequence.toString());
                }
                catch (Exception e){
                    qtd = 0;
                }
                
                if (qtd==1)
                    btnMenos.setVisibility(View.INVISIBLE);
                else
                    if (qtd==9999999)
                        btnMais.setVisibility(View.INVISIBLE);
                    else{
                        btnMais.setVisibility(View.VISIBLE);
                        btnMenos.setVisibility(View.VISIBLE);
                    }


            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                indCategoria = position;

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

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtd = 0;
                try
                {
                    qtd = Integer.parseInt(etQtd.getText().toString().trim());
                }
                catch(Exception exc)
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Quantidade inválida", Toast.LENGTH_SHORT).show();
                    return;
                }

                Produto p = null;
                String nome = actvProduto.getText().toString();

                switch(indCategoria)
                {
                    case 0:
                        p = buscaProduto(nome, modulos);
                        break;
                    case 1:
                        p = buscaProduto(nome, inversores);
                        break;
                    case 2:
                        p = buscaProduto(nome, stringBoxes);
                        break;
                    case 3:
                        p = buscaProduto(nome, fixacoes);
                        break;
                    case 4:
                        p = buscaProduto(nome, bombasSolares);
                        break;
                    case 5:
                        p = buscaProduto(nome, cabos);
                        break;

                }

                if (p == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Produto não existe", Toast.LENGTH_SHORT).show();
                    return;
                }

                ProdutoQuantidade pq = null;
                try{pq = new ProdutoQuantidade(p, qtd);}
                catch(Exception exc){}
                produtosAdicionados.add(pq);

                Toast.makeText(getActivity().getApplicationContext(), "Produto adicionado", Toast.LENGTH_SHORT).show();
            }
        });

        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nome = etNomeKit.getText().toString().trim();
                final int codEmpresa = ((MainActivity)getActivity()).getUsuario().getCodEmpresa();
                if (nome==null||nome.equals(""))
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Nome inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());
                StringRequest postRequest = new StringRequest(
                        Request.Method.POST,
                        Enderecos.POST_KIT,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                MyTask task = new MyTask(Kit[].class);
                                task.execute(Enderecos.GET_KIT + "/" + codEmpresa);
                                while(task.isTrabalhando()) ;
                                Kit[] kits = (Kit[]) task.getDados();

                                final Kit kitAdicionado = buscaKit(nome, kits);
                                if (kitAdicionado == null)
                                {
                                    Toast.makeText(getActivity().getApplicationContext(), "Kit não foi pego", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                for(final ProdutoQuantidade pq : produtosAdicionados)
                                {
                                    String urlPostProduto = "";

                                    Class<? extends Produto> categoria = pq.getProduto().getClass();

                                    if (categoria == Modulo.class)
                                        urlPostProduto = Enderecos.POST_KITMODULO;

                                    else if (categoria == Inversor.class)
                                        urlPostProduto = Enderecos.POST_KITINVERSOR;

                                    else if (categoria == StringBox.class)
                                        urlPostProduto = Enderecos.POST_KITSTRINGBOX;

                                    else if (categoria == Fixacao.class)
                                        urlPostProduto = Enderecos.POST_KITFIXACAO;

                                    else if (categoria == BombaSolar.class)
                                        urlPostProduto = Enderecos.POST_KITBOMBASOLAR;

                                    else if (categoria == Cabo.class)
                                        urlPostProduto = Enderecos.POST_KITCABO;

                                    Log.d("MSG", urlPostProduto);


                                    StringRequest postRequest2 = new StringRequest(
                                            Request.Method.POST,
                                            urlPostProduto,
                                            new Response.Listener<String>()
                                            {
                                                @Override
                                                public void onResponse(String response) {
                                                    Log.d("MSG", pq.getProduto().getNome());
                                                }
                                            },
                                            new Response.ErrorListener()
                                            {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    // error
                                                    Toast.makeText(
                                                            getActivity().getApplicationContext(),
                                                            "Erro ao produto kit",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                    ) {
                                        @Override
                                        protected Map<String, String> getParams()
                                        {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("codKit", kitAdicionado.getCodigo() + "");
                                            params.put("codProduto", pq.getProduto().getCodigo() + "");
                                            params.put("quantidade", pq.getQuantidade() + "");
                                            return params;
                                        }
                                    };
                                    QUEUE.add(postRequest2);
                                }

                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        "Kit concluído",
                                        Toast.LENGTH_SHORT).show();

                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_adicionarkit, new KitsFragment());
                                fragmentTransaction.commit();
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Erro ao inserir kit",
                                    Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("codEmpresa", codEmpresa + "");
                        params.put("nome", nome);
                        return params;
                    }
                };
                QUEUE.add(postRequest);
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

    public Kit buscaKit(
        String nome,
        Kit[] kits)
    {
        for(Kit k : kits)
            if (k.getNome().equals(nome))
                return k;
        return null;
    }

    private Produto buscaProduto(
        String nome,
        Produto[] produtos)
    {
        for(Produto p : produtos)
            if (p.getNome().equals(nome))
                return p;
        return null;
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

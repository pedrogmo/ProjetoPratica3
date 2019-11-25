package com.example.appandroidfotovoltaica.ui.mainactivity.kits.kitindividual;

import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.adapters.produtoquantidadearrayadpter.ProdutoQuantidadeArrayAdapter;
import com.example.appandroidfotovoltaica.classes.categoria.Categoria;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.kit.Kit;
import com.example.appandroidfotovoltaica.classes.kitproduto.KitProduto;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.classes.produto.Produto;
import com.example.appandroidfotovoltaica.classes.produto.cabo.Cabo;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.bombasolar.BombaSolar;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.inversor.Inversor;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.modulo.Modulo;
import com.example.appandroidfotovoltaica.classes.produto.fixacao.Fixacao;
import com.example.appandroidfotovoltaica.classes.produto.stringbox.StringBox;
import com.example.appandroidfotovoltaica.classes.produtoquantidade.ProdutoQuantidade;
import com.example.appandroidfotovoltaica.ui.mainactivity.MainActivity;
import com.example.appandroidfotovoltaica.ui.mainactivity.kits.KitsFragment;

import java.util.ArrayList;

public class KitIndividualFragment extends Fragment {

    private TextView tvNome;
    private ListView lvProdutos;
    private Button btnExcluir;

    private Kit kitAtual;

    private Modulo[] arrModulo;
    private Inversor[] arrInversor;
    private StringBox[] arrStringBox;
    private Fixacao[] arrFixacao;
    private BombaSolar[] arrBombaSolar;
    private Cabo[] arrCabo;

    public static KitIndividualFragment newInstance() {
        return new KitIndividualFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_kitindividual, container, false);

        tvNome = root.findViewById(R.id.tvNomeKit);
        lvProdutos = root.findViewById(R.id.lvListaProdutosKits);
        btnExcluir = root.findViewById(R.id.btnExcluirKit);

        Bundle bundle = getArguments();
        kitAtual = (Kit) bundle.getSerializable("kit");
        tvNome.setText(kitAtual.getNome());

        fazerBuscas();

        this.lvProdutos.setAdapter(new ProdutoQuantidadeArrayAdapter(
                getActivity().getApplicationContext(), produtosDoKit()
        ));

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Deseja mesmo excluir?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                    "Sim",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());

                            StringRequest dr = new StringRequest(
                                    Request.Method.DELETE,
                                    Enderecos.DELETE_KIT + "/" + kitAtual.getCodigo(),
                                    new Response.Listener<String>()
                                    {
                                        @Override
                                        public void onResponse(String response) {
                                            // response
                                            Toast.makeText(
                                                    getActivity().getApplicationContext(),
                                                    "Kit excluído",
                                                    Toast.LENGTH_SHORT).show();

                                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                            fragmentTransaction.replace(R.id.fragment_kitindividual, new KitsFragment());
                                            fragmentTransaction.commit();
                                        }
                                    },
                                    new Response.ErrorListener()
                                    {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(
                                                getActivity().getApplicationContext(),
                                                "Erro ao excluir",
                                                Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            );
                            QUEUE.add(dr);

                            dialog.cancel();
                        }
                    });

                builder1.setNegativeButton(
                        "Não",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        return root;
    }

    private void fazerBuscas()
    {
        int codEmpresa = ((MainActivity) getActivity()).getUsuario().getCodEmpresa();

        MyTask task = new MyTask(Modulo[].class);
        task.execute(Enderecos.GET_MODULO + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        arrModulo = (Modulo[]) task.getDados();

        task = new MyTask(Inversor[].class);
        task.execute(Enderecos.GET_INVERSOR + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        arrInversor = (Inversor[]) task.getDados();

        task = new MyTask(StringBox[].class);
        task.execute(Enderecos.GET_STRINGBOX + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        arrStringBox = (StringBox[]) task.getDados();

        task = new MyTask(Fixacao[].class);
        task.execute(Enderecos.GET_FIXACAO + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        arrFixacao = (Fixacao[]) task.getDados();

        task = new MyTask(BombaSolar[].class);
        task.execute(Enderecos.GET_BOMBASOLAR + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        arrBombaSolar = (BombaSolar[]) task.getDados();

        task = new MyTask(Cabo[].class);
        task.execute(Enderecos.GET_CABO + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        arrCabo = (Cabo[]) task.getDados();
    }

    private ArrayList<ProdutoQuantidade> produtosDoKit()
    {
        ArrayList<ProdutoQuantidade> ret = new ArrayList<ProdutoQuantidade>();

        for(int i=0; i<6; ++i)
        {
            KitProduto[] kitProdutos;
            MyTask task = new MyTask(KitProduto[].class);
            task.execute(Categoria.ROTAS_GET_KITPRODUTO[i] + "/" + kitAtual.getCodigo());
            while (task.isTrabalhando()) ;
            kitProdutos = (KitProduto[]) task.getDados();

            for (KitProduto kp : kitProdutos)
            {
                Produto p = produtoCodigo(arrModulo, kp.getCodProduto());
                if (p != null)
                {
                    try
                    {
                        ret.add(new ProdutoQuantidade(p, kp.getQuantidade()));
                    }
                    catch (Exception exc) {}
                }
            }
        }
        return ret;
    }

    private Produto produtoCodigo(
        Produto[] produtos,
        int codigo)
    {
        for(Produto p : produtos)
            if (p.getCodigo() == codigo)
                return p;
        return null;
    }
}

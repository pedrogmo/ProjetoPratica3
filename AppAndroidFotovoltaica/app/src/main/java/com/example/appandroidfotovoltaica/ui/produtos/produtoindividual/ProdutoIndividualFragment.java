package com.example.appandroidfotovoltaica.ui.produtos.produtoindividual;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.produto.Produto;
import com.example.appandroidfotovoltaica.ui.produtos.ProdutosFragment;

import java.util.HashMap;
import java.util.Map;

public class ProdutoIndividualFragment extends Fragment {

    private ProdutoIndividualViewModel mViewModel;

    private EditText etPreco;
    private TextView
        tvNome,
        tvDescricao,
        tvExcPreco;
    private Button
        btnAlterar,
        btnExcluir;

    private int categoriaProduto;
    private Produto produtoAtual;

    public static ProdutoIndividualFragment newInstance() {
        return new ProdutoIndividualFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(ProdutoIndividualViewModel.class);
        View root = inflater.inflate(R.layout.fragment_produtoindividual, container, false);

        Bundle bundle = getArguments();
        this.categoriaProduto = (int) bundle.getSerializable("categoria");
        this.produtoAtual = (Produto) bundle.getSerializable("produto");

        this.etPreco = (EditText) root.findViewById(R.id.etPrecoProdutoAlt);
        this.tvNome = (TextView) root.findViewById(R.id.tvNomeProdutoAlt);
        this.tvDescricao = (TextView) root.findViewById(R.id.tvDescricaoProdutoAlt);
        this.tvExcPreco = (TextView) root.findViewById(R.id.tvExceptionPrecoProdutoAlt);
        this.btnAlterar = (Button) root.findViewById(R.id.btnAlterarProduto);
        this.btnExcluir = (Button) root.findViewById(R.id.btnExcluirProduto);

        this.tvNome.setText(this.tvNome.getText() + produtoAtual.getNome());
        this.tvDescricao.setText(this.tvDescricao.getText() + produtoAtual.getDescricao());
        this.etPreco.setText(produtoAtual.getPreco() + "");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout l = (LinearLayout) root.findViewById(R.id.camposExtraProduto);
        TextView txt = new TextView(getActivity().getApplicationContext());
        txt.setLayoutParams(params);
        txt.setText("BOM DIA MEEIRO");
        txt.setTextSize(30);
        txt.setTextColor(Color.BLACK);
        l.addView(txt);

        this.btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvExcPreco.setText("");
                final float preco = Float.parseFloat(etPreco.getText().toString().trim());

                if (preco < 0.0f)
                {
                    tvExcPreco.setText("O preço deve ser positivo");
                    return;
                }

                final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());
                final String URL;

                switch(categoriaProduto)
                {
                    case 0:
                        URL = Enderecos.PATCH_MODULO;
                        break;
                    case 1:
                        URL = Enderecos.PATCH_INVERSOR;
                        break;
                    case 2:
                        URL = Enderecos.PATCH_STRINGBOX;
                        break;
                    case 3:
                        URL = Enderecos.PATCH_FIXACAO;
                        break;
                    case 4:
                        URL = Enderecos.PATCH_BOMBASOLAR;
                        break;
                    case 5:
                        URL = Enderecos.PATCH_CABO;
                        break;
                    default:
                        URL = "";
                        break;
                }

                StringRequest putRequest = new StringRequest(
                    Request.Method.PATCH,
                    URL + "/" + produtoAtual.getCodigo(),
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Produto alterado",
                                Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Erro ao alterar",
                                Toast.LENGTH_SHORT).show();
                        }
                    }
                )
                {

                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("preco", preco + "");
                        return params;
                    }

                };

                QUEUE.add(putRequest);
            }
        });

        this.btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());
                final String URL;

                switch(categoriaProduto)
                {
                    case 0:
                        URL = Enderecos.DELETE_MODULO;
                        break;
                    case 1:
                        URL = Enderecos.DELETE_INVERSOR;
                        break;
                    case 2:
                        URL = Enderecos.DELETE_STRINGBOX;
                        break;
                    case 3:
                        URL = Enderecos.DELETE_FIXACAO;
                        break;
                    case 4:
                        URL = Enderecos.DELETE_BOMBASOLAR;
                        break;
                    case 5:
                        URL = Enderecos.DELETE_CABO;
                        break;
                    default:
                        URL = "";
                        break;
                }

                StringRequest dr = new StringRequest(
                    Request.Method.DELETE,
                    URL + "/" + produtoAtual.getCodigo(),
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Produto excluído",
                                Toast.LENGTH_SHORT).show();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_produtoindividual, new ProdutosFragment());
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
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProdutoIndividualViewModel.class);
        // TODO: Use the ViewModel
    }

}

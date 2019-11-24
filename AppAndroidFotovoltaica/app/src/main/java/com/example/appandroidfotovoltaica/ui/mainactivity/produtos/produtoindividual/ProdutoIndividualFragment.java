package com.example.appandroidfotovoltaica.ui.mainactivity.produtos.produtoindividual;

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
import com.example.appandroidfotovoltaica.classes.categoria.Categoria;
import com.example.appandroidfotovoltaica.classes.produto.Produto;
import com.example.appandroidfotovoltaica.classes.produto.cabo.Cabo;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.EquipamentoFotovoltaico;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.bombasolar.BombaSolar;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.inversor.Inversor;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.modulo.Modulo;
import com.example.appandroidfotovoltaica.classes.produto.stringbox.StringBox;
import com.example.appandroidfotovoltaica.ui.mainactivity.produtos.ProdutosFragment;

import java.util.HashMap;
import java.util.Map;

public class ProdutoIndividualFragment extends Fragment {

    private EditText etPreco;
    private TextView
        tvNome,
        tvDescricao,
        tvExcPreco;
    private Button
        btnAlterar,
        btnExcluir;
    private LinearLayout llCamposExtra;

    private Class<? extends Produto> categoriaProduto;
    private Produto produtoAtual;

    public static ProdutoIndividualFragment newInstance() {
        return new ProdutoIndividualFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_produtoindividual, container, false);

        Bundle bundle = getArguments();
        this.produtoAtual = (Produto) bundle.getSerializable("produto");
        this.categoriaProduto = this.produtoAtual.getClass();

        this.etPreco = (EditText) root.findViewById(R.id.etPrecoProdutoAlt);
        this.tvNome = (TextView) root.findViewById(R.id.tvNomeProdutoAlt);
        this.tvDescricao = (TextView) root.findViewById(R.id.tvDescricaoProdutoAlt);
        this.tvExcPreco = (TextView) root.findViewById(R.id.tvExceptionPrecoProdutoAlt);
        this.btnAlterar = (Button) root.findViewById(R.id.btnAlterarProduto);
        this.btnExcluir = (Button) root.findViewById(R.id.btnExcluirProduto);

        this.tvNome.setText(this.tvNome.getText() + produtoAtual.getNome());
        this.tvDescricao.setText(this.tvDescricao.getText() + produtoAtual.getDescricao());
        this.etPreco.setText(produtoAtual.getPreco() + "");
        this.llCamposExtra = (LinearLayout) root.findViewById(R.id.camposExtraProdutoAlt);

        this.adiconarCamposExtra();

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
                final String URL = Categoria.ROTAS_PATCH_PRODUTO[Categoria.getIndice(categoriaProduto)];

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
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Deseja mesmo excluir?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());
                                final String URL = Categoria.ROTAS_DELETE_PRODUTO[Categoria.getIndice(categoriaProduto)];

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
    private void adicionarTextView(
        String texto,
        LinearLayout.LayoutParams params)
    {
        TextView txt = new TextView(getActivity().getApplicationContext());
        txt.setLayoutParams(params);
        txt.setText(texto);
        this.llCamposExtra.addView(txt);
    }

    private void adiconarCamposExtra()
    {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

        if (this.produtoAtual instanceof EquipamentoFotovoltaico)
        {
            EquipamentoFotovoltaico e = (EquipamentoFotovoltaico) produtoAtual;

            this.adicionarTextView("Altura: " + e.getAltura() + "m", params);
            this.adicionarTextView("Largura: " + e.getLargura() + "m", params);
            this.adicionarTextView("Profundiade: " + e.getProfundidade() + "m", params);
            this.adicionarTextView("Peso: " + e.getPeso() + "kg", params);

            if (this.categoriaProduto == BombaSolar.class)
            {
                BombaSolar b = (BombaSolar) produtoAtual;

                this.adicionarTextView("Tensão alimentação: " + b.getTensaoAlimentacao()  + "v", params);
                this.adicionarTextView("Temperatura máxima: " + b.getTemperaturaMaxima() + "°C", params);
                this.adicionarTextView("Altura máxima: " + b.getAlturaMaxima() + "m", params);
                this.adicionarTextView("Bombeamento máximo diário: " + b.getBombeamentoMaximoDiario() + "L", params);
                this.adicionarTextView("Diâmetro tubo: " + b.getDiametroTubo() + "mm", params);
            }
            else if (this.categoriaProduto == Inversor.class)
            {
                Inversor i = (Inversor) produtoAtual;

                this.adicionarTextView("Eficiência máxima: " + i.getEficienciaMaxima(), params);
            }
            else if (this.categoriaProduto == Modulo.class)
            {
                Modulo m = (Modulo) produtoAtual;

                this.adicionarTextView("Potência: " + m.getPotencia() + "w", params);
            }
        }
        else if (this.categoriaProduto == Cabo.class)
        {
            Cabo c = (Cabo) produtoAtual;

            this.adicionarTextView("Comprimento: " + c.getComprimento() + "m", params);
            this.adicionarTextView("Diâmetro: " + c.getDiametro() + "mm", params);
            this.adicionarTextView("Condução: " + c.getConducao() + "v", params);
        }
        else if (this.categoriaProduto == StringBox.class)
        {
            StringBox s = (StringBox) produtoAtual;

            this.adicionarTextView("Tipo: " + s.getTipo(), params);
            this.adicionarTextView("Número de polos: " + s.getNumeroPolos(), params);
            this.adicionarTextView("Tensão máxima: " + s.getTensaoMaxima() + "v", params);
            this.adicionarTextView("Corrente nominal: " + s.getCorrenteNominal() + "A", params);
        }
    }
}

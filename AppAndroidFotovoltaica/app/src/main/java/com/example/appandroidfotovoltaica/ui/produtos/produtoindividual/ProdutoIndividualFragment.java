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
import com.example.appandroidfotovoltaica.classes.produto.cabo.Cabo;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.EquipamentoFotovoltaico;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.bombasolar.BombaSolar;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.inversor.Inversor;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.modulo.Modulo;
import com.example.appandroidfotovoltaica.classes.produto.fixacao.Fixacao;
import com.example.appandroidfotovoltaica.classes.produto.stringbox.StringBox;
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

    private final int TAMANHO_FONTE = 15;

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

        adiconarCamposExtra((LinearLayout) root.findViewById(R.id.camposExtraProduto));

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

    public void adiconarCamposExtra(LinearLayout l)
    {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        if (produtoAtual instanceof EquipamentoFotovoltaico)
        {
            EquipamentoFotovoltaico e = (EquipamentoFotovoltaico) produtoAtual;

            TextView txt = new TextView(getActivity().getApplicationContext());
            txt.setLayoutParams(params);
            txt.setText("Altura: " + e.getAltura());
            txt.setTextSize(TAMANHO_FONTE);
            txt.setTextColor(Color.BLACK);
            l.addView(txt);

            txt = new TextView(getActivity().getApplicationContext());
            txt.setLayoutParams(params);
            txt.setText("Largura: " + e.getLargura());
            txt.setTextSize(TAMANHO_FONTE);
            txt.setTextColor(Color.BLACK);
            l.addView(txt);

            txt = new TextView(getActivity().getApplicationContext());
            txt.setLayoutParams(params);
            txt.setText("Profundiade: " + e.getProfundidade());
            txt.setTextSize(TAMANHO_FONTE);
            txt.setTextColor(Color.BLACK);
            l.addView(txt);

            txt = new TextView(getActivity().getApplicationContext());
            txt.setLayoutParams(params);
            txt.setText("Peso: " + e.getPeso());
            txt.setTextSize(TAMANHO_FONTE);
            txt.setTextColor(Color.BLACK);
            l.addView(txt);

            if (produtoAtual instanceof BombaSolar)
            {
                BombaSolar b = (BombaSolar) produtoAtual;

                txt = new TextView(getActivity().getApplicationContext());
                txt.setLayoutParams(params);
                txt.setText("Tensão alimentação: " + b.getTensaoAlimentacao());
                txt.setTextSize(TAMANHO_FONTE);
                txt.setTextColor(Color.BLACK);
                l.addView(txt);

                txt = new TextView(getActivity().getApplicationContext());
                txt.setLayoutParams(params);
                txt.setText("Temperatura máxima: " + b.getTemperaturaMaxima());
                txt.setTextSize(TAMANHO_FONTE);
                txt.setTextColor(Color.BLACK);
                l.addView(txt);

                txt = new TextView(getActivity().getApplicationContext());
                txt.setLayoutParams(params);
                txt.setText("Altura máxima: " + b.getAlturaMaxima());
                txt.setTextSize(TAMANHO_FONTE);
                txt.setTextColor(Color.BLACK);
                l.addView(txt);

                txt = new TextView(getActivity().getApplicationContext());
                txt.setLayoutParams(params);
                txt.setText("Bombeamento máximo diário: " + b.getBombeamentoMaximoDiario());
                txt.setTextSize(TAMANHO_FONTE);
                txt.setTextColor(Color.BLACK);
                l.addView(txt);

                txt = new TextView(getActivity().getApplicationContext());
                txt.setLayoutParams(params);
                txt.setText("Diâmetro tubo: " + b.getDiametroTubo());
                txt.setTextSize(TAMANHO_FONTE);
                txt.setTextColor(Color.BLACK);
                l.addView(txt);
            }
            else if (produtoAtual instanceof Inversor)
            {
                Inversor i = (Inversor) produtoAtual;

                txt = new TextView(getActivity().getApplicationContext());
                txt.setLayoutParams(params);
                txt.setText("Eficiência máxima: " + i.getEficienciaMaxima());
                txt.setTextSize(TAMANHO_FONTE);
                txt.setTextColor(Color.BLACK);
                l.addView(txt);
            }
            else if (produtoAtual instanceof Modulo)
            {
                Modulo m = (Modulo) produtoAtual;

                txt = new TextView(getActivity().getApplicationContext());
                txt.setLayoutParams(params);
                txt.setText("Voltagem: " + m.getVoltagem());
                txt.setTextSize(TAMANHO_FONTE);
                txt.setTextColor(Color.BLACK);
                l.addView(txt);
            }
        }
        else if (produtoAtual instanceof Cabo)
        {
            Cabo c = (Cabo) produtoAtual;

            TextView txt = new TextView(getActivity().getApplicationContext());
            txt.setLayoutParams(params);
            txt.setText("Comprimento: " + c.getComprimento());
            txt.setTextSize(TAMANHO_FONTE);
            txt.setTextColor(Color.BLACK);
            l.addView(txt);

            txt = new TextView(getActivity().getApplicationContext());
            txt.setLayoutParams(params);
            txt.setText("Diâmetro: " + c.getDiametro());
            txt.setTextSize(TAMANHO_FONTE);
            txt.setTextColor(Color.BLACK);
            l.addView(txt);

            txt = new TextView(getActivity().getApplicationContext());
            txt.setLayoutParams(params);
            txt.setText("Condução: " + c.getConducao());
            txt.setTextSize(TAMANHO_FONTE);
            txt.setTextColor(Color.BLACK);
            l.addView(txt);
        }
        else if (produtoAtual instanceof StringBox)
        {
            StringBox s = (StringBox) produtoAtual;

            TextView txt = new TextView(getActivity().getApplicationContext());
            txt.setLayoutParams(params);
            txt.setText("Tipo: " + s.getTipo());
            txt.setTextSize(TAMANHO_FONTE);
            txt.setTextColor(Color.BLACK);
            l.addView(txt);

            txt = new TextView(getActivity().getApplicationContext());
            txt.setLayoutParams(params);
            txt.setText("Número de polos: " + s.getNumeroPolos());
            txt.setTextSize(TAMANHO_FONTE);
            txt.setTextColor(Color.BLACK);
            l.addView(txt);

            txt = new TextView(getActivity().getApplicationContext());
            txt.setLayoutParams(params);
            txt.setText("Tensão máxima: " + s.getTensaoMaxima() + "v");
            txt.setTextSize(TAMANHO_FONTE);
            txt.setTextColor(Color.BLACK);
            l.addView(txt);

            txt = new TextView(getActivity().getApplicationContext());
            txt.setLayoutParams(params);
            txt.setText("Corrente nominal: " + s.getCorrenteNominal() + "A");
            txt.setTextSize(TAMANHO_FONTE);
            txt.setTextColor(Color.BLACK);
            l.addView(txt);
        }
    }

}

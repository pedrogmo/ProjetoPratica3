package com.example.appandroidfotovoltaica.ui.mainactivity.produtos.adicionarproduto;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
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
import com.example.appandroidfotovoltaica.classes.produto.equipamento.bombasolar.BombaSolar;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.inversor.Inversor;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.modulo.Modulo;
import com.example.appandroidfotovoltaica.classes.produto.fixacao.Fixacao;
import com.example.appandroidfotovoltaica.classes.produto.stringbox.StringBox;
import com.example.appandroidfotovoltaica.classes.categoria.Categoria;
import com.example.appandroidfotovoltaica.classes.verificadora.mensagenserro.mensagenserroproduto.MensagensErroProduto;
import com.example.appandroidfotovoltaica.ui.mainactivity.produtos.ProdutosFragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class AdicionarProdutoFragment extends Fragment {

    private AdicionarProdutoViewModel mViewModel;
    private EditText
        etNome,
        etPreco,
        etDescricao;
    private TextView
        tvExcNome,
        tvExcPreco,
        tvExcDescricao;
    private Button btnAdicionar;
    private LinearLayout llCamposExtra;

    private Vector<TextView> tvExcCampos;

    private Vector<EditText> etCampos;

    private Class<? extends Produto> categoriaProduto;

    private MensagensErroProduto mensagensErroProduto;

    public static AdicionarProdutoFragment newInstance() {
        return new AdicionarProdutoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(AdicionarProdutoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_adicionarproduto, container, false);

        Bundle bundle = getArguments();
        int indCategoria = (int) bundle.getSerializable("categoria");
        this.categoriaProduto = Categoria.getClasse(indCategoria);

        this.tvExcCampos = new Vector<TextView>();
        this.etCampos = new Vector<EditText>();

        this.etNome = (EditText) root.findViewById(R.id.etNomeProdutoAdd);
        this.etPreco = (EditText) root.findViewById(R.id.etPrecoProdutoAdd);
        this.etDescricao = (EditText) root.findViewById(R.id.etDescricaoProdutoAdd);
        this.tvExcNome = (TextView) root.findViewById(R.id.tvExceptionNomeProdutoAdd);
        this.tvExcPreco = (TextView) root.findViewById(R.id.tvExceptionPrecoProdutoAdd);
        this.tvExcDescricao = (TextView) root.findViewById(R.id.tvExceptionDescricaoProdutoAdd);
        this.btnAdicionar = (Button) root.findViewById(R.id.btnAdicionarProduto);

        this.mensagensErroProduto = new MensagensErroProduto(
            tvExcNome,
            tvExcPreco,
            tvExcDescricao,
            categoriaProduto,
            tvExcCampos);

        this.llCamposExtra = (LinearLayout) root.findViewById(R.id.camposExtraProdutoAdd);
        this.adiconarCamposExtra();


        this.btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RequestQueue QUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());
                final String URL;
                final String nome = etNome.getText().toString().trim();
                final String preco = etPreco.getText().toString().trim();
                final String descricao = etDescricao.getText().toString().trim();

                if (mensagensErroProduto.teveMensagensDeErro(nome, preco, descricao, etCampos))
                    return;

                if (categoriaProduto == Modulo.class)
                    URL = Enderecos.POST_MODULO;
                else if (categoriaProduto == Inversor.class)
                    URL = Enderecos.POST_INVERSOR;
                else if (categoriaProduto == StringBox.class)
                    URL = Enderecos.POST_STRINGBOX;
                else if (categoriaProduto == Fixacao.class)
                    URL = Enderecos.POST_FIXACAO;
                else if (categoriaProduto == BombaSolar.class)
                    URL = Enderecos.POST_BOMBASOLAR;
                else if (categoriaProduto == Cabo.class)
                    URL = Enderecos.POST_CABO;
                else
                    URL = "";

                StringRequest postRequest = new StringRequest(
                    Request.Method.POST,
                    URL,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Produto inserido",
                                Toast.LENGTH_SHORT).show();

                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_adicionarproduto, new ProdutosFragment());
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
                                "Erro ao inserir produto",
                                Toast.LENGTH_SHORT).show();
                        }
                    }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("nome", nome);
                        params.put("preco", preco);
                        params.put("descricao", descricao);
                        if (categoriaProduto == BombaSolar.class ||
                            categoriaProduto == Inversor.class ||
                            categoriaProduto == Modulo.class)
                        {
                            params.put("altura", etCampos.get(0).getText().toString().trim());
                            params.put("largura", etCampos.get(1).getText().toString().trim());
                            params.put("profundidade", etCampos.get(2).getText().toString().trim());
                            params.put("peso", etCampos.get(3).getText().toString().trim());

                            if (categoriaProduto == BombaSolar.class)
                            {
                                params.put("tensaoAlimentacao", etCampos.get(4).getText().toString().trim());
                                params.put("temperaturaMaxima", etCampos.get(5).getText().toString().trim());
                                params.put("alturaMaxima", etCampos.get(6).getText().toString().trim());
                                params.put("bombeamentoMaximoDiario", etCampos.get(7).getText().toString().trim());
                                params.put("diametroTubo", etCampos.get(8).getText().toString().trim());
                            }

                            else if (categoriaProduto == Inversor.class)
                                params.put("eficienciaMaxima", etCampos.get(4).getText().toString().trim());

                            else if (categoriaProduto == Modulo.class)
                                params.put("voltagem", etCampos.get(4).getText().toString().trim());
                        }
                        else if (categoriaProduto == StringBox.class)
                        {
                            params.put("tipo", etCampos.get(0).getText().toString().trim());
                            params.put("numeroPolos", etCampos.get(1).getText().toString().trim());
                            params.put("tensaoMaxima", etCampos.get(2).getText().toString().trim());
                            params.put("correnteNominal", etCampos.get(3).getText().toString().trim());
                        }
                        else if (categoriaProduto == Cabo.class)
                        {
                            params.put("comprimento", etCampos.get(0).getText().toString().trim());
                            params.put("diametro", etCampos.get(1).getText().toString().trim());
                            params.put("conducao", etCampos.get(2).getText().toString().trim());
                        }
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
        mViewModel = ViewModelProviders.of(this).get(AdicionarProdutoViewModel.class);
        // TODO: Use the ViewModel
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

    private void adicionarEditText(
        LinearLayout.LayoutParams params,
        boolean numerico)
    {
        EditText et = new EditText(getActivity().getApplicationContext());
        et.setLayoutParams(params);
        if (numerico)
            et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        this.llCamposExtra.addView(et);
        this.etCampos.add(et);
    }

    private void adicionarTxtExc(
            LinearLayout.LayoutParams params)
    {
        TextView txtExc = new TextView(getActivity().getApplicationContext());
        txtExc.setLayoutParams(params);
        txtExc.setTextColor(Color.RED);
        this.llCamposExtra.addView(txtExc);
        this.tvExcCampos.add(txtExc);
    }

    private void adiconarCamposExtra()
    {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

        if (this.categoriaProduto == BombaSolar.class ||
            this.categoriaProduto == Inversor.class ||
            this.categoriaProduto == Modulo.class)
        {
            this.adicionarTextView("Altura (m):", params);
            this.adicionarEditText(params, true);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Largura (m): ", params);
            this.adicionarEditText(params, true);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Profundiade (m):", params);
            this.adicionarEditText(params, true);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Peso (kg):", params);
            this.adicionarEditText(params, true);
            this.adicionarTxtExc(params);

            if (this.categoriaProduto == BombaSolar.class)
            {
                this.adicionarTextView("Tensão alimentação (v):", params);
                this.adicionarEditText(params, true);
                this.adicionarTxtExc(params);

                this.adicionarTextView("Temperatura máxima (°C):", params);
                this.adicionarEditText(params, true);
                this.adicionarTxtExc(params);

                this.adicionarTextView("Altura máxima (m):", params);
                this.adicionarEditText(params, true);
                this.adicionarTxtExc(params);

                this.adicionarTextView("Bombeamento máximo diário (L):", params);
                this.adicionarEditText(params, true);
                this.adicionarTxtExc(params);

                this.adicionarTextView("Diâmetro tubo:", params);
                this.adicionarEditText(params, false);
                this.adicionarTxtExc(params);
            }
            else if (this.categoriaProduto == Inversor.class)
            {
                this.adicionarTextView("Eficiência máxima:", params);
                this.adicionarEditText(params, true);
                this.adicionarTxtExc(params);
            }
            else if (this.categoriaProduto == Modulo.class)
            {
                this.adicionarTextView("Voltagem (v):", params);
                this.adicionarEditText(params, true);
                this.adicionarTxtExc(params);
            }
        }
        else if (this.categoriaProduto == Cabo.class)
        {
            this.adicionarTextView("Comprimento (m):", params);
            this.adicionarEditText(params, true);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Diâmetro (mm):", params);
            this.adicionarEditText(params, true);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Condução:", params);
            this.adicionarEditText(params, false);
            this.adicionarTxtExc(params);
        }
        else if (this.categoriaProduto == StringBox.class)
        {
            this.adicionarTextView("Tipo:", params);
            this.adicionarEditText(params, false);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Número de polos:", params);
            this.adicionarEditText(params, true);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Tensão máxima (v):", params);
            this.adicionarEditText(params, true);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Corrente nominal (A):", params);
            this.adicionarEditText(params, true);
            this.adicionarTxtExc(params);
        }
    }
}

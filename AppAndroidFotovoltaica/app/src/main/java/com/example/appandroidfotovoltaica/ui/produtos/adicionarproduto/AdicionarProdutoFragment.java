package com.example.appandroidfotovoltaica.ui.produtos.adicionarproduto;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.produto.Produto;
import com.example.appandroidfotovoltaica.classes.produto.cabo.Cabo;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.EquipamentoFotovoltaico;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.bombasolar.BombaSolar;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.inversor.Inversor;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.modulo.Modulo;
import com.example.appandroidfotovoltaica.classes.produto.stringbox.StringBox;
import com.example.appandroidfotovoltaica.classes.categoria.Categoria;
import com.example.appandroidfotovoltaica.ui.produtos.ProdutosFragment;

import org.w3c.dom.Text;

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

        this.llCamposExtra = (LinearLayout) root.findViewById(R.id.camposExtraProdutoAdd);
        this.adiconarCamposExtra();

        this.btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_adicionarproduto, new ProdutosFragment());
            fragmentTransaction.commit();
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
        LinearLayout.LayoutParams params)
    {
        EditText et = new EditText(getActivity().getApplicationContext());
        et.setLayoutParams(params);
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
            this.adicionarEditText(params);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Largura (m): ", params);
            this.adicionarEditText(params);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Profundiade (m):", params);
            this.adicionarEditText(params);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Peso (kg):", params);
            this.adicionarEditText(params);
            this.adicionarTxtExc(params);

            if (this.categoriaProduto == BombaSolar.class)
            {
                this.adicionarTextView("Tensão alimentação (v):", params);
                this.adicionarEditText(params);
                this.adicionarTxtExc(params);

                this.adicionarTextView("Temperatura máxima (°C):", params);
                this.adicionarEditText(params);
                this.adicionarTxtExc(params);

                this.adicionarTextView("Altura máxima (m):", params);
                this.adicionarEditText(params);
                this.adicionarTxtExc(params);

                this.adicionarTextView("Bombeamento máximo diário (L):", params);
                this.adicionarEditText(params);
                this.adicionarTxtExc(params);

                this.adicionarTextView("Diâmetro tubo (mm):", params);
                this.adicionarEditText(params);
                this.adicionarTxtExc(params);
            }
            else if (this.categoriaProduto == Inversor.class)
            {
                this.adicionarTextView("Eficiência máxima:", params);
                this.adicionarEditText(params);
                this.adicionarTxtExc(params);
            }
            else if (this.categoriaProduto == Modulo.class)
            {
                this.adicionarTextView("Voltagem (v):", params);
                this.adicionarEditText(params);
                this.adicionarTxtExc(params);
            }
        }
        else if (this.categoriaProduto == Cabo.class)
        {
            this.adicionarTextView("Comprimento (m):", params);
            this.adicionarEditText(params);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Diâmetro (mm):", params);
            this.adicionarEditText(params);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Condução (v):", params);
            this.adicionarEditText(params);
            this.adicionarTxtExc(params);
        }
        else if (this.categoriaProduto == StringBox.class)
        {
            this.adicionarTextView("Tipo:", params);
            this.adicionarEditText(params);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Número de polos:", params);
            this.adicionarEditText(params);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Tensão máxima (v):", params);
            this.adicionarEditText(params);
            this.adicionarTxtExc(params);

            this.adicionarTextView("Corrente nominal (A):", params);
            this.adicionarEditText(params);
            this.adicionarTxtExc(params);
        }
    }
}

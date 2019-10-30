package com.example.appandroidfotovoltaica.classes.verificadora.mensagenserro.mensagenserroproduto;

import android.widget.EditText;
import android.widget.TextView;

import com.example.appandroidfotovoltaica.classes.categoria.Categoria;
import com.example.appandroidfotovoltaica.classes.produto.Produto;
import com.example.appandroidfotovoltaica.classes.produto.cabo.Cabo;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.EquipamentoFotovoltaico;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.bombasolar.BombaSolar;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.inversor.Inversor;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.modulo.Modulo;
import com.example.appandroidfotovoltaica.classes.produto.stringbox.StringBox;
import com.example.appandroidfotovoltaica.classes.verificadora.Verificadora;

import java.util.Vector;

public class MensagensErroProduto
{
    private TextView tvNome, tvPreco, tvDescricao;
    private Class<? extends Produto> categoria;
    private Vector<TextView> tvCampos;

    public MensagensErroProduto(
        TextView tvNome,
        TextView tvPreco,
        TextView tvDescricao,
        Class<? extends Produto> categoria,
        Vector<TextView> tvCampos)
    {
        this.tvNome = tvNome;
        this.tvPreco = tvPreco;
        this.tvDescricao = tvDescricao;
        this.categoria = categoria;
        this.tvCampos = tvCampos;
    }

    public void limparMensagens()
    {
        if (tvNome != null)
            tvNome.setText("");
        if (tvPreco != null)
            tvPreco.setText("");
        if (tvDescricao != null)
            tvDescricao.setText("");
        for(TextView tv : tvCampos)
            tv.setText("");
    }

    private boolean floatInvalido(
        String dado,
        TextView txt,
        String mensagem)
    {
        float f = 0.0f;
        try
        {
            f = Float.parseFloat(dado);
            if (f < 0.0f)
            {
                txt.setText(mensagem);
                return true;
            }
            return false;
        }
        catch(Exception exc)
        {
            txt.setText(mensagem);
            return true;
        }
    }

    public boolean teveMensagensDeErro(
        String nome,
        String preco,
        String descricao,
        Vector<EditText> etCampos)
    {
        limparMensagens();
        boolean teveMensagem = false;
        if (tvNome != null)
            if (!Verificadora.isNomeValido(nome))
            {
                tvNome.setText("Nome inválido. Números e simbolos não podem ser utilizados. O tamanho do nome deve ser de 10 a 50 caracteres");
                teveMensagem = true;
            }
        if (tvPreco != null)
        {
            if (floatInvalido(preco, tvPreco, "Preço inválido. Deve ser um número positivo"))
                teveMensagem = true;
        }

        if (tvDescricao != null)
        {
            if (descricao == null || descricao.equals(""))
            {
                tvDescricao.setText("Descrição inválida. Deve ter conteúdo");
                teveMensagem = true;
            }
        }

        if (categoria == Modulo.class ||
            categoria == Inversor.class ||
            categoria == BombaSolar.class)
        {
            if (floatInvalido(etCampos.get(0).getText().toString().trim(), tvCampos.get(0), "Altura inválida. Deve ser um número positivo"))
                teveMensagem = true;
            if (floatInvalido(etCampos.get(1).getText().toString().trim(), tvCampos.get(1), "Largura inválida. Deve ser um número positivo"))
                teveMensagem = true;
            if (floatInvalido(etCampos.get(2).getText().toString().trim(), tvCampos.get(2), "Profundidade inválida. Deve ser um número positivo"))
                teveMensagem = true;
            if (floatInvalido(etCampos.get(3).getText().toString().trim(), tvCampos.get(3), "Peso inválido. Deve ser um número positivo"))
                teveMensagem = true;

            if (categoria == BombaSolar.class)
            {
                if (floatInvalido(etCampos.get(4).getText().toString().trim(), tvCampos.get(4), "Tensão de alimentação inválida. Deve ser um número positivo"))
                    teveMensagem = true;
                if (floatInvalido(etCampos.get(5).getText().toString().trim(), tvCampos.get(5), "Temperatura máxima inválida. Deve ser um número positivo"))
                    teveMensagem = true;
                if (floatInvalido(etCampos.get(6).getText().toString().trim(), tvCampos.get(6), "Altura máxima inválida. Deve ser um número positivo"))
                    teveMensagem = true;
                if (floatInvalido(etCampos.get(7).getText().toString().trim(), tvCampos.get(7), "Bombeamento máximo diário inválido. Deve ser um número positivo"))
                    teveMensagem = true;
                String d = etCampos.get(8).getText().toString().trim();
                if (d == null || d.equals(""))
                {
                    tvCampos.get(8).setText("Diâmetro do tubo inválido. Dever ter conteúdo");
                    teveMensagem = true;
                }
            }

            else if (categoria == Inversor.class)
            {
                if (floatInvalido(etCampos.get(4).getText().toString().trim(), tvCampos.get(4), "Eficiência máxima inválida. Deve ser um número positivo"))
                    teveMensagem = true;
            }

            else if (categoria == Modulo.class)
            {
                if (floatInvalido(etCampos.get(4).getText().toString().trim(), tvCampos.get(4), "Voltagem inválida. Deve ser um número positivo"))
                    teveMensagem = true;
            }
        }

        else if (categoria == StringBox.class)
        {
            String t = etCampos.get(0).getText().toString().trim();
            if (t == null || t.equals(""))
            {
                tvCampos.get(0).setText("Tipo inválido. Deve ter conteúdo (CC ou CA)");
                teveMensagem = true;
            }

            if (floatInvalido(etCampos.get(1).getText().toString().trim(), tvCampos.get(1), "Número de polos inválido. Deve ser um número positivo"))
                teveMensagem = true;
            if (floatInvalido(etCampos.get(2).getText().toString().trim(), tvCampos.get(2), "Tensão máxima inválida. Deve ser um número positivo"))
                teveMensagem = true;
            if (floatInvalido(etCampos.get(3).getText().toString().trim(), tvCampos.get(3), "Corrente nominal inválida. Deve ser um número positivo"))
                teveMensagem = true;
        }

        else if (categoria == Cabo.class)
        {
            if (floatInvalido(etCampos.get(0).getText().toString().trim(), tvCampos.get(0), "Comprimento inválido. Deve ser um número positivo"))
                teveMensagem = true;
            if (floatInvalido(etCampos.get(1).getText().toString().trim(), tvCampos.get(1), "Diâmetro inválido. Deve ser um número positivo"))
                teveMensagem = true;

            String c = etCampos.get(2).getText().toString().trim();

            if (c == null || c.equals(""))
            {
                tvCampos.get(2).setText("Condução inválida. Deve ter conteúdo");
                teveMensagem = true;
            }
        }

        return teveMensagem;
    }
}

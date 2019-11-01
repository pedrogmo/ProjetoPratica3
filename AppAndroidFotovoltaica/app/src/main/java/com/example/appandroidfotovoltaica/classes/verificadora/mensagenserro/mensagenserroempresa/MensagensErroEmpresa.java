package com.example.appandroidfotovoltaica.classes.verificadora.mensagenserro.mensagenserroempresa;

import android.widget.TextView;

import com.example.appandroidfotovoltaica.classes.verificadora.Verificadora;

public class MensagensErroEmpresa
{
    private TextView
        tvExceptionNome,
        tvExceptionCNPJ,
        tvExceptionSenhaUm,
        tvExceptionSenhaConfirmada;

    public MensagensErroEmpresa(
        TextView tvExceptionNome,
        TextView tvExceptionCNPJ,
        TextView tvExceptionSenhaUm,
        TextView tvExceptionSenhaConfirmada)
    {
        this.tvExceptionNome = tvExceptionNome;
        this.tvExceptionCNPJ = tvExceptionCNPJ;
        this.tvExceptionSenhaUm = tvExceptionSenhaUm;
        this.tvExceptionSenhaConfirmada = tvExceptionSenhaConfirmada;
    }

    public boolean teveMensagensDeErro(
        String nome,
        String cnpj,
        String senhaUm,
        String senhaConfirmada)
    {
        limparMensagens();
        boolean teveMensagem = false;

        if (tvExceptionNome != null)
            if (!Verificadora.isNomeValido(nome))
            {
                tvExceptionNome.setText("Nome inválido. Números e simbolos não podem ser utilizados. O tamanho do nome deve ser de 10 a 50 caracteres");
                teveMensagem = true;
            }

        if (tvExceptionCNPJ != null)
            if (!Verificadora.isCNPJValido(cnpj))
            {
                tvExceptionCNPJ.setText("CNPJ inválido. O formato de um CNPJ é 00.000.000/0000-00");
                teveMensagem = true;
            }

        if (tvExceptionSenhaUm != null)
            if (!Verificadora.isSenhaValida(senhaUm))
            {
                tvExceptionSenhaUm.setText("Senha inválida. A senha deve possuir de 6 a 22 caracteres e não poderá possuir espaços");
            }

        if (tvExceptionSenhaConfirmada != null)
            if (!Verificadora.isSenhaIguais(senhaUm,senhaConfirmada))
            {
                tvExceptionSenhaConfirmada.setText("As senhas devem ser iguais.");
            }

        return teveMensagem;
    }

    public void limparMensagens()
    {
        if (tvExceptionNome != null)
            tvExceptionNome.setText("");
        if (tvExceptionCNPJ != null)
            tvExceptionCNPJ.setText("");
        if (tvExceptionSenhaUm != null)
            tvExceptionSenhaUm.setText("");
        if (tvExceptionSenhaConfirmada != null)
            tvExceptionSenhaConfirmada.setText("");
    }
}

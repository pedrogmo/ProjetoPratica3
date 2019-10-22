package com.example.appandroidfotovoltaica;

import android.widget.TextView;

public class MensagensErroUsuario
{
    private TextView
        tvExceptionNome,
        tvExceptionDataNascimento,
        tvExceptionEmail,
        tvExceptionTelefone,
        tvExceptionCpf,
        tvExceptionSenhaUm,
        tvExceptionSenhaConfirmada;

    public MensagensErroUsuario(
        TextView tvExceptionNome,
        TextView tvExceptionDataNascimento,
        TextView tvExceptionEmail,
        TextView tvExceptionTelefone,
        TextView tvExceptionCpf,
        TextView tvExceptionSenhaUm,
        TextView tvExceptionSenhaConfirmada)
    {
        this.tvExceptionNome = tvExceptionNome;
        this.tvExceptionDataNascimento = tvExceptionDataNascimento;
        this.tvExceptionEmail = tvExceptionEmail;
        this.tvExceptionTelefone = tvExceptionTelefone;
        this.tvExceptionCpf = tvExceptionCpf;
        this.tvExceptionSenhaUm = tvExceptionSenhaUm;
        this.tvExceptionSenhaConfirmada = tvExceptionSenhaConfirmada;
    }

    public boolean teveMensagensDeErro(
        String nome,
        String data,
        String email,
        String telefone,
        String cpf,
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

        if (tvExceptionDataNascimento != null)
            if (!Verificadora.isDataValida(data))
            {
                tvExceptionDataNascimento.setText("Data inválida. Siga o formato dd/mm/aaaa (Exemplo: 24/09/1977)");
                teveMensagem = true;
            }

        if (tvExceptionEmail != null)
            if (!Verificadora.isEmailValido(email))
            {
                tvExceptionEmail.setText("Email inválido.");
            }

        if (tvExceptionTelefone != null)
            if (!Verificadora.isTelefoneValido(telefone))
            {
                tvExceptionTelefone.setText("Telefone inválido. O número poderá ter entre 9 e 11* caracteres." +
                        "*: código de país incluso");
                teveMensagem = true;
            }

        if (tvExceptionCpf != null)
            if (!Verificadora.isCpfValido(cpf))
            {
                tvExceptionCpf.setText("Cpf inválido. O formato de um cpf é 000.000.000-00");
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
        tvExceptionNome.setText("");
        tvExceptionDataNascimento.setText("");
        tvExceptionEmail.setText("");
        tvExceptionTelefone.setText("");
        tvExceptionCpf.setText("");
        tvExceptionSenhaUm.setText("");
        tvExceptionSenhaConfirmada.setText("");
    }
}

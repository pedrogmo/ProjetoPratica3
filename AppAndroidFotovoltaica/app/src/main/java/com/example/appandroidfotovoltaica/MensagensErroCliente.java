package com.example.appandroidfotovoltaica;

import android.widget.TextView;

public class MensagensErroCliente
{
    private TextView
        tvExceptionNome,
        tvExceptionData,
        tvExceptionEmail,
        tvExceptionTelefone,
        tvExceptionCpf;

    public MensagensErroCliente(
        TextView tvExceptionNome,
        TextView tvExceptionData,
        TextView tvExceptionEmail,
        TextView tvExceptionTelefone,
        TextView tvExceptionCpf)
    {
        this.tvExceptionNome = tvExceptionNome;
        this.tvExceptionData = tvExceptionData;
        this.tvExceptionEmail = tvExceptionEmail;
        this.tvExceptionTelefone = tvExceptionTelefone;
        this.tvExceptionCpf = tvExceptionCpf;
    }

    public void limparMensagens()
    {
        if (tvExceptionNome != null)
            tvExceptionNome.setText("");
        if (tvExceptionData != null)
            tvExceptionData.setText("");
        if (tvExceptionEmail != null)
            tvExceptionEmail.setText("");
        if (tvExceptionTelefone != null)
            tvExceptionTelefone.setText("");
        if (tvExceptionCpf != null)
            tvExceptionCpf.setText("");
    }

    public boolean teveMensagensDeErro(
        String nome,
        String data,
        String email,
        String telefone,
        String cpf)
    {
        limparMensagens();
        boolean teveMensagem = false;
        if (tvExceptionNome != null)
            if (!Verificadora.isNomeValido(nome))
            {
                tvExceptionNome.setText("Nome inválido. Números e simbolos não podem ser utilizados. O tamanho do nome deve ser de 10 a 50 caracteres");
                teveMensagem = true;
            }

        if (tvExceptionData != null)
            if (!Verificadora.isDataValida(data))
            {
                tvExceptionData.setText("Data inválida. Siga o formato dd/mm/aaaa (Exemplo: 24/09/1977)");
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

        return teveMensagem;
    }
}

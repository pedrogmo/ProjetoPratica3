package com.example.appandroidfotovoltaica;

public class Cliente {
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private Data data;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception
    {
        if (!isNomeValido(nome))
            throw new Exception("Cliente - setNome: nome inválido");

        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception
    {
        if (!isEmailValido(email))
            throw new Exception("Cliente - setEmail: email inválido");
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) throws Exception
    {
        if (!isTelefoneValido(telefone))
            throw new Exception("Cliente - setTelefone: telefone inválido");
        this.telefone = telefone;
    }
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) throws Exception
    {
        if (!isCpfValido(cpf))
            throw new Exception("Cpf - setCpf: cpf inválido");
        this.cpf = cpf;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) throws Exception
    {
        try{
            this.data = (Data)data.clone();
        }
        catch (Exception e)
        {
            throw new Exception("Cliente - setData: data inválida");
        }

    }

   private boolean isNomeValido(String nome)
   {
       if (nome == null)
           return false;
       if (nome.length() <= 0)
           return false;

       for (int i = 0; i < nome.length(); i++)
           if (!Character.isLetter(nome.charAt(i)))
               return false;

       return true;
   }
    private boolean isEmailValido(String email)
    {
        if (email == null)
            return false;
        if (email.length() <= 0)
            return false;
        if (!email.contains("@"))
            return false;
        if (!email.contains(".com"))
            return false;

        return true;
    }
    private boolean isTelefoneValido(String telefone)
    {
        if (telefone == null)
            return false;
        if (telefone.length() < 9)
            return false;
        for (int i = 0; i < nome.length(); i++)
            if (!Character.isDigit(nome.charAt(i)))
                return false;

        return true;
    }
    private boolean isCpfValido(String cpf)
    {
        if(!cpf.matches("[0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}[-]?[0-9]{2}"))
            return false;
        return true;
    }




}

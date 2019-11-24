package com.example.appandroidfotovoltaica.classes.mytask;

import android.os.AsyncTask;
import android.util.Log;
import com.example.appandroidfotovoltaica.classes.clientews.ClienteWS;

public class MyTask
        extends AsyncTask<String, Void, Object>
{
    private boolean trabalhando = true;
    private Object dados;
    private final Class<?> TIPO_DADO;

    public MyTask(
            Class<?> tipoDado)
    {
            TIPO_DADO = tipoDado;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(
            String... params)
    {
        Object resultado = null;
        try
        {
            resultado = ClienteWS.getObjeto(
                    TIPO_DADO, params[0]);
            this.trabalhando = false;
            this.dados = resultado;
        }
        catch(Exception exc){
            Log.d("ERRO", exc.getMessage());
        }
        return resultado;
    }

    @Override
    protected void onPostExecute(
            Object retorno)
    {
        super.onPostExecute(retorno);
    }

    public boolean isTrabalhando()
    {
        return this.trabalhando;
    }

    public Object getDados()
    {
        return this.dados;
    }
}
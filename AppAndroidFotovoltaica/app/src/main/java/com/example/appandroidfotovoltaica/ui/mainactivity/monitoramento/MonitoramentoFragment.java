package com.example.appandroidfotovoltaica.ui.mainactivity.monitoramento;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appandroidfotovoltaica.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MonitoramentoFragment extends Fragment {

    private MonitoramentoViewModel mViewModel;

    private TextView tvConexao, tvLuz, tvTemperatura;
    private int cont = 0;

    private static String SERVER_IP = "192.168.43.138";
    private static int SERVER_PORT = 80;
    private Socket socket;
    private boolean rodando;
    private BufferedReader input;
    private PrintWriter output;
    private String mensagem;

    public static MonitoramentoFragment newInstance() {
        return new MonitoramentoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(MonitoramentoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_monitoramento, container, false);

        tvConexao = root.findViewById(R.id.tvConexaoArduino);
        tvLuz = root.findViewById(R.id.tvLuzArduino);
        tvTemperatura = root.findViewById(R.id.tvTemperaturaArduino);

        tvConexao.setText("Conectando com arduino...");


        if (socket != null && !socket.isClosed())
    	{
    		try
    		{
    			socket.close();
    		}
    		catch(Exception exc)
    		{
    			exc.printStackTrace();
    		}
    	}
	
	   new ConexaoThread().start();


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MonitoramentoViewModel.class);
    }

    private class ConexaoThread extends Thread {

        @Override
        public void run() {

            try
            {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVER_PORT);
                rodando = true;
                output = new PrintWriter(
			         new BufferedWriter(
        				new OutputStreamWriter(socket.getOutputStream())),
        				true
        		);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                new EnviadorThread().start();
                new ReceptorThread().start();
            }
            catch (UnknownHostException e1)
            {
                e1.printStackTrace();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    private class ReceptorThread extends Thread
    {
        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    final String message = input.readLine();
                    if (message != null)
                    {
                        getActivity().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                String[] dados = message.split("|");
                                float luz = Float.parseFloat(dados[0]);
                                float temperatura = Float.parseFloat(dados[1]);

                                tvLuz.setText("Luz: " + luz);
                                tvTemperatura.setText("Temperatura: " + temperatura);
                            }
                        });
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private class EnviadorThread extends Thread
    {
        @Override
        public void run()
        {
            output.write(mensagem);
            output.flush();
        }
    }
}

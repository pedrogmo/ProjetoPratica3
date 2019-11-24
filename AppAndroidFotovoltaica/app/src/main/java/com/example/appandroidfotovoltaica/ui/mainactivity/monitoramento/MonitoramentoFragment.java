package com.example.appandroidfotovoltaica.ui.mainactivity.monitoramento;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appandroidfotovoltaica.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MonitoramentoFragment extends Fragment {

    private TextView tvConexao, tvLuz, tvTemperatura;
    private EditText etIp;
    private Button btnConectar;

    private static final int SERVER_PORT = 80;
    private Socket socket;
    private boolean rodando = true;
    private BufferedReader input;
    private PrintWriter output;
    private String mensagem = "\r\n\r\n";

    public static MonitoramentoFragment newInstance() {
        return new MonitoramentoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_monitoramento, container, false);

        tvConexao = root.findViewById(R.id.tvConexaoArduino);
        tvLuz = root.findViewById(R.id.tvLuzArduino);
        tvTemperatura = root.findViewById(R.id.tvTemperaturaArduino);
        etIp = root.findViewById(R.id.etIpArduino);
        btnConectar = root.findViewById(R.id.btnConectarArduino);

        btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        return root;
    }

    private class ConexaoThread extends Thread {

        @Override
        public void run() {

            try
            {
                tvConexao.setText("Conectando com arduino...");

                final String IP = etIp.getText().toString().trim();

                InetAddress serverAddr = InetAddress.getByName(IP);
                socket = new Socket(serverAddr, SERVER_PORT);
                rodando = true;
                output = new PrintWriter(
			         new BufferedWriter(
        				new OutputStreamWriter(socket.getOutputStream())),
        				true
        		);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                tvConexao.setText("Conectado");

                new EnviadorThread().start();
                while(rodando) ;

                new ReceptorThread().start();
            }
            catch (Exception e)
            {
                tvConexao.setText("Erro ao conectar");
            }

        }
    }

    private class ReceptorThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                final String luz = input.readLine();
                final String temp = input.readLine();

                if (luz != null && temp != null)
                {
                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                String qualidade = "";
                                float lf = Float.parseFloat(luz);

                                if (lf < 100.0f)
                                    qualidade = " (Pouca luminosidade)";
                                else if (lf < 200.0f)
                                    qualidade = " (Luminosidade boa)";
                                else
                                    qualidade = " (Luminosidade ótima)";

                                tvLuz.setText("Luz: " + luz + qualidade);
                                tvTemperatura.setText("Temperatura: " + temp + "ºC");
                            }
                            catch(Exception exc)
                            {
                                tvLuz.setText("Luz: [ERRO]");
                                tvTemperatura.setText("Temperatura: [ERRO]");
                            }
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

    private class EnviadorThread extends Thread
    {
        @Override
        public void run()
        {
            output.write(mensagem);
            output.flush();
            rodando = false;
        }
    }
}

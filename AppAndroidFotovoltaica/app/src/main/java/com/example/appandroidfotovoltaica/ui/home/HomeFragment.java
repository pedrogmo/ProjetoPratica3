package com.example.appandroidfotovoltaica.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appandroidfotovoltaica.CalculadoraFotoVoltaica;

import com.example.appandroidfotovoltaica.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Toolbar toolbar;
    TextView tvNumeroPlacas, tvInversor, tvInversorMais, tvInversorMenos, tvMes;
    EditText etIrradiacao, etMedia, etWatts;
    RadioGroup rg;
    RadioButton rbTotal, rbMensal;
    Button btnCalcular;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calculadora, container, false);

        tvNumeroPlacas = root.findViewById(R.id.tvNumeroPlacas);
        tvInversor = root.findViewById(R.id.tvInversor);
        tvInversorMais = root.findViewById(R.id.tvInversorMais);
        tvInversorMenos = root.findViewById(R.id.tvInversorMenos);
        tvMes = root.findViewById(R.id.tvMes);
        /*toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        etIrradiacao = root.findViewById(R.id.etIrradiacao);
        etMedia = root.findViewById(R.id.etMedia);
        etWatts = root.findViewById(R.id.etWatts);

        rg = root.findViewById(R.id.rgMedia);
        rbTotal = root.findViewById(R.id.rbTotal);
        rbTotal.setChecked(true);// inicia em true
        rbMensal = root.findViewById(R.id.rbMensal);
        String meses[] = {"Janeiro","Fevereiro","Março","Abril","Maio",
                          "Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};


        btnCalcular = root.findViewById(R.id.btnCalcular);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    etWatts.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    etIrradiacao.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    etMedia.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    limpar();

                    tvNumeroPlacas.setText(tvNumeroPlacas.getText().toString() + CalculadoraFotoVoltaica.numeroPlacas(Double.parseDouble(etMedia.getText().toString()),
                            Float.parseFloat(etIrradiacao.getText().toString()),
                            Double.parseDouble(etWatts.getText().toString())) + "");
                    tvInversor.setText(tvInversor.getText().toString() + CalculadoraFotoVoltaica.inversor(Double.parseDouble(etMedia.getText().toString()),
                            Double.parseDouble(etIrradiacao.getText().toString())) + "");
                    tvInversorMais.setText(tvInversorMais.getText().toString() + CalculadoraFotoVoltaica.inversorMais(Double.parseDouble(etMedia.getText().toString()),
                            Double.parseDouble(etIrradiacao.getText().toString())) + "");
                    tvInversorMenos.setText(tvInversorMenos.getText().toString() + CalculadoraFotoVoltaica.inversorMenos(Double.parseDouble(etMedia.getText().toString()),
                            Double.parseDouble(etIrradiacao.getText().toString())) + "");
                }
                catch(Exception e)
                {

                }


            }
        });

        return root;
    }

    private void limpar() {
        tvNumeroPlacas.setText("Número Placas:");
        tvInversorMais.setText("Inversor Mais:");
        tvInversorMenos.setText("Inversor Menos:");
        tvInversor.setText("Inversor:");
    }
}
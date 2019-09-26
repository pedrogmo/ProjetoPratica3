package com.example.appandroidfotovoltaica.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
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
    private int indiceMes = 0;
    String meses[] = {"1°", "2°", "3°", "4°", "5°",
            "6°", "7°", "8°", "9°", "10°", "11°", "12°"};
    final Double valoresMeses[] = new Double[12];
    Toolbar toolbar;
    TextView tvNumeroPlacas, tvInversor, tvInversorMais, tvInversorMenos, tvMes;
    EditText etIrradiacao, etMedia, etWatts;
    RadioGroup rgMedia;
    RadioButton rbTotal, rbMensal;
    Button btnEsq, btnDir, btnCalcular;

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


        rbTotal = root.findViewById(R.id.rbTotal);
        rbTotal.setChecked(true);// inicia em true
        rbMensal = root.findViewById(R.id.rbMensal);


        btnEsq = root.findViewById(R.id.btnEsq);
        btnEsq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (etMedia.getText().toString().trim().length() > 0)
                        valoresMeses[indiceMes] = Double.parseDouble(etMedia.getText().toString());

                    if (indiceMes <= 0)
                        indiceMes = 11;
                    else
                        indiceMes--;

                    tvMes.setText(meses[indiceMes]);
                    if (valoresMeses[indiceMes] != null)
                        etMedia.setText(valoresMeses[indiceMes].toString());
                    else
                        etMedia.setText("");
                } catch (Exception e) {
                }
            }
        });
        btnDir = root.findViewById(R.id.btnDir);
        btnDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (etMedia.getText().toString().trim().length() > 0)
                        valoresMeses[indiceMes] = Double.parseDouble(etMedia.getText().toString());

                    if (indiceMes >= 11)
                        indiceMes = 0;
                    else
                        indiceMes++;

                    tvMes.setText(meses[indiceMes]);
                    if (valoresMeses[indiceMes] != null)
                        etMedia.setText(valoresMeses[indiceMes].toString());
                    else
                        etMedia.setText("");
                } catch (Exception e) {

                }


            }
        });
        rgMedia = root.findViewById(R.id.rgMedia);
        rgMedia.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId)
                {
                    case R.id.rbTotal:
                        indiceMes = 0;
                        etMedia.setText("");
                        limpar(valoresMeses);
                        btnDir.setVisibility(View.INVISIBLE);
                        btnEsq.setVisibility(View.INVISIBLE);
                        tvMes.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.rbMensal:
                        indiceMes = 0;
                        etMedia.setText("");
                        btnDir.setVisibility(View.VISIBLE);
                        btnEsq.setVisibility(View.VISIBLE);
                        tvMes.setVisibility(View.VISIBLE);
                        tvMes.setText(meses[indiceMes]);
                        break;
                }
            }
        });
        btnCalcular = root.findViewById(R.id.btnCalcular);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    etWatts.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    etIrradiacao.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    etMedia.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    limpar();
                    double media = 0;
                    if (rbTotal.isChecked()) {
                        media = Double.parseDouble(etMedia.getText().toString());
                    } else if (rbMensal.isChecked()) {
                        media = CalculadoraFotoVoltaica.media(valoresMeses);
                    }

                    tvNumeroPlacas.setText(tvNumeroPlacas.getText().toString() + CalculadoraFotoVoltaica.numeroPlacas(media,
                            Float.parseFloat(etIrradiacao.getText().toString()),
                            Double.parseDouble(etWatts.getText().toString())) + "");
                    tvInversor.setText(tvInversor.getText().toString() + CalculadoraFotoVoltaica.inversor(media,
                            Double.parseDouble(etIrradiacao.getText().toString())) + "");
                    tvInversorMais.setText(tvInversorMais.getText().toString() + CalculadoraFotoVoltaica.inversorMais(media,
                            Double.parseDouble(etIrradiacao.getText().toString())) + "");
                    tvInversorMenos.setText(tvInversorMenos.getText().toString() + CalculadoraFotoVoltaica.inversorMenos(media,
                            Double.parseDouble(etIrradiacao.getText().toString())) + "");
                } catch (Exception e) {

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
    private void limpar(Double valores[]) {
        for (int i = 0; i < valores.length; i++)
            valores[i] = null;
    }
}
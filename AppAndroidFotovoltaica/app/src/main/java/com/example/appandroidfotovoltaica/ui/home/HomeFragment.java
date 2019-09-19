package com.example.appandroidfotovoltaica.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import br.unicamp.cotuca.aplicativoandroid.CalculadoraFotoVoltaica;

import com.example.appandroidfotovoltaica.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Toolbar toolbar;
    TextView tvNumeroPlacas, tvInversor, tvInversorMais, tvInversorMenos;
    EditText etIrradiacao, etMedia, etWatts;
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
        /*toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        etIrradiacao = root.findViewById(R.id.etIrradiacao);
        etMedia = root.findViewById(R.id.etMedia);
        etWatts = root.findViewById(R.id.etWatts);

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
                    tvInversor.setText(tvInversor.getText().toString() + CalculadoraFotoVoltaica.inversor(Double.parseDouble(etMedia.getText().toString()),
                            Double.parseDouble(etIrradiacao.getText().toString())) + "");
                    tvNumeroPlacas.setText(tvNumeroPlacas.getText().toString() + CalculadoraFotoVoltaica.numeroPlacas(Double.parseDouble(etMedia.getText().toString()),
                            Float.parseFloat(etIrradiacao.getText().toString()),
                            Double.parseDouble(etWatts.getText().toString())) + "");
                    tvInversorMais.setText(tvInversorMais.getText().toString() + CalculadoraFotoVoltaica.inversor(Double.parseDouble(etMedia.getText().toString()),
                            Double.parseDouble(etIrradiacao.getText().toString())) + "");
                    tvInversorMenos.setText(tvInversorMenos.getText().toString() + CalculadoraFotoVoltaica.inversor(Double.parseDouble(etMedia.getText().toString()),
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
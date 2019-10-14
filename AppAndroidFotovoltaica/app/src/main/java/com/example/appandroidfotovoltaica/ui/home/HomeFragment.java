package com.example.appandroidfotovoltaica.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appandroidfotovoltaica.CalculadoraFotoVoltaica;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.ValorMensalEnergia;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private int indiceMes = 0;
    private ValorMensalEnergia valoresMensaisEnergia[];
    TextView tvNumeroPlacas, tvInversor, tvInversorMais, tvInversorMenos, tvMes;
    EditText etIrradiacao, etMedia, etWatts;
    RadioGroup rgMedia;
    RadioButton rbTotal, rbMensal;
    Button btnCalcular;
    ImageView btnEsq, btnDir;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calculadora, container, false);


        valoresMensaisEnergia = new ValorMensalEnergia[12];
        for (int i = 0; i < valoresMensaisEnergia.length; i++)
        {
            valoresMensaisEnergia[i] = new ValorMensalEnergia((i + 1) + "°");
        }

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
                    if (indiceMes <= 0)
                        indiceMes = 11;
                    else
                        indiceMes--;

                    tvMes.setText(valoresMensaisEnergia[indiceMes].getMes());
                    etMedia.setText(valoresMensaisEnergia[indiceMes].getValor() + "");
                    if (valoresMensaisEnergia[indiceMes].getValor() == 0)
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
                    if (indiceMes >= 11)
                        indiceMes = 0;
                    else
                        indiceMes++;

                    tvMes.setText(valoresMensaisEnergia[indiceMes].getMes());
                    etMedia.setText(valoresMensaisEnergia[indiceMes].getValor() + "");
                    if (valoresMensaisEnergia[indiceMes].getValor() == 0)
                        etMedia.setText("");
                } catch (Exception e) {

                }


            }
        });
        etMedia.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                try{
                    if (rbMensal.isChecked())
                        valoresMensaisEnergia[indiceMes].setValor(Double.parseDouble(etMedia.getText().toString()));
                }
                catch(Exception e){}

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
                        tvMes.setText(valoresMensaisEnergia[indiceMes].getMes());
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
                        media = CalculadoraFotoVoltaica.media(valoresMensaisEnergia);
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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        setHasOptionsMenu(true);
        inflater.inflate(R.menu.activity_main_drawer, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    private void limpar() {
        tvNumeroPlacas.setText("Número Placas:");
        tvInversorMais.setText("Inversor Mais:");
        tvInversorMenos.setText("Inversor Menos:");
        tvInversor.setText("Inversor:");
    }
}
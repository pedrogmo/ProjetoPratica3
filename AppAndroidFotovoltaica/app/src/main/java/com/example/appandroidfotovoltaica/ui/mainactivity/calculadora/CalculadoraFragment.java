package com.example.appandroidfotovoltaica.ui.mainactivity.calculadora;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appandroidfotovoltaica.classes.calculadora.CalculadoraFotoVoltaica;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.cliente.Cliente;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.kit.Kit;
import com.example.appandroidfotovoltaica.classes.kitproduto.KitProduto;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.modulo.Modulo;
import com.example.appandroidfotovoltaica.classes.valormensal.ValorMensalEnergia;
import com.example.appandroidfotovoltaica.ui.mainactivity.MainActivity;

import java.util.ArrayList;

public class CalculadoraFragment extends Fragment {

    private CalculadoraViewModel calculadoraViewModel;
    private int indiceMes = 0;
    private ValorMensalEnergia valoresMensaisEnergia[];
    private Kit[] kits;
    private Cliente[] clientes;
    private int indKit, indCliente;

    private TextView tvNumeroPlacas, tvInversor, tvInversorMais, tvInversorMenos, tvMes;
    private EditText etIrradiacao, etMedia;
    private RadioGroup rgMedia;
    private RadioButton rbTotal, rbMensal;
    private Button btnCalcular;
    private ImageView btnEsq, btnDir;
    private Spinner spKit, spCliente;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calculadoraViewModel =
                ViewModelProviders.of(this).get(CalculadoraViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calculadora, container, false);

        valoresMensaisEnergia = new ValorMensalEnergia[12];
        for (int i = 0; i < valoresMensaisEnergia.length; i++)
        {
            valoresMensaisEnergia[i] = new ValorMensalEnergia((i + 1) + "°");
        }

        spKit = root.findViewById(R.id.spCalculadoraKit);

        MyTask task = new MyTask(Kit[].class);
        task.execute(Enderecos.GET_KIT);
        while(task.isTrabalhando()) ;
        kits = (Kit[]) task.getDados();

        ArrayList<String> alNomeKits = new ArrayList<String>();
        for(Kit k : kits)
            alNomeKits.add(k.getNome());

        spKit.setAdapter(
            new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                0,
                alNomeKits)
        );

        spCliente = root.findViewById(R.id.spCalculadoraCliente);

        MyTask task2 = new MyTask(Cliente[].class);
        int codEmp = ((MainActivity)getActivity()).getUsuario().getCodEmpresa();
        task2.execute(Enderecos.GET_CLIENTE + "/" + codEmp);
        while(task2.isTrabalhando()) ;
        clientes = (Cliente[]) task2.getDados();

        ArrayList<String> alNomeClientes = new ArrayList<String>();
        for(Cliente c : clientes)
            alNomeClientes.add(c.getNome());

        spCliente.setAdapter(
            new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                0,
                alNomeClientes)
        );

        tvNumeroPlacas = root.findViewById(R.id.tvNumeroPlacas);
        tvInversor = root.findViewById(R.id.tvInversor);
        tvInversorMais = root.findViewById(R.id.tvInversorMais);
        tvInversorMenos = root.findViewById(R.id.tvInversorMenos);
        tvMes = root.findViewById(R.id.tvMes);
        etIrradiacao = root.findViewById(R.id.etIrradiacao);
        etMedia = root.findViewById(R.id.etMedia);

        rbTotal = root.findViewById(R.id.rbTotal);
        rbTotal.setChecked(true);// inicia em true
        rbMensal = root.findViewById(R.id.rbMensal);

        spKit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                indKit = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                indCliente = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

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
                try
                {
                    MyTask task = new MyTask(KitProduto[].class);
                    task.execute(Enderecos.GET_KITMODULO);
                    while(task.isTrabalhando()) ;
                    KitProduto[] kitmodulo = (KitProduto[]) task.getDados();

                    task = new MyTask(Modulo[].class);
                    task.execute(Enderecos.GET_MODULO + kitmodulo[0].getCodProduto());
                    while(task.isTrabalhando()) ;
                    Modulo[] modulo = (Modulo[]) task.getDados();

                    float watts = modulo[0].getPotencia();

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
                            watts) + "");
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
}
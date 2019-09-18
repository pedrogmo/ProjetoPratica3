package br.unicamp.cotuca.aplicativoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Toolbar toolBar_main;
    TextView tvNumeroPlacas, tvInversor, tvInversorMais, tvInversorMenos;
    EditText etIrradiacao, etMedia, etWatts;
    Button btnCalcular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolBar_main = (Toolbar) findViewById(R.id.toolBar_main);

        tvNumeroPlacas = findViewById(R.id.tvNumeroPlacas);
        tvInversor = findViewById(R.id.tvInversor);
        tvInversorMais = findViewById(R.id.tvInversorMais);
        tvInversorMenos = findViewById(R.id.tvInversorMenos);

        etIrradiacao = findViewById(R.id.etIrradiacao);
        etMedia = findViewById(R.id.etMedia);
        etWatts = findViewById(R.id.etWatts);

        btnCalcular = findViewById(R.id.btnCalcular);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etWatts.onEditorAction(EditorInfo.IME_ACTION_DONE);
                etIrradiacao.onEditorAction(EditorInfo.IME_ACTION_DONE);
                etMedia.onEditorAction(EditorInfo.IME_ACTION_DONE);
                limpar();
                tvInversor.setText(tvInversor.getText().toString() + CalculadoraFotoVoltaica.inversor(Double.parseDouble(etMedia.getText().toString()),
                        Double.parseDouble(etIrradiacao.getText().toString()))  + "");
                tvNumeroPlacas.setText(tvNumeroPlacas.getText().toString() + CalculadoraFotoVoltaica.numeroPlacas(Double.parseDouble(etMedia.getText().toString()),
                        Float.parseFloat(etIrradiacao.getText().toString()),
                        Double.parseDouble(etWatts.getText().toString())) + "");
                tvInversorMais.setText(tvInversorMais.getText().toString() + CalculadoraFotoVoltaica.inversor(Double.parseDouble(etMedia.getText().toString()),
                        Double.parseDouble(etIrradiacao.getText().toString()))  + "");
                tvInversorMenos.setText(tvInversorMenos.getText().toString() + CalculadoraFotoVoltaica.inversor(Double.parseDouble(etMedia.getText().toString()),
                        Double.parseDouble(etIrradiacao.getText().toString()))  + "");
            }
        });
    }
    private void limpar()
    {
        tvNumeroPlacas.setText("Número Placas:");
        tvInversorMais.setText("Inversor Mais:");
        tvInversorMenos.setText("Inversor Menos:");
        tvInversor.setText("Inversor:");
    }


}

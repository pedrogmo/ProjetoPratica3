package com.example.appandroidfotovoltaica.ui.mainactivity.propostas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.constantesdetransicao.ConstantesDeTransicao;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.classes.proposta.Proposta;
import com.example.appandroidfotovoltaica.ui.mainactivity.clientes.adicionarcliente.AdicionarClienteFragment;
import com.example.appandroidfotovoltaica.ui.mainactivity.propostas.visualizarproposta.VisualizarPropostaFragment;
import com.github.barteksc.pdfviewer.source.DocumentSource;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PropostasFragment extends Fragment {

    private static final int STORAGE_CODE = 1000;

    private PropostasViewModel propostasViewModel;

    private Button btnGerar;
    private ListView lvProposta;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.propostasViewModel = ViewModelProviders.of(this).get(PropostasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_propostas, container, false);

        this.btnGerar = (Button) root.findViewById(R.id.btnGerarPDF);
        this.lvProposta = (ListView) root.findViewById(R.id.lvListaPropostas);

        MyTask task = new MyTask(Proposta[].class);
        task.execute(Enderecos.GET_PROPOSTA);
        while(task.isTrabalhando()) ;
        Proposta[] arrProposta = (Proposta[]) task.getDados();

        ArrayList<String> alNomes = new ArrayList<String>();
        for(Proposta p : arrProposta)
            alNomes.add(p.getNome());

        this.lvProposta.setAdapter(
            new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                0,
                alNomes)
        );

        this.btnGerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                {
                    if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);
                    }
                    else
                    {
                        savePdf();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_propostas, new VisualizarPropostaFragment(), ConstantesDeTransicao.F_PROPOSTAS_VISUALIZAR).addToBackStack(ConstantesDeTransicao.M_PROPOSTAS_VISUALIZAR).commit();
                    }
                }
                else
                {

                }

            }


        });
        return root;
    }

    private void savePdf()
    {
        Document doc = new Document();
        String fileName = new SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()).format(System.currentTimeMillis());

        String filePath = Environment.getExternalStorageDirectory() + "/" + fileName + ".pdf";

        try
        {
            PdfWriter.getInstance(doc, new FileOutputStream(filePath));
            doc.open();
            String text = "O MEEIRO TEM UM GRANDE CORAÇÃO...";

            doc.addAuthor("Gustavo de Meira");

            doc.addTitle("Pdf do Meira");

            doc.add(new Paragraph(text));

            doc.close();

            Toast.makeText(
                getActivity().getApplicationContext(),
                fileName + ".pdf saved to \n " + filePath,
                Toast.LENGTH_SHORT).show();



            //i.putExtra("pdfEscolhido",() doc);

            /*Intent sendIntent = new Intent(Intent.ACTION_SEND);
            //Uri uri = FileProvider.getUriForFile(this, "  ", (File)doc).parse(filePath);

            sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sendIntent.setType("message/rfc822");


            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);*/
        }
        catch(Exception exc)
        {
            Toast.makeText(
                getActivity().getApplicationContext(),
                exc.toString(),
                Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case STORAGE_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                }
                else
                {
                    Toast.makeText(
                        getActivity().getApplicationContext(),
                        "Permission denied.",
                        Toast.LENGTH_SHORT).show();
                }
            }break;
        }
    }
}
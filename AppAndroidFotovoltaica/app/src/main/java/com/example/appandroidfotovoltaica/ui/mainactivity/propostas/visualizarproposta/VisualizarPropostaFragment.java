package com.example.appandroidfotovoltaica.ui.mainactivity.propostas.visualizarproposta;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.constantesdetransicao.ConstantesDeTransicao;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.classes.proposta.Proposta;
import com.github.barteksc.pdfviewer.source.DocumentSource;
import com.github.barteksc.pdfviewer.util.FileUtils;
import com.github.barteksc.pdfviewer.PDFView;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;



public class VisualizarPropostaFragment extends Fragment {

    private VisualizarPropostaViewModel propostasViewModel;

    private com.github.barteksc.pdfviewer.PDFView pdfView;
    private static final int STORAGE_CODE = 1000;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.propostasViewModel = ViewModelProviders.of(this).get(VisualizarPropostaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_visualizarproposta, container, false);

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
                //pdfView.fromSource((DocumentSource) savePdf()).load();
            }
        }
        else
        {

        }


        return root;
    }

    private void savePdf()
    {
        Document doc = new Document(PageSize.LETTER, 0.75F, 0.75F, 0.75F, 0.75F);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String fileName = new SimpleDateFormat(
                "yyyyMMdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());

        String filePath = Environment.getExternalStorageDirectory() + "/" + fileName + ".pdf";

        try
        {
            //PdfWriter.getInstance(doc, new FileOutputStream(filePath));
            PdfWriter.getInstance(doc, byteArrayOutputStream);
            doc.open();
            doc.add(new Chunk(""));
            String text = "O MEEIRO TEM UM GRANDE CORAÇÃO...";

            doc.addAuthor("Gustavo de Meira");

            doc.addTitle("Pdf do Meira");

            doc.add(new Paragraph(text));

            doc.close();

            //pdfView.fromBytes(byteArrayOutputStream.toByteArray()).load();
            //pdfView.fromFile()
            File f = new File(filePath);

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



        //pdfView.fromSource();



        //pdfView.fromBytes().load();


}
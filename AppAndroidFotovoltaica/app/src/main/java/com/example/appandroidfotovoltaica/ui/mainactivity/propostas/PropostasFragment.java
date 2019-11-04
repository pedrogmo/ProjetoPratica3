package com.example.appandroidfotovoltaica.ui.mainactivity.propostas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appandroidfotovoltaica.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PropostasFragment extends Fragment {

    private static final int STORAGE_CODE = 1000;

    private PropostasViewModel propostasViewModel;

    private Button btnGerar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.propostasViewModel = ViewModelProviders.of(this).get(PropostasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_propostas, container, false);

        this.btnGerar = (Button) root.findViewById(R.id.btnGerarProposta);

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
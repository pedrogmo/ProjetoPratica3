package com.example.appandroidfotovoltaica.ui.mainactivity.propostas.visualizarproposta;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.constantesdetransicao.ConstantesDeTransicao;
import com.example.appandroidfotovoltaica.classes.empresa.Empresa;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.classes.proposta.Proposta;
import com.example.appandroidfotovoltaica.classes.usuario.Usuario;
import com.github.barteksc.pdfviewer.source.DocumentSource;
import com.github.barteksc.pdfviewer.util.FileUtils;
import com.github.barteksc.pdfviewer.PDFView;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Element;

import org.w3c.dom.Text;

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
    private Usuario logado;
    private String nomeEmpresa;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.propostasViewModel = ViewModelProviders.of(this).get(VisualizarPropostaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_visualizarproposta, container, false);
        pdfView = root.findViewById(R.id.pdfView);

        logado = (Usuario)getActivity().getIntent().getSerializableExtra("usuario");


        MyTask task = new MyTask(Empresa[].class);
        task.execute(Enderecos.GET_EMPRESA + "/" + logado.getCodEmpresa());
        while (task.isTrabalhando()) ;
        Empresa[] resultEmpresas = (Empresa[]) task.getDados();

        nomeEmpresa = resultEmpresas[0].getNome();

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
        String fileName = "Proposta Meira";

        String filePath = Environment.getExternalStorageDirectory() + "/" + fileName + ".pdf";

        try
        {
            PdfWriter.getInstance(doc, byteArrayOutputStream);
            doc.open();
            doc.add(new Chunk(""));
            String nomeAutorProposta = logado.getNome();
            String nomeEmpresaProposta = nomeEmpresa;
            String nomeClient;
            String text = "O MEEIRO TEM UM GRANDE CORAÇÃO...";

            doc.addAuthor("Gustavo de Meira");

            doc.addTitle("Pdf do Meira");

            Paragraph paragrafoInicial = new Paragraph(text, new Font(Font.FontFamily.HELVETICA, 24, Font.NORMAL, BaseColor.BLACK));
            paragrafoInicial.setAlignment(Element.ALIGN_CENTER);



            doc.add(paragrafoInicial);

            PdfPTable table = new PdfPTable(3);
            PdfPCell cell = new PdfPCell(new Phrase("Cell with colspan 3"));
            cell.setColspan(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
            cell.setRowspan(2);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
            table.addCell("Cell 1.1");
            cell = new PdfPCell();
            cell.addElement(new Phrase("Cell 1.2"));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Cell 2.1"));
            cell.setPadding(5);
            cell.setUseAscender(true);
            cell.setUseDescender(true);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell();
            cell.setPadding(5);
            cell.setUseAscender(true);
            cell.setUseDescender(true);
            Paragraph p = new Paragraph("Cell 2.2");
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            table.addCell(cell);
            doc.add(table);
            doc.close();


            pdfView.fromBytes(byteArrayOutputStream.toByteArray()).load();
            //pdfView.fromFile()
            //File f = new File(filePath);

            //pdfView.fromFile(new File(filePath)).load();

            /*Toast.makeText(
                    getActivity().getApplicationContext(),
                    fileName + ".pdf saved to \n " + filePath,
                    Toast.LENGTH_SHORT).show();*/





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
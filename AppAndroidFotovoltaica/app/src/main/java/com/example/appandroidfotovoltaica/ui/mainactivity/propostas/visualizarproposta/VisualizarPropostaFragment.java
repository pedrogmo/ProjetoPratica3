package com.example.appandroidfotovoltaica.ui.mainactivity.propostas.visualizarproposta;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appandroidfotovoltaica.R;
import com.example.appandroidfotovoltaica.classes.categoria.Categoria;
import com.example.appandroidfotovoltaica.classes.cliente.Cliente;
import com.example.appandroidfotovoltaica.classes.empresa.Empresa;
import com.example.appandroidfotovoltaica.classes.enderecos.Enderecos;
import com.example.appandroidfotovoltaica.classes.kit.Kit;
import com.example.appandroidfotovoltaica.classes.kitproduto.KitProduto;
import com.example.appandroidfotovoltaica.classes.mytask.MyTask;
import com.example.appandroidfotovoltaica.classes.produto.Produto;
import com.example.appandroidfotovoltaica.classes.produto.cabo.Cabo;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.bombasolar.BombaSolar;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.inversor.Inversor;
import com.example.appandroidfotovoltaica.classes.produto.equipamento.modulo.Modulo;
import com.example.appandroidfotovoltaica.classes.produto.fixacao.Fixacao;
import com.example.appandroidfotovoltaica.classes.produto.stringbox.StringBox;
import com.example.appandroidfotovoltaica.classes.produtoquantidade.ProdutoQuantidade;
import com.example.appandroidfotovoltaica.classes.proposta.Proposta;
import com.example.appandroidfotovoltaica.classes.usuario.Usuario;
import com.example.appandroidfotovoltaica.ui.mainactivity.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class VisualizarPropostaFragment extends Fragment {

    private com.github.barteksc.pdfviewer.PDFView pdfView;
    private static final int STORAGE_CODE = 1000;
    private Kit kitAtual;
    private Usuario usuarioLogado;
    private Cliente cliente;
    private Empresa empresa;
    private Proposta propostaAtual;
    private ArrayList<ProdutoQuantidade> produtosKit;
    private Modulo[] arrModulo;
    private Inversor[] arrInversor;
    private StringBox[] arrStringBox;
    private Fixacao[] arrFixacao;
    private BombaSolar[] arrBombaSolar;
    private Cabo[] arrCabo;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_visualizarproposta, container, false);
        pdfView = root.findViewById(R.id.pdfView);


        usuarioLogado = ((MainActivity) getActivity()).getUsuario();

        Bundle bundle = getArguments();
        propostaAtual = (Proposta) bundle.getSerializable("proposta");



        MyTask task = new MyTask(Kit[].class);
        task.execute(Enderecos.GET_KIT + "/" + propostaAtual.getCodKit());
        while (task.isTrabalhando()) ;
        Kit[] resultKits = (Kit[]) task.getDados();

        kitAtual = resultKits[0];

        task = new MyTask(Empresa[].class);
        task.execute(Enderecos.GET_EMPRESA + "/" + usuarioLogado.getCodEmpresa());
        while (task.isTrabalhando()) ;
        Empresa[] resultEmpresas = (Empresa[]) task.getDados();

        empresa = resultEmpresas[0];

        task = new MyTask(Cliente[].class);
        task.execute(Enderecos.GET_CLIENTE + "/" + propostaAtual.getCodCliente());
        while (task.isTrabalhando()) ;
        Cliente[] resultClientes = (Cliente[]) task.getDados();

        cliente = resultClientes[0];

        fazerBuscas();
        produtosKit = produtosDoKit();

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
        String fileName = "Proposta Solarium";

        String filePath = Environment.getExternalStorageDirectory() + "/" + fileName + ".pdf";

        try
        {
            PdfWriter.getInstance(doc, byteArrayOutputStream);
            doc.open();
            doc.add(new Chunk(""));

            String header = "Proposta de " + usuarioLogado.getNome() + " (" + empresa.getNome() + ")";

            doc.addAuthor(usuarioLogado.getNome());

            doc.addTitle(propostaAtual.getNome());

            Paragraph cabecalho = new Paragraph(header, new Font(Font.FontFamily.HELVETICA, 24, Font.NORMAL, BaseColor.BLACK));
            cabecalho.setAlignment(Element.ALIGN_CENTER);

            doc.add(cabecalho);

            doc.add(new Paragraph("\n\n"));

            String textoIntroducao = "Os cálculos foram feitos com base nos valores obtidos em nossas análises, segue a proposta:";
            Paragraph paragrafoInicial = new Paragraph(textoIntroducao, new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, BaseColor.BLACK));
            paragrafoInicial.setAlignment(Element.ALIGN_CENTER);

            doc.add(paragrafoInicial);

            doc.add(new Paragraph("\n\n"));

            PdfPTable table = new PdfPTable(5);
            PdfPCell cell = new PdfPCell(new Phrase("Kit: " + kitAtual.getNome()));
            cell.setColspan(5);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            Paragraph categoria = new Paragraph("Tipo");
            categoria.setAlignment(Element.ALIGN_CENTER);
            cell = new PdfPCell(categoria);
            cell.setRowspan(1);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
            table.addCell("Modelo");
            cell = new PdfPCell();
            Paragraph qtd = new Paragraph("Quantidade Total");
            qtd.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(qtd);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
            Paragraph precoU = new Paragraph("Preço unitário");
            precoU.setAlignment(Element.ALIGN_CENTER);
            cell = new PdfPCell(precoU);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(5);
            cell.setUseAscender(true);
            cell.setUseDescender(true);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell();
            cell.setPadding(5);
            cell.setUseAscender(true);
            cell.setUseDescender(true);
            Paragraph precoT = new Paragraph("Preço total");
            precoT.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(precoT);
            table.addCell(cell);

            double valorTotal = 0, precoTotalProduto = 0;
            int qtdTotalProduto = 0;
            for(ProdutoQuantidade pq : produtosKit)
            {
                categoria = new Paragraph(Categoria.getCategoria(pq.getProduto()));
                categoria.setAlignment(Element.ALIGN_CENTER);
                cell = new PdfPCell(categoria);
                cell.setRowspan(1);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                table.addCell(pq.getProduto().getNome());
                cell = new PdfPCell();
                qtdTotalProduto  = pq.getQuantidade() * propostaAtual.getQtdKits();
                Log.d("MEIRAAAAAA",propostaAtual.getQtdKits() + "");
                qtd = new Paragraph(qtdTotalProduto + "");
                qtd.setAlignment(Element.ALIGN_CENTER);
                cell.addElement(qtd);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                precoU = new Paragraph(pq.getProduto().getPreco() + "");
                precoU.setAlignment(Element.ALIGN_CENTER);
                cell = new PdfPCell(precoU);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(5);
                cell.setUseAscender(true);
                cell.setUseDescender(true);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                cell = new PdfPCell();
                cell.setPadding(5);
                cell.setUseAscender(true);
                cell.setUseDescender(true);
                precoTotalProduto = pq.getProduto().getPreco() * qtdTotalProduto;
                valorTotal += precoTotalProduto;
                precoT = new Paragraph(precoTotalProduto + "");
                precoT.setAlignment(Element.ALIGN_CENTER);
                cell.addElement(precoT);
                table.addCell(cell);
            }

            doc.add(table);
            Paragraph custo = new Paragraph("Custo Total: R$" + valorTotal, new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, BaseColor.BLACK));
            custo.setAlignment(Element.ALIGN_CENTER);
            doc.add(custo);

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


    private void fazerBuscas()
    {
        int codEmpresa = ((MainActivity) getActivity()).getUsuario().getCodEmpresa();

        MyTask task = new MyTask(Modulo[].class);
        task.execute(Enderecos.GET_MODULO + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        arrModulo = (Modulo[]) task.getDados();

        task = new MyTask(Inversor[].class);
        task.execute(Enderecos.GET_INVERSOR + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        arrInversor = (Inversor[]) task.getDados();

        task = new MyTask(StringBox[].class);
        task.execute(Enderecos.GET_STRINGBOX + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        arrStringBox = (StringBox[]) task.getDados();

        task = new MyTask(Fixacao[].class);
        task.execute(Enderecos.GET_FIXACAO + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        arrFixacao = (Fixacao[]) task.getDados();

        task = new MyTask(BombaSolar[].class);
        task.execute(Enderecos.GET_BOMBASOLAR + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        arrBombaSolar = (BombaSolar[]) task.getDados();

        task = new MyTask(Cabo[].class);
        task.execute(Enderecos.GET_CABO + "/" + codEmpresa);
        while(task.isTrabalhando()) ;
        arrCabo = (Cabo[]) task.getDados();
    }

    private ArrayList<ProdutoQuantidade> produtosDoKit()
    {
        ArrayList<ProdutoQuantidade> ret = new ArrayList<ProdutoQuantidade>();

        Produto[][] vetores =
                {
                        arrModulo, arrInversor, arrStringBox, arrFixacao, arrBombaSolar, arrCabo
                };

        for(int i=0; i<6; ++i)
        {
            KitProduto[] kitProdutos;
            MyTask task = new MyTask(KitProduto[].class);
            task.execute(Categoria.ROTAS_GET_KITPRODUTO[i] + "/" + kitAtual.getCodigo());
            while (task.isTrabalhando()) ;
            kitProdutos = (KitProduto[]) task.getDados();

            for (KitProduto kp : kitProdutos)
            {
                Produto p = produtoCodigo(vetores[i], kp.getCodProduto());
                if (p != null)
                {
                    try
                    {
                        ret.add(new ProdutoQuantidade(p, kp.getQuantidade()));
                    }
                    catch (Exception exc) {}
                }
            }
        }
        return ret;
    }

    private Produto produtoCodigo(
            Produto[] produtos,
            int codigo)
    {
        for(Produto p : produtos)
            if (p.getCodigo() == codigo)
                return p;
        return null;
    }
}
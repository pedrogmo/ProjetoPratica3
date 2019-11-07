package com.example.appandroidfotovoltaica.ui.mainactivity.propostas.visualizarproposta;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appandroidfotovoltaica.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;


public class VisualizarPropostaFragment extends Fragment {

    private VisualizarPropostaViewModel propostasViewModel;

    private PdfView pdfView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.propostasViewModel = ViewModelProviders.of(this).get(VisualizarPropostaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_visualizarproposta, container, false);

        pdfView = root.findViewById(R.id.pdfView);

        pdfView.fromAsset("teste.pdf").load();

        return root;
    }
}
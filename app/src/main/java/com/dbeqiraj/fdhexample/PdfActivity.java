package com.dbeqiraj.fdhexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PdfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);

        String url = getIntent().getStringExtra("url");
        pdfView.fromFile(new File(url)).load();
    }
}

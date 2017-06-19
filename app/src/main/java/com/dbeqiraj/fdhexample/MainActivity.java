package com.dbeqiraj.fdhexample;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dbeqiraj.filedownloadhelper.FileDownloadHelper;
import com.dbeqiraj.filedownloadhelper.FileDownloadHelperListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FileDownloadHelperListener {

    final static String MP3_URL = "http://www.sample-videos.com/audio/mp3/crowd-cheering.mp3";
    final static String PDF_URL = "http://www.sample-videos.com/pdf/Sample-pdf-5mb.pdf";
    final static String MP4_URL = "http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4";

    private FileDownloadHelper fileDownloadHelper;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileDownloadHelper = new FileDownloadHelper(this);

        Button mp3 = (Button) findViewById(R.id.mp3);
        Button pdf = (Button) findViewById(R.id.pdf);
        Button mp4 = (Button) findViewById(R.id.mp4);

        mp3.setOnClickListener(this);
        pdf.setOnClickListener(this);
        mp4.setOnClickListener(this);
        fileDownloadHelper.setListener(this);

        dialog = buildProgressDialog();
    }

    @Override
    public void onClick(View v) {
        dialog.show();
        if ( v.getId() == R.id.mp3 ) {
            fileDownloadHelper.setUrl(MP3_URL);
        } else if ( v.getId() == R.id.pdf ) {
            fileDownloadHelper.setUrl(PDF_URL);
        } else if ( v.getId() == R.id.mp4 ) {
            fileDownloadHelper.setUrl(MP4_URL);
        }
        fileDownloadHelper.prepareAsync();
    }

    @Override
    public void onComplete(String newUrl) {
        dialog.cancel();
        Intent intent = null;
        switch (fileDownloadHelper.getUrl()) {
            case MP3_URL:
                intent = new Intent(this, Mp3Activity.class);
                break;
            case PDF_URL:
                intent = new Intent(this, PdfActivity.class);
                break;
            case MP4_URL:
                intent = new Intent(this, MP4Activity.class);
                break;
        }
        if (intent != null) {
            intent.putExtra("url", newUrl);
            startActivity(intent);
        }
    }

    @Override
    public void onFailed() {
        dialog.cancel();
        Toast.makeText(this, "Failed to load stream!", Toast.LENGTH_SHORT).show();
        Log.e(getClass().getSimpleName(), "Failed to load stream from url: " + fileDownloadHelper.getUrl());
    }

    Dialog buildProgressDialog() {
        ProgressDialog alertDialog = new ProgressDialog(this);
        alertDialog.setMessage("Loading...");
        alertDialog.setCancelable(false);
        return alertDialog;
    }
}

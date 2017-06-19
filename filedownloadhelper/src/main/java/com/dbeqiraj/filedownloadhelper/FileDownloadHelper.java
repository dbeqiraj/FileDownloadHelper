package com.dbeqiraj.filedownloadhelper;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by d.beqiraj on 6/10/2017.
 */

public class FileDownloadHelper {

    private Context context;
    private String url;
    private String format;
    private int timeout;
    private FileDownloadHelperListener listener;

    public FileDownloadHelper(Context context) {
        this.context = context;
    }

    public FileDownloadHelper(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    public FileDownloadHelper(Context context, String url, String format) {
        this.context = context;
        this.url = url;
        this.format = format;
    }

    public FileDownloadHelper(Context context, String url, String format, int timeout) {
        this.context = context;
        this.url = url;
        this.format = format;
        this.timeout = timeout;
    }

    public void prepareAsync() throws IllegalStateException {

        if (url != null) {
            if ( format == null )
                format = Utilities.findFormat(url);

            if ( listener != null && format != null) {
                new DoJob(context, url, format, timeout, listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else if ( listener == null ) {
                Toast.makeText(context, "Please, attach a listener before calling prepareAsync()!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Please, specify the file format!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Please, specify the url!", Toast.LENGTH_SHORT).show();
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setListener(FileDownloadHelperListener listener) {
        this.listener = listener;
    }
}

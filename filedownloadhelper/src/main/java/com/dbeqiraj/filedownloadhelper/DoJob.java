package com.dbeqiraj.filedownloadhelper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by d.beqiraj on 6/10/2017.
 */

public class DoJob extends AsyncTask<Void, Void, HashMap<String, Object>> {

    private Context context;
    private String path;
    private String format;
    private int timeout;
    private FileDownloadHelperListener listener;

    DoJob(Context context, String path, String format, int timeout, FileDownloadHelperListener listener) {
        this.context = context;
        this.path = path;
        this.format = format;
        this.timeout = timeout;
        this.listener = listener;
    }

    @Override
    protected HashMap<String, Object> doInBackground(Void... params) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", false);
        try {
            URL url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            if ( timeout >= 0 )
                urlConnection.setConnectTimeout(timeout);

            /* Create a temporary file to store the required file. */
            File file = new File(context.getCacheDir(), "helperfile." + format);
            file.deleteOnExit();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            byte[] buffer = new byte[1024];
            int bufferLength;
            long    startTime       =   System.currentTimeMillis();
            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                if ( System.currentTimeMillis() - startTime < timeout) {
                    fileOutputStream.close();
                    return result;
                } else {
                    fileOutputStream.write(buffer, 0, bufferLength);
                }
            }
            inputStream.close();
            fileOutputStream.close();
            result.put("success", true);
            result.put("url", file.getAbsolutePath());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), "Failed to load stream from url: " + path);
            return result;
        }
    }

    @Override
    protected void onCancelled(){
        listener.onFailed();
    }

    @Override
    protected void onPostExecute(HashMap<String, Object> result) {
        if ( (Boolean) result.get("success") ) {
            listener.onComplete((String) result.get("url"));
        } else {
            listener.onFailed();
        }
    }
}

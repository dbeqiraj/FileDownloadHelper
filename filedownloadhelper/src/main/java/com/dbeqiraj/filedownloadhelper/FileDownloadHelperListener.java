package com.dbeqiraj.filedownloadhelper;

/**
 * Created by d.beqiraj on 6/10/2017.
 */

public interface FileDownloadHelperListener {
    void onComplete(String newUrl);
    void onFailed();
}

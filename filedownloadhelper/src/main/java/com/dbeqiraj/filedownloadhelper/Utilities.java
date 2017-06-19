package com.dbeqiraj.filedownloadhelper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by d.beqiraj on 6/10/2017.
 */

public class Utilities {
    private final static String[] ALLOWED_FORMATS = {".3gp", ".mp4", ".m4a", ".aac", ".flac", ".mid", ".xmf",
            ".mxmf", ".rtttl", ".rtx", ".ota", ".imy", ".mp3", ".mkv", ".wav", ".ogg", ".webm",
            ".bmp", ".gif", ".jpg", ".png", ".webp",
            ".pdf", ".txt"};

    static String findFormat(String url){

        List<String> formats = Arrays.asList(ALLOWED_FORMATS);

        for ( String format : formats ) {
            if ( url.toLowerCase().contains(format) ) {
                return format.substring(1);
            }
        }
        return null;
    }
}

package com.muedsa.bilibililiveapiclient.util;

public class ApiUtil {
    public static String fillUrl(String url, Object... args){
        return String.format(url, args);
    }

    public static String removeSearchHighlight(String content){
        return content
                .replaceAll("<em class=\"keyword\">", "")
                .replaceAll("</em>", "");
    }
}

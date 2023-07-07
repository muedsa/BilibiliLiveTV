package com.muedsa.bilibililiveapiclient.util;

import com.google.common.escape.Escaper;
import com.google.common.net.UrlEscapers;

import java.util.Map;
import java.util.StringJoiner;

public class ApiUtil {
    private static final Escaper QUERY_ESCAPER = UrlEscapers.urlFormParameterEscaper();

    public static String fillUrl(String url, Object... args){
        return String.format(url, args);
    }

    public static String removeSearchHighlight(String content){
        return content
                .replaceAll("<em class=\"keyword\">", "")
                .replaceAll("</em>", "");
    }

    public static String stringSortedParams(Map<String, Object> params) {
        StringJoiner param = new StringJoiner("&");
        params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> param.add(entry.getKey() + "=" + QUERY_ESCAPER.escape(entry.getValue().toString())));
        return param.toString();
    }

    public static String stringParams(Map<String, Object> params) {
        StringJoiner param = new StringJoiner("&");
        params.forEach((key, value) -> param.add(key + "=" + QUERY_ESCAPER.escape(value.toString())));
        return param.toString();
    }

    public static String buildUrlWithParams(String url, Map<String, Object> params) {
        return url + "?" + stringParams(params);
    }
}

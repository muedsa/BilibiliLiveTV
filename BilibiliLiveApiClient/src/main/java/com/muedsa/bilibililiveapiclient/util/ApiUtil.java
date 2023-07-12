package com.muedsa.bilibililiveapiclient.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

public class ApiUtil {

    public static String fillUrl(String url, Object... args) {
        return String.format(url, args);
    }

    public static String removeSearchHighlight(String content) {
        return content
                .replaceAll("<em class=\"keyword\">", "")
                .replaceAll("</em>", "");
    }

    public static String stringSortedParams(Map<String, Object> params) {
        StringJoiner param = new StringJoiner("&");
        params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> param.add(entry.getKey() + "=" + encodeQueryParamValue(entry.getValue().toString())));
        return param.toString();
    }

    public static String stringParams(Map<String, Object> params) {
        StringJoiner param = new StringJoiner("&");
        params.forEach((key, value) -> param.add(key + "=" + encodeQueryParamValue(value.toString())));
        return param.toString();
    }

    public static String buildUrlWithParams(String url, Map<String, Object> params) {
        return url + "?" + stringParams(params);
    }

    private static String encodeQueryParamValue(String paramValue) {
        String encodeValue = null;
        try {
            encodeValue = URLEncoder.encode(paramValue, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException ignored) {
        }
        return encodeValue;
    }
}

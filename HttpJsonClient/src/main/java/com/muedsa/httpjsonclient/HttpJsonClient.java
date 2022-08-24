package com.muedsa.httpjsonclient;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

public class HttpJsonClient {

    public static final String HEADER_KEY_USER_AGENT = "User-Agent";
    public static final String HEADER_KEY_COOKIE = "Cookie";

    public static final String HEADER_VALUE_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36";

    public <T> T GetJson(String url, TypeReference<T> type) throws IOException {
        return GetJson(url, type, Collections.singletonMap(HEADER_KEY_USER_AGENT, HEADER_VALUE_USER_AGENT));
    }

    public <T> T GetJson(String url, TypeReference<T> type, Map<String, String> headers) throws IOException {
        URL urlObj = new URL(url);
        URLConnection urlConnection = urlObj.openConnection();
        if(headers != null) headers.forEach(urlConnection::setRequestProperty);
        String result = convertStreamToString(urlConnection.getInputStream());
        result = beforeJsonParse(result);
        return JSON.parseObject(result, type);
    }

    public <T> T PostJson(String url, Map<String, Object> params, TypeReference<T> type) throws IOException {
        return PostJson(url, params, type, Collections.singletonMap(HEADER_KEY_USER_AGENT, HEADER_VALUE_USER_AGENT));
    }

    public <T> T PostJson(String url, Map<String, Object> params, TypeReference<T> type, Map<String, String> headers) throws IOException {
        StringBuilder postData = new StringBuilder();
        if(params != null){
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "utf-8"))
                        .append("=")
                        .append(URLEncoder.encode(String.valueOf(param.getValue()), "utf-8"));
            }
        }
        URL urlObj = new URL(url);
        URLConnection urlConnection = urlObj.openConnection();
        if(headers != null) headers.forEach(urlConnection::setRequestProperty);
        urlConnection.setConnectTimeout(2000);
        urlConnection.setReadTimeout(5000);
        urlConnection.setDoOutput(true);
        urlConnection.getOutputStream().write(postData.toString().getBytes(StandardCharsets.UTF_8));
        String result = convertStreamToString(urlConnection.getInputStream());
        result = beforeJsonParse(result);
        return JSON.parseObject(result, type);
    }

    private String convertStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try{
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        finally {
            safeClose(inputStream);
            safeClose(reader);
        }
        return sb.toString();
    }
    private void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String beforeJsonParse(String json) {
        return SpecialJsonUtil.fixDateTime(json);
    }
}
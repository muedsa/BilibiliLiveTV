package com.muedsa.httpjsonclient;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SimpleHttpClient {

    public URLConnection connect(String url, Map<String, String> headers) throws IOException {
        URL urlObj = new URL(url);
        URLConnection urlConnection = urlObj.openConnection();
        if(headers != null) headers.forEach(urlConnection::setRequestProperty);
        urlConnection.setConnectTimeout(2000);
        urlConnection.setReadTimeout(3000);
        return urlConnection;
    }

    public String get(String url, Map<String, String> headers) throws IOException {
        URLConnection connect = connect(url, headers);
        return IOUtil.convertStreamToString(connect.getInputStream());
    }

    public String post(String url, byte[] data, Map<String, String> headers) throws IOException {
        URLConnection connect = connect(url, headers);
        connect.setDoOutput(true);
        connect.getOutputStream().write(data);
        return IOUtil.convertStreamToString(connect.getInputStream());
    }

    public String post(String url, Map<String, Object> params, Map<String, String> headers) throws IOException {
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
        return post(url, postData.toString().getBytes(StandardCharsets.UTF_8), headers);
    }
}

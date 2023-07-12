package com.muedsa.httpjsonclient;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SimpleHttpClient {

    public URLConnection connect(String url, Map<String, String> headers) throws IOException {
        URL urlObj = new URL(url);
        URLConnection urlConnection = urlObj.openConnection();
        setHeader(urlConnection, headers);
        urlConnection.setConnectTimeout(2000);
        urlConnection.setReadTimeout(3000);
        return urlConnection;
    }

    private void setHeader(URLConnection urlConnection, Map<String, String> headers) {
        Map<String, String> newHeaders;
        if (headers != null) {
            newHeaders = new HashMap<>(headers);
        } else {
            newHeaders = new HashMap<>();
        }
        newHeaders.computeIfAbsent(HttpClientContainer.HEADER_KEY_HOST, k -> urlConnection.getURL().getHost());
        newHeaders.forEach(urlConnection::setRequestProperty);
    }

    public byte[] getByteArray(String url, Map<String, String> headers) throws IOException {
        URLConnection connect = connect(url, headers);
        return IOUtil.convertStreamToByteArray(connect.getInputStream(), connect.getHeaderField(HttpClientContainer.HEADER_KEY_CONTENT_ENCODING));
    }

    public String get(String url, Map<String, String> headers) throws IOException {
        URLConnection connect = connect(url, headers);
        return IOUtil.convertStreamToString(connect.getInputStream(), connect.getHeaderField(HttpClientContainer.HEADER_KEY_CONTENT_ENCODING), StandardCharsets.UTF_8.name());
    }

    public String post(String url, byte[] data, Map<String, String> headers) throws IOException {
        URLConnection connect = connect(url, headers);
        connect.setDoOutput(true);
        connect.getOutputStream().write(data);
        return IOUtil.convertStreamToString(connect.getInputStream(), connect.getHeaderField(HttpClientContainer.HEADER_KEY_CONTENT_ENCODING), StandardCharsets.UTF_8.name());
    }

    public String post(String url, Map<String, Object> params, Map<String, String> headers) throws IOException {
        StringBuilder postData = new StringBuilder();
        if (params != null) {
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

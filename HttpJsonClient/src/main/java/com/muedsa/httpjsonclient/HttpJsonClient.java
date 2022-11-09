package com.muedsa.httpjsonclient;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

public class HttpJsonClient extends SimpleHttpClient {

    public <T> T getJson(String url, TypeReference<T> type) throws IOException {
        return getJson(url, type, Collections.singletonMap(Container.HEADER_KEY_USER_AGENT, Container.HEADER_VALUE_USER_AGENT));
    }

    public <T> T getJson(String url, TypeReference<T> type, Map<String, String> headers) throws IOException {
        String result = get(url, headers);
        result = beforeJsonParse(result);
        return JSON.parseObject(result, type);
    }

    public <T> T postJson(String url, Map<String, Object> params, TypeReference<T> type) throws IOException {
        return postJson(url, params, type, Collections.singletonMap(Container.HEADER_KEY_USER_AGENT, Container.HEADER_VALUE_USER_AGENT));
    }

    public <T> T postJson(String url, Map<String, Object> params, TypeReference<T> type, Map<String, String> headers) throws IOException {
        String result = post(url, params, headers);
        result = beforeJsonParse(result);
        return JSON.parseObject(result, type);
    }

    private String beforeJsonParse(String json) {
        return SpecialJsonUtil.fixDateTime(json);
    }
}
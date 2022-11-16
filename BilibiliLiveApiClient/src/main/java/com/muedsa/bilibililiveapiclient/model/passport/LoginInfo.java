package com.muedsa.bilibililiveapiclient.model.passport;

import com.alibaba.fastjson2.annotation.JSONField;

public class LoginInfo {
    @JSONField(name = "url")
    private String url;

    @JSONField(name = "refresh_token")
    private String refreshToken;

    @JSONField(name = "timestamp")
    private Long timestamp;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}

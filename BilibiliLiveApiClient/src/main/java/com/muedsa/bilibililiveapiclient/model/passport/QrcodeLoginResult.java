package com.muedsa.bilibililiveapiclient.model.passport;

import com.alibaba.fastjson2.annotation.JSONField;

public class QrcodeLoginResult {

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "refresh_token")
    private String refreshToken;

    @JSONField(name = "timestamp")
    private Long timestamp;

    @JSONField(name = "code")
    private Integer code;

    @JSONField(name = "message")
    private String message;

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

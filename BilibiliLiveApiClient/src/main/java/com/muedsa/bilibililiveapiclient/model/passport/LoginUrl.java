package com.muedsa.bilibililiveapiclient.model.passport;

import com.alibaba.fastjson2.annotation.JSONField;

public class LoginUrl {

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "oauthKey")
    private String oauthKey;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOauthKey() {
        return oauthKey;
    }

    public void setOauthKey(String oauthKey) {
        this.oauthKey = oauthKey;
    }
}

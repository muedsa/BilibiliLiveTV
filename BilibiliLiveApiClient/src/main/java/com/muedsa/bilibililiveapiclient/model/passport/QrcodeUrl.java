package com.muedsa.bilibililiveapiclient.model.passport;

import com.alibaba.fastjson2.annotation.JSONField;

public class QrcodeUrl {

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "qrcode_key")
    private String qrcodeKey;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQrcodeKey() {
        return qrcodeKey;
    }

    public void setQrcodeKey(String qrcodeKey) {
        this.qrcodeKey = qrcodeKey;
    }
}

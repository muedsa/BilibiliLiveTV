package com.muedsa.bilibililiveapiclient.model.passport;

import com.alibaba.fastjson2.annotation.JSONField;

public class LoginResponse {

    @JSONField(name = "status")
    private Boolean status;

    @JSONField(deserialize = false)
    private Integer intData;

    @JSONField(deserialize = false)
    private LoginInfo data;

    @JSONField(name = "message")
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getIntData() {
        return intData;
    }

    public void setIntData(Integer intData) {
        this.intData = intData;
    }

    public LoginInfo getData() {
        return data;
    }

    public void setData(LoginInfo data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

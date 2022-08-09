package com.muedsa.github.model;

import com.alibaba.fastjson2.annotation.JSONField;

public class BaseResponse<T> {
    @JSONField(name = "code")
    private Integer code;
    @JSONField(name = "msg")
    private String msg;
    @JSONField(name = "data")
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

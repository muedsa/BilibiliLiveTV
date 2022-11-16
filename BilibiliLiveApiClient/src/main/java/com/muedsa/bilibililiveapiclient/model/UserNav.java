package com.muedsa.bilibililiveapiclient.model;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.Objects;

public class UserNav {

    @JSONField(name = "isLogin")
    private Boolean isLogin;

    @JSONField(name = "face")
    private String face;

    @JSONField(name = "uname")
    public String uname;

    // todo more field

    public boolean isLogin() {
        return Objects.nonNull(isLogin) && isLogin;
    }

    public void setLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    public Boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}

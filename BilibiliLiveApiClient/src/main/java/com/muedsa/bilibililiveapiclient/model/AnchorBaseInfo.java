package com.muedsa.bilibililiveapiclient.model;

import com.alibaba.fastjson2.annotation.JSONField;

public class AnchorBaseInfo {

    @JSONField(name = "uname")
    private String uname;

    @JSONField(name = "face")
    private String face;

    @JSONField(name = "gender")
    private String gender;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

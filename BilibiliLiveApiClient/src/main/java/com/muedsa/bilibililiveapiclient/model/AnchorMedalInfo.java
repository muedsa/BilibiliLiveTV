package com.muedsa.bilibililiveapiclient.model;

import com.alibaba.fastjson.annotation.JSONField;

public class AnchorMedalInfo {

    @JSONField(name = "medal_name")
    private String medalName;

    @JSONField(name = "medal_id")
    private Integer medalId;

    @JSONField(name = "fansclub")
    private Integer fansClub;

    public String getMedalName() {
        return medalName;
    }

    public void setMedalName(String medalName) {
        this.medalName = medalName;
    }

    public Integer getMedalId() {
        return medalId;
    }

    public void setMedalId(Integer medalId) {
        this.medalId = medalId;
    }

    public Integer getFansClub() {
        return fansClub;
    }

    public void setFansClub(Integer fansClub) {
        this.fansClub = fansClub;
    }
}

package com.muedsa.bilibililiveapiclient.model.search;

import com.alibaba.fastjson2.annotation.JSONField;

public class WatchedShow {

    @JSONField(name = "switch")
    private Boolean isSwitch;

    @JSONField(name = "num")
    private Integer num;

    @JSONField(name = "text_small")
    private String textSmall;

    @JSONField(name = "text_large")
    private String textLarge;

    @JSONField(name = "icon")
    private String icon;

    @JSONField(name = "icon_location")
    private String iconLocation;

    @JSONField(name = "icon_web")
    private String iconWeb;

    public Boolean getSwitch() {
        return isSwitch;
    }

    public void setSwitch(Boolean aSwitch) {
        isSwitch = aSwitch;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTextSmall() {
        return textSmall;
    }

    public void setTextSmall(String textSmall) {
        this.textSmall = textSmall;
    }

    public String getTextLarge() {
        return textLarge;
    }

    public void setTextLarge(String textLarge) {
        this.textLarge = textLarge;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconLocation() {
        return iconLocation;
    }

    public void setIconLocation(String iconLocation) {
        this.iconLocation = iconLocation;
    }

    public String getIconWeb() {
        return iconWeb;
    }

    public void setIconWeb(String iconWeb) {
        this.iconWeb = iconWeb;
    }
}

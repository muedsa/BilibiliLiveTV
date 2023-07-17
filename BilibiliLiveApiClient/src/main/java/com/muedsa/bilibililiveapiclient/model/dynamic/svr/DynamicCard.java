package com.muedsa.bilibililiveapiclient.model.dynamic.svr;

import com.alibaba.fastjson2.annotation.JSONField;

public class DynamicCard {

    @JSONField(name = "desc")
    private DynamicDesc desc;

    @JSONField(name = "card")
    private String card;

    @JSONField(name = "extend_json")
    private String extendJson;

    @JSONField(name = "display")
    private DynamicDisplay display;

    public DynamicDesc getDesc() {
        return desc;
    }

    public void setDesc(DynamicDesc desc) {
        this.desc = desc;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getExtendJson() {
        return extendJson;
    }

    public void setExtendJson(String extendJson) {
        this.extendJson = extendJson;
    }

    public DynamicDisplay getDisplay() {
        return display;
    }

    public void setDisplay(DynamicDisplay display) {
        this.display = display;
    }
}

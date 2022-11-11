package com.muedsa.bilibililiveapiclient.model.history;

import com.alibaba.fastjson2.annotation.JSONField;

public class HistoryTab {
    @JSONField(name = "max")
    private String type;

    @JSONField(name = "max")
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

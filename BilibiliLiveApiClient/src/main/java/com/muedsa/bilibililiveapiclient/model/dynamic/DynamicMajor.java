package com.muedsa.bilibililiveapiclient.model.dynamic;

import com.alibaba.fastjson2.annotation.JSONField;

public class DynamicMajor {

    @JSONField(name = "archive")
    private MajorArchive archive;

    @JSONField(name = "type")
    private String type;

    public MajorArchive getArchive() {
        return archive;
    }

    public void setArchive(MajorArchive archive) {
        this.archive = archive;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

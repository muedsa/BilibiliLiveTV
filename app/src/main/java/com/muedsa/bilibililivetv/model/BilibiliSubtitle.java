package com.muedsa.bilibililivetv.model;

import com.alibaba.fastjson2.annotation.JSONField;

import java.io.Serializable;

public class BilibiliSubtitle implements Serializable {

    @JSONField(name = "from")
    private float from;

    @JSONField(name = "to")
    private float to;

    @JSONField(name = "location")
    private int location;

    @JSONField(name = "content")
    private String content;

    public float getFrom() {
        return from;
    }

    public void setFrom(float from) {
        this.from = from;
    }

    public float getTo() {
        return to;
    }

    public void setTo(float to) {
        this.to = to;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

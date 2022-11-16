package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

public class VideoSubtitle {

    @JSONField(name = "id")
    private Long id;

    @JSONField(name = "lan")
    private String lan;

    @JSONField(name = "lan_doc")
    private String lan_doc;

    @JSONField(name = "is_lock")
    private Boolean isLock;

    @JSONField(name = "subtitle_url")
    private String subtitleUrl;

    @JSONField(name = "type")
    private Integer type;

    @JSONField(name = "id_str")
    private String idStr;

    @JSONField(name = "ai_type")
    private Integer aiType;

    @JSONField(name = "ai_status")
    private Integer aiStatus;

    @JSONField(name = "author")
    private VideoSubtitleAuthor author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getLan_doc() {
        return lan_doc;
    }

    public void setLan_doc(String lan_doc) {
        this.lan_doc = lan_doc;
    }

    public Boolean getLock() {
        return isLock;
    }

    public void setLock(Boolean lock) {
        isLock = lock;
    }

    public String getSubtitleUrl() {
        return subtitleUrl;
    }

    public void setSubtitleUrl(String subtitleUrl) {
        this.subtitleUrl = subtitleUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public Integer getAiType() {
        return aiType;
    }

    public void setAiType(Integer aiType) {
        this.aiType = aiType;
    }

    public Integer getAiStatus() {
        return aiStatus;
    }

    public void setAiStatus(Integer aiStatus) {
        this.aiStatus = aiStatus;
    }

    public VideoSubtitleAuthor getAuthor() {
        return author;
    }

    public void setAuthor(VideoSubtitleAuthor author) {
        this.author = author;
    }
}

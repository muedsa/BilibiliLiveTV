package com.muedsa.bilibililiveapiclient.model.dynamic;

import com.alibaba.fastjson2.annotation.JSONField;
import com.muedsa.bilibililiveapiclient.model.Badge;

public class MajorArchive {

    @JSONField(name = "aid")
    private String aid;

    @JSONField(name = "badge")
    private Badge badge;

    @JSONField(name = "bvid")
    private String bvid;

    @JSONField(name = "cover")
    private String cover;

    @JSONField(name = "desc")
    private String desc;

    @JSONField(name = "disable_preview")
    private Integer disablePreview;

    @JSONField(name = "duration_text")
    private String durationText;

    @JSONField(name = "jump_url")
    private String jumpUrl;

    @JSONField(name = "state")
    private MajorArchiveState state;

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "type")
    private Integer type;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getDisablePreview() {
        return disablePreview;
    }

    public void setDisablePreview(Integer disablePreview) {
        this.disablePreview = disablePreview;
    }

    public String getDurationText() {
        return durationText;
    }

    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public MajorArchiveState getState() {
        return state;
    }

    public void setState(MajorArchiveState state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

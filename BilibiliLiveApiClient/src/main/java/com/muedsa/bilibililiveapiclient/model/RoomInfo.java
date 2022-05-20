package com.muedsa.bilibililiveapiclient.model;

import com.alibaba.fastjson.annotation.JSONField;

public class RoomInfo {

    @JSONField(name = "uid")
    private Long uid;

    @JSONField(name = "room_id")
    private Long roomId;

    @JSONField(name = "short_id")
    private Long shortId;

    @JSONField(name = "online")
    private Integer online;

    @JSONField(name = "description")
    private String description;

    @JSONField(name = "live_status")
    private Integer liveStatus;

    @JSONField(name = "background")
    private String background;

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "cover")
    private String cover;

    @JSONField(name = "keyframe")
    private String keyframe;

    @JSONField(name = "tags")
    private String tags;

    @JSONField(name = "area_id")
    private Integer areaId;

    @JSONField(name = "area_name")
    private String areaName;

    @JSONField(name = "parent_area_id")
    private Integer parentAreaId;

    @JSONField(name = "parent_area_name")
    private String parentAreaName;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getShortId() {
        return shortId;
    }

    public void setShortId(Long shortId) {
        this.shortId = shortId;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(Integer liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getKeyframe() {
        return keyframe;
    }

    public void setKeyframe(String keyframe) {
        this.keyframe = keyframe;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getParentAreaId() {
        return parentAreaId;
    }

    public void setParentAreaId(Integer parentAreaId) {
        this.parentAreaId = parentAreaId;
    }

    public String getParentAreaName() {
        return parentAreaName;
    }

    public void setParentAreaName(String parentAreaName) {
        this.parentAreaName = parentAreaName;
    }
}

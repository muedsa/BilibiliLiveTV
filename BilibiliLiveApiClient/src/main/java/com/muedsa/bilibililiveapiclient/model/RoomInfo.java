package com.muedsa.bilibililiveapiclient.model;

import com.alibaba.fastjson.annotation.JSONField;

public class RoomInfo {

    @JSONField(name = "uid")
    private Long uid;

    @JSONField(name = "room_id")
    private Long roomId;

    @JSONField(name = "short_id")
    private Long shortId;

    @JSONField(name = "attention")
    private Long attention;

    @JSONField(name = "online")
    private Integer online;

    @JSONField(name = "is_portrait")
    private Boolean isPortrait;

    @JSONField(name = "description")
    private String description;

    @JSONField(name = "live_status")
    private Integer liveStatus;

    @JSONField(name = "area_id")
    private Integer areaId;

    @JSONField(name = "parent_area_name")
    private String parentAreaName;

    @JSONField(name = "old_area_id")
    private Integer oldAreaId;

    @JSONField(name = "background")
    private String background;

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "user_cover")
    private String userCover;

    @JSONField(name = "keyframe")
    private String keyframe;

    @JSONField(name = "is_strict_room")
    private Boolean isStrictRoom;

    @JSONField(name = "live_time")
    private String liveTime;

    @JSONField(name = "tags")
    private String tags;

    @JSONField(name = "is_anchor")
    private Integer isAnchor;

    @JSONField(name = "area_name")
    private String areaName;

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

    public Long getAttention() {
        return attention;
    }

    public void setAttention(Long attention) {
        this.attention = attention;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Boolean getPortrait() {
        return isPortrait;
    }

    public void setPortrait(Boolean portrait) {
        isPortrait = portrait;
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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getParentAreaName() {
        return parentAreaName;
    }

    public void setParentAreaName(String parentAreaName) {
        this.parentAreaName = parentAreaName;
    }

    public Integer getOldAreaId() {
        return oldAreaId;
    }

    public void setOldAreaId(Integer oldAreaId) {
        this.oldAreaId = oldAreaId;
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

    public String getUserCover() {
        return userCover;
    }

    public void setUserCover(String userCover) {
        this.userCover = userCover;
    }

    public String getKeyframe() {
        return keyframe;
    }

    public void setKeyframe(String keyframe) {
        this.keyframe = keyframe;
    }

    public Boolean getStrictRoom() {
        return isStrictRoom;
    }

    public void setStrictRoom(Boolean strictRoom) {
        isStrictRoom = strictRoom;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getIsAnchor() {
        return isAnchor;
    }

    public void setIsAnchor(Integer isAnchor) {
        this.isAnchor = isAnchor;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}

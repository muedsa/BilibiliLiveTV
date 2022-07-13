package com.muedsa.bilibililiveapiclient.model.search;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.Date;
import java.util.List;

public class LiveUser {

    @JSONField(name = "area")
    private Integer area;

    @JSONField(name = "area_v2_id")
    private Integer areaV2Id;

    @JSONField(name = "attentions")
    private Integer attentions;

    @JSONField(name = "cate_name")
    private String cateName;

    @JSONField(name = "hit_columns")
    private List<String> hitColumns;

    @JSONField(name = "is_live")
    private Boolean isLive;

    @JSONField(name = "live_status")
    private Integer liveStatus;

    @JSONField(name = "live_time", format = "yyyy-MM-dd HH:mm:ss")
    private Date liveTime;

    @JSONField(name = "rank_index")
    private Integer rankIndex;

    @JSONField(name = "rank_offset")
    private Integer rankOffset;

    @JSONField(name = "rank_score")
    private Integer rankScore;

    @JSONField(name = "roomid")
    private Long roomId;

    @JSONField(name = "short_id")
    private Long shortId;

    @JSONField(name = "tags")
    private String tags;

    @JSONField(name = "type")
    private String type;

    @JSONField(name = "uface")
    private String uface;

    @JSONField(name = "uid")
    private Integer uid;

    @JSONField(name = "uname")
    private String uname;

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getAreaV2Id() {
        return areaV2Id;
    }

    public void setAreaV2Id(Integer areaV2Id) {
        this.areaV2Id = areaV2Id;
    }

    public Integer getAttentions() {
        return attentions;
    }

    public void setAttentions(Integer attentions) {
        this.attentions = attentions;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public List<String> getHitColumns() {
        return hitColumns;
    }

    public void setHitColumns(List<String> hitColumns) {
        this.hitColumns = hitColumns;
    }

    public Boolean getLive() {
        return isLive;
    }

    public void setLive(Boolean live) {
        isLive = live;
    }

    public Integer getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(Integer liveStatus) {
        this.liveStatus = liveStatus;
    }

    public Date getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(Date liveTime) {
        this.liveTime = liveTime;
    }

    public Integer getRankIndex() {
        return rankIndex;
    }

    public void setRankIndex(Integer rankIndex) {
        this.rankIndex = rankIndex;
    }

    public Integer getRankOffset() {
        return rankOffset;
    }

    public void setRankOffset(Integer rankOffset) {
        this.rankOffset = rankOffset;
    }

    public Integer getRankScore() {
        return rankScore;
    }

    public void setRankScore(Integer rankScore) {
        this.rankScore = rankScore;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUface() {
        return uface;
    }

    public void setUface(String uface) {
        this.uface = uface;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}

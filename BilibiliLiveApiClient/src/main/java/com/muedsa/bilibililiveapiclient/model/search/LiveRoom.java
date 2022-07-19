package com.muedsa.bilibililiveapiclient.model.search;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LiveRoom {

    @JSONField(name = "area")
    private Integer area;

    @JSONField(name = "attentions")
    private Integer attentions;

    @JSONField(name = "cate_name")
    private String cateName;

    @JSONField(name = "cover")
    private String cover;

    @JSONField(name = "hit_columns")
    private List<String> hitColumns;

    @JSONField(name = "is_live_room_inline")
    private Integer isLiveRoomInline;

    @JSONField(name = "live_status")
    private Integer liveStatus;

    @JSONField(name = "live_time", format = "yyyy-MM-dd HH:mm:ss", locale = "zh_CN")
    private Date liveTime;

    @JSONField(name = "online")
    private Integer online;

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

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "type")
    private String type;

    @JSONField(name = "uface")
    private String uface;

    @JSONField(name = "uid")
    private Long uid;

    @JSONField(name = "uname")
    private String uname;

    @JSONField(name = "user_cover")
    private String userCover;

    @JSONField(name = "watched_show")
    private WatchedShow watchedShow;

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<String> getHitColumns() {
        return hitColumns;
    }

    public void setHitColumns(List<String> hitColumns) {
        this.hitColumns = hitColumns;
    }

    public Integer getIsLiveRoomInline() {
        return isLiveRoomInline;
    }

    public void setIsLiveRoomInline(Integer isLiveRoomInline) {
        this.isLiveRoomInline = isLiveRoomInline;
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

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUserCover() {
        return userCover;
    }

    public void setUserCover(String userCover) {
        this.userCover = userCover;
    }

    public WatchedShow getWatchedShow() {
        return watchedShow;
    }

    public void setWatchedShow(WatchedShow watchedShow) {
        this.watchedShow = watchedShow;
    }
}

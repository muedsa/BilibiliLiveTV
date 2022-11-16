package com.muedsa.bilibililiveapiclient.model.history;

import com.alibaba.fastjson2.annotation.JSONField;

public class HistoryRecord {

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "long_title")
    private String longTitle;

    @JSONField(name = "cover")
    private String cover;

    @JSONField(name = "covers")
    private String covers;

    @JSONField(name = "uri")
    private String uri;

    @JSONField(name = "history")
    private BaseInfo history;

    @JSONField(name = "videos")
    private Integer videos;

    @JSONField(name = "author_name")
    private String authorName;

    @JSONField(name = "author_face")
    private String authorFace;

    @JSONField(name = "author_mid")
    private String authorMid;

    @JSONField(name = "view_at")
    private Long viewAt;

    @JSONField(name = "progress")
    private Integer progress;

    @JSONField(name = "badge")
    private String badge;

    @JSONField(name = "show_title")
    private String showTitle;

    @JSONField(name = "duration")
    private Long duration;

    @JSONField(name = "current")
    private String current;

    @JSONField(name = "total")
    private Integer total;

    @JSONField(name = "new_desc")
    private String newDesc;

    @JSONField(name = "is_finish")
    private Integer isFinish;

    @JSONField(name = "is_fav")
    private Integer isFav;

    @JSONField(name = "kid")
    private Long kid;

    @JSONField(name = "tag_name")
    private String tagName;

    @JSONField(name = "live_status")
    private Integer liveStatus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLongTitle() {
        return longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCovers() {
        return covers;
    }

    public void setCovers(String covers) {
        this.covers = covers;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public BaseInfo getHistory() {
        return history;
    }

    public void setHistory(BaseInfo history) {
        this.history = history;
    }

    public Integer getVideos() {
        return videos;
    }

    public void setVideos(Integer videos) {
        this.videos = videos;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorFace() {
        return authorFace;
    }

    public void setAuthorFace(String authorFace) {
        this.authorFace = authorFace;
    }

    public String getAuthorMid() {
        return authorMid;
    }

    public void setAuthorMid(String authorMid) {
        this.authorMid = authorMid;
    }

    public Long getViewAt() {
        return viewAt;
    }

    public void setViewAt(Long viewAt) {
        this.viewAt = viewAt;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getNewDesc() {
        return newDesc;
    }

    public void setNewDesc(String newDesc) {
        this.newDesc = newDesc;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    public Integer getIsFav() {
        return isFav;
    }

    public void setIsFav(Integer isFav) {
        this.isFav = isFav;
    }

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(Integer liveStatus) {
        this.liveStatus = liveStatus;
    }
}

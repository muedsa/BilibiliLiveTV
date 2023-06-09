package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

public class VideoData {

    @JSONField(name = "bvid")
    private String bvid;

    @JSONField(name = "aid")
    private Long aid;

    @JSONField(name = "videos")
    private Integer videos;

    @JSONField(name = "tid")
    private Integer tid;

    @JSONField(name = "tname")
    private String tname;

    @JSONField(name = "copyright")
    private Integer copyright;

    @JSONField(name = "pic")
    private String pic;

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "pubdate")
    private Long pubDate;

    @JSONField(name = "ctime")
    private Long ctime;

    @JSONField(name = "desc")
    private String desc;

    @JSONField(name = "desc_v2")
    private List<VideoDataDesc> descV2;

    @JSONField(name = "state")
    private Integer state;

    @JSONField(name = "duration")
    private Long duration;

    @JSONField(name = "mission_id")
    private Long missionId;

    // rights

    @JSONField(name = "owner")
    private VideoOwner owner;

    //stat

    @JSONField(name = "dynamic")
    private String dynamic;

    @JSONField(name = "cid")
    private Long cid;

    @JSONField(name = "dimension")
    private VideoDimension dimension;

    //premiere


    @JSONField(name = "teenage_mode")
    private Integer teenageMode;

    @JSONField(name = "is_chargeable_season")
    private Boolean isChargeableSeason;

    @JSONField(name = "is_story")
    private Boolean isStory;

    @JSONField(name = "no_cache")
    private Boolean noCache;

    @JSONField(name = "pages")
    private List<VideoPage> pages;

    @JSONField(name = "subtitle")
    private VideoSubtitleInfo subtitle;

    @JSONField(name = "ugc_season")
    private Season ugcSeason;

    @JSONField(name = "is_season_display")
    private Boolean isSeasonDisplay;

    //user_garb

    //honor_reply


    @JSONField(name = "like_icon")
    private String likeIcon;

    //embedPlayer


    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Integer getVideos() {
        return videos;
    }

    public void setVideos(Integer videos) {
        this.videos = videos;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Integer getCopyright() {
        return copyright;
    }

    public void setCopyright(Integer copyright) {
        this.copyright = copyright;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPubDate() {
        return pubDate;
    }

    public void setPubDate(Long pubDate) {
        this.pubDate = pubDate;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<VideoDataDesc> getDescV2() {
        return descV2;
    }

    public void setDescV2(List<VideoDataDesc> descV2) {
        this.descV2 = descV2;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public VideoOwner getOwner() {
        return owner;
    }

    public void setOwner(VideoOwner owner) {
        this.owner = owner;
    }

    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public VideoDimension getDimension() {
        return dimension;
    }

    public void setDimension(VideoDimension dimension) {
        this.dimension = dimension;
    }

    public Integer getTeenageMode() {
        return teenageMode;
    }

    public void setTeenageMode(Integer teenageMode) {
        this.teenageMode = teenageMode;
    }

    public Boolean getChargeableSeason() {
        return isChargeableSeason;
    }

    public void setChargeableSeason(Boolean chargeableSeason) {
        isChargeableSeason = chargeableSeason;
    }

    public Boolean getStory() {
        return isStory;
    }

    public void setStory(Boolean story) {
        isStory = story;
    }

    public Boolean getNoCache() {
        return noCache;
    }

    public void setNoCache(Boolean noCache) {
        this.noCache = noCache;
    }

    public List<VideoPage> getPages() {
        return pages;
    }

    public void setPages(List<VideoPage> pages) {
        this.pages = pages;
    }

    public VideoSubtitleInfo getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(VideoSubtitleInfo subtitle) {
        this.subtitle = subtitle;
    }

    public Season getUgcSeason() {
        return ugcSeason;
    }

    public void setUgcSeason(Season ugcSeason) {
        this.ugcSeason = ugcSeason;
    }

    public Boolean getSeasonDisplay() {
        return isSeasonDisplay;
    }

    public void setSeasonDisplay(Boolean seasonDisplay) {
        isSeasonDisplay = seasonDisplay;
    }

    public String getLikeIcon() {
        return likeIcon;
    }

    public void setLikeIcon(String likeIcon) {
        this.likeIcon = likeIcon;
    }
}

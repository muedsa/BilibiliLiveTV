package com.muedsa.bilibililiveapiclient.model.dynamic;

import com.alibaba.fastjson2.annotation.JSONField;
import com.muedsa.bilibililiveapiclient.model.video.VideoDimension;
import com.muedsa.bilibililiveapiclient.model.video.VideoOwner;

public class VideoDynamicCard {

    @JSONField(name = "aid")
    private Long aid;

    @JSONField(name = "cid")
    private Long cid;

    @JSONField(name = "ctime")
    private Long ctime;

    @JSONField(name = "desc")
    private String desc;

    @JSONField(name = "dimension")
    private VideoDimension dimension;

    @JSONField(name = "duration")
    private Long duration;

    @JSONField(name = "first_frame")
    private String firstFrame;

    @JSONField(name = "jump_url")
    private String jumpUrl;

    @JSONField(name = "owner")
    private VideoOwner owner;

    @JSONField(name = "pic")
    private String pic;

    @JSONField(name = "pub_location")
    private String pubLocation;

    @JSONField(name = "pubdate")
    private Long pubDate;

    @JSONField(name = "state")
    private Integer state;

    @JSONField(name = "tid")
    private Integer tid;

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "tname")
    private String tname;

    @JSONField(name = "videos")
    private Integer videos;

    @JSONField(name = "short_link_v2")
    private String shortLinkV2;

    private String bvid;

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
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

    public VideoDimension getDimension() {
        return dimension;
    }

    public void setDimension(VideoDimension dimension) {
        this.dimension = dimension;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getFirstFrame() {
        return firstFrame;
    }

    public void setFirstFrame(String firstFrame) {
        this.firstFrame = firstFrame;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public VideoOwner getOwner() {
        return owner;
    }

    public void setOwner(VideoOwner owner) {
        this.owner = owner;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPubLocation() {
        return pubLocation;
    }

    public void setPubLocation(String pubLocation) {
        this.pubLocation = pubLocation;
    }

    public Long getPubDate() {
        return pubDate;
    }

    public void setPubDate(Long pubDate) {
        this.pubDate = pubDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Integer getVideos() {
        return videos;
    }

    public void setVideos(Integer videos) {
        this.videos = videos;
    }

    public String getShortLinkV2() {
        return shortLinkV2;
    }

    public void setShortLinkV2(String shortLinkV2) {
        this.shortLinkV2 = shortLinkV2;
    }

    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }
}

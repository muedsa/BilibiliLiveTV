package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

public class VideoInfo {

    @JSONField(name = "aid")
    private Long aid;

    @JSONField(name = "bvid")
    private String bvid;

    @JSONField(name = "p")
    private Integer p;

    @JSONField(name = "episode")
    private String episode;

    @JSONField(name = "videoData")
    private VideoData videoData;

    // todo more field


    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public VideoData getVideoData() {
        return videoData;
    }

    public void setVideoData(VideoData videoData) {
        this.videoData = videoData;
    }
}

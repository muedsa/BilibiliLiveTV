package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

public class VideoInfo {

    @JSONField(name = "aid")
    private Long aId;

    @JSONField(name = "bvid")
    private String bvId;

    @JSONField(name = "p")
    private Integer p;

    @JSONField(name = "episode")
    private String episode;

    @JSONField(name = "VideoData")
    private VideoData videoData;

    // todo more field


    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
    }

    public String getBvId() {
        return bvId;
    }

    public void setBvId(String bvId) {
        this.bvId = bvId;
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

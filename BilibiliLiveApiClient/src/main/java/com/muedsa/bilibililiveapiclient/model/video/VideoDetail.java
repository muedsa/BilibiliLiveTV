package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

public class VideoDetail {

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "videoInfo")
    private VideoInfo videoInfo;

    @JSONField(name = "playInfo")
    private PlayInfo playInfo;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
    }

    public PlayInfo getPlayInfo() {
        return playInfo;
    }

    public void setPlayInfo(PlayInfo playInfo) {
        this.playInfo = playInfo;
    }
}

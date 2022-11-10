package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;
import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;

public class VideoDetail {

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "videoInfo")
    private VideoInfo videoInfo;

    @JSONField(name = "playInfo")
    private BilibiliResponse<PlayInfo> playInfoResponse;

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

    public BilibiliResponse<PlayInfo> getPlayInfoResponse() {
        return playInfoResponse;
    }

    public void setPlayInfoResponse(BilibiliResponse<PlayInfo> playInfoResponse) {
        this.playInfoResponse = playInfoResponse;
    }
}

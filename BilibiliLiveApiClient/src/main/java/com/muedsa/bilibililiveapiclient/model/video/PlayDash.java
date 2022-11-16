package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

public class PlayDash {

    @JSONField(name = "duration")
    private Long duration;

    @JSONField(name = "minBufferTime", alternateNames = "min_buffer_time")
    private Long minBufferTime;

    @JSONField(name = "video")
    private List<PlayDashInfo> video;

    @JSONField(name = "audio")
    private List<PlayDashInfo> audio;

    //dolby

    //flac

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getMinBufferTime() {
        return minBufferTime;
    }

    public void setMinBufferTime(Long minBufferTime) {
        this.minBufferTime = minBufferTime;
    }

    public List<PlayDashInfo> getVideo() {
        return video;
    }

    public void setVideo(List<PlayDashInfo> video) {
        this.video = video;
    }

    public List<PlayDashInfo> getAudio() {
        return audio;
    }

    public void setAudio(List<PlayDashInfo> audio) {
        this.audio = audio;
    }
}

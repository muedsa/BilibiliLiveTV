package com.muedsa.bilibililiveapiclient.model.dynamic;

import com.alibaba.fastjson2.annotation.JSONField;

public class MajorArchiveState {

    @JSONField(name = "danmaku")
    private String danmaku;

    @JSONField(name = "play")
    private Integer play;

    public String getDanmaku() {
        return danmaku;
    }

    public void setDanmaku(String danmaku) {
        this.danmaku = danmaku;
    }

    public Integer getPlay() {
        return play;
    }

    public void setPlay(Integer play) {
        this.play = play;
    }
}

package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

public class VideoSubtitleInfo {

    @JSONField(name = "allow_submit")
    private Boolean allowSubmit;

    @JSONField(name = "list")
    private List<VideoSubtitle> list;

    public Boolean getAllowSubmit() {
        return allowSubmit;
    }

    public void setAllowSubmit(Boolean allowSubmit) {
        this.allowSubmit = allowSubmit;
    }

    public List<VideoSubtitle> getList() {
        return list;
    }

    public void setList(List<VideoSubtitle> list) {
        this.list = list;
    }
}

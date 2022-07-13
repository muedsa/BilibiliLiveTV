package com.muedsa.bilibililiveapiclient.model;

import com.alibaba.fastjson2.annotation.JSONField;

public class AnchorInfo {

    @JSONField(name = "base_info")
    private AnchorBaseInfo baseInfo;

    @JSONField(name = "base_info")
    private AnchorLiveInfo LiveInfo;

    @JSONField(name = "medal_info")
    private AnchorMedalInfo medalInfo;

    public AnchorBaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(AnchorBaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public AnchorLiveInfo getLiveInfo() {
        return LiveInfo;
    }

    public void setLiveInfo(AnchorLiveInfo liveInfo) {
        LiveInfo = liveInfo;
    }

    public AnchorMedalInfo getMedalInfo() {
        return medalInfo;
    }

    public void setMedalInfo(AnchorMedalInfo medalInfo) {
        this.medalInfo = medalInfo;
    }
}

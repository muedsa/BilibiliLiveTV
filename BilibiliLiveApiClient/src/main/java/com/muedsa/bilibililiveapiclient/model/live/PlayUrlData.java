package com.muedsa.bilibililiveapiclient.model.live;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

public class PlayUrlData {

    @JSONField(name = "current_qn")
    private Long currentQn;

    @JSONField(name = "quality_description")
    private List<QualityDescription> qualityDescription;

    private List<Durl> durl;

    public Long getCurrentQn() {
        return currentQn;
    }

    public void setCurrentQn(Long currentQn) {
        this.currentQn = currentQn;
    }

    public List<QualityDescription> getQualityDescription() {
        return qualityDescription;
    }

    public void setQualityDescription(List<QualityDescription> qualityDescription) {
        this.qualityDescription = qualityDescription;
    }

    public List<Durl> getDurl() {
        return durl;
    }

    public void setDurl(List<Durl> durl) {
        this.durl = durl;
    }
}

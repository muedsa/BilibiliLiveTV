package com.muedsa.bilibililiveapiclient.model.history;

import com.alibaba.fastjson2.annotation.JSONField;

public class HistoryCursor {
    @JSONField(name = "max")
    private Long max;

    @JSONField(name = "view_at")
    private Long viewAt;

    @JSONField(name = "business")
    private String business;

    @JSONField(name = "ps")
    private Integer ps;

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public Long getViewAt() {
        return viewAt;
    }

    public void setViewAt(Long viewAt) {
        this.viewAt = viewAt;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public Integer getPs() {
        return ps;
    }

    public void setPs(Integer ps) {
        this.ps = ps;
    }
}

package com.muedsa.bilibililiveapiclient.model;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

public class DanmakuInfo {

    @JSONField(name = "group")
    private String group;

    @JSONField(name = "business_id")
    private Integer businessId;

    @JSONField(name = "refresh_row_factor")
    private Double refreshRowFactor;

    @JSONField(name = "refresh_rate")
    private Integer refreshRate;

    @JSONField(name = "max_delay")
    private Integer maxDelay;

    @JSONField(name = "token")
    private String token;

    @JSONField(name = "host_list")
    private List<DanmakuHostInfo> hostList;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Double getRefreshRowFactor() {
        return refreshRowFactor;
    }

    public void setRefreshRowFactor(Double refreshRowFactor) {
        this.refreshRowFactor = refreshRowFactor;
    }

    public Integer getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(Integer refreshRate) {
        this.refreshRate = refreshRate;
    }

    public Integer getMaxDelay() {
        return maxDelay;
    }

    public void setMaxDelay(Integer maxDelay) {
        this.maxDelay = maxDelay;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<DanmakuHostInfo> getHostList() {
        return hostList;
    }

    public void setHostList(List<DanmakuHostInfo> hostList) {
        this.hostList = hostList;
    }
}


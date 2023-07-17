package com.muedsa.bilibililiveapiclient.model.dynamic.svr;

import com.alibaba.fastjson2.annotation.JSONField;

public class DynamicDesc {
    @JSONField(name = "dynamic_id")
    private Long dynamicId;

    @JSONField(name = "type")
    private Integer type;

    @JSONField(name = "uid")
    private Long uid;

    @JSONField(name = "rid")
    private Long rid;

    @JSONField(name = "timestamp")
    private Long timestamp;

    @JSONField(name = "bvid")
    private String bvid;

    public Long getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(Long dynamicId) {
        this.dynamicId = dynamicId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }
}

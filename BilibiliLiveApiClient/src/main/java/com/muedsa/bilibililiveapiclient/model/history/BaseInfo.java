package com.muedsa.bilibililiveapiclient.model.history;

import com.alibaba.fastjson2.annotation.JSONField;

public class BaseInfo {
    @JSONField(name = "oid")
    private Long oid;

    @JSONField(name = "epid")
    private Long epid;

    @JSONField(name = "bvid")
    private String bvid;

    @JSONField(name = "page")
    private Integer page;

    @JSONField(name = "cid")
    private Long cid;

    @JSONField(name = "part")
    private String part;

    @JSONField(name = "business")
    private String business;

    @JSONField(name = "dt")
    private Integer dt;

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Long getEpid() {
        return epid;
    }

    public void setEpid(Long epid) {
        this.epid = epid;
    }

    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }
}

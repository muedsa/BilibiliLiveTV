package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

public class PlayDashInfo {

    @JSONField(name = "id")
    private Integer id;

    @JSONField(name = "baseUrl", alternateNames = "base_url")
    private String baseUrl;

    @JSONField(name = "backupUrl", alternateNames = "backup_url")
    private String backupUrl;

    @JSONField(name = "bandwidth")
    private String bandwidth;

    @JSONField(name = "mimeType")
    private String mimeType;

    @JSONField(name = "codecs")
    private String codecs;

    @JSONField(name = "width")
    private Integer width;

    @JSONField(name = "height")
    private Integer height;

    @JSONField(name = "frameRate", alternateNames = "frame_rate")
    private String frameRate;

    @JSONField(name = "sar")
    private String sar;

    @JSONField(name = "startWithSap", alternateNames = "start_with_sap")
    private Integer startWithSap;

    @JSONField(name = "segmentBase", alternateNames = {"SegmentBase", "segment_base"})
    private SegmentBase segmentBase;

    @JSONField(name = "codecid")
    private Integer codeCid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBackupUrl() {
        return backupUrl;
    }

    public void setBackupUrl(String backupUrl) {
        this.backupUrl = backupUrl;
    }

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getCodecs() {
        return codecs;
    }

    public void setCodecs(String codecs) {
        this.codecs = codecs;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(String frameRate) {
        this.frameRate = frameRate;
    }

    public String getSar() {
        return sar;
    }

    public void setSar(String sar) {
        this.sar = sar;
    }

    public Integer getStartWithSap() {
        return startWithSap;
    }

    public void setStartWithSap(Integer startWithSap) {
        this.startWithSap = startWithSap;
    }

    public SegmentBase getSegmentBase() {
        return segmentBase;
    }

    public void setSegmentBase(SegmentBase segmentBase) {
        this.segmentBase = segmentBase;
    }

    public Integer getCodeCid() {
        return codeCid;
    }

    public void setCodeCid(Integer codeCid) {
        this.codeCid = codeCid;
    }
}

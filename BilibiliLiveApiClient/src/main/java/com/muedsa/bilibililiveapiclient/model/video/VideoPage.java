package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

public class VideoPage {

    @JSONField(name = "cid")
    private Long cid;

    @JSONField(name = "page")
    private Integer page;

    @JSONField(name = "from")
    private String from;

    @JSONField(name = "part")
    private String part;

    @JSONField(name = "duration")
    private Long duration;

    @JSONField(name = "vid")
    private String vid;

    @JSONField(name = "weblink")
    private String weblink;

    @JSONField(name = "dimension")
    private VideoDimension dimension;

    @JSONField(name = "first_frame")
    private String firstFrame;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }

    public VideoDimension getDimension() {
        return dimension;
    }

    public void setDimension(VideoDimension dimension) {
        this.dimension = dimension;
    }

    public String getFirstFrame() {
        return firstFrame;
    }

    public void setFirstFrame(String firstFrame) {
        this.firstFrame = firstFrame;
    }
}

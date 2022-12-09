package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

public class SectionEpisode {
    @JSONField(name = "season_id")
    private Long seasonId;

    @JSONField(name = "section_id")
    private Long sectionId;

    @JSONField(name = "id")
    private Long id;

    @JSONField(name = "aid")
    private Long aid;

    @JSONField(name = "cid")
    private Long cid;

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "attribute")
    private Integer attribute;

    @JSONField(name = "arc")
    private Archive arc;

    @JSONField(name = "page")
    private VideoPage page;

    @JSONField(name = "bvid")
    private String bvId;

    public Long getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Long seasonId) {
        this.seasonId = seasonId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public Archive getArc() {
        return arc;
    }

    public void setArc(Archive arc) {
        this.arc = arc;
    }

    public VideoPage getPage() {
        return page;
    }

    public void setPage(VideoPage page) {
        this.page = page;
    }

    public String getBvId() {
        return bvId;
    }

    public void setBvId(String bvId) {
        this.bvId = bvId;
    }
}

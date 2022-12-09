package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

public class Archive {
    @JSONField(name = "aid")
    private Long aid;

    @JSONField(name = "videos")
    private Integer videos;

    @JSONField(name = "type_id")
    private Integer typeId;

    @JSONField(name = "type_name")
    private String typeName;

    @JSONField(name = "copyright")
    private Integer copyright;

    @JSONField(name = "pic")
    private String pic;

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "pubdate")
    private Long pubDate;

    @JSONField(name = "ctime")
    private Long cTime;

    @JSONField(name = "desc")
    private String desc;

    @JSONField(name = "state")
    private Integer state;

    @JSONField(name = "duration")
    private Long duration;

    @JSONField(name = "stat")
    private ArchiveStat stat;

    @JSONField(name = "dynamic")
    private String dynamic;

    @JSONField(name = "dimension")
    private VideoDimension dimension;

//    @JSONField(name = "desc_v2")
//    private List<VideoDataDesc> descV2;

    @JSONField(name = "is_chargeable_season")
    private Boolean isChargeableSeason;

    @JSONField(name = "is_blooper")
    private Boolean isBlooper;

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Integer getVideos() {
        return videos;
    }

    public void setVideos(Integer videos) {
        this.videos = videos;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getCopyright() {
        return copyright;
    }

    public void setCopyright(Integer copyright) {
        this.copyright = copyright;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPubDate() {
        return pubDate;
    }

    public void setPubDate(Long pubDate) {
        this.pubDate = pubDate;
    }

    public Long getcTime() {
        return cTime;
    }

    public void setcTime(Long cTime) {
        this.cTime = cTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public ArchiveStat getStat() {
        return stat;
    }

    public void setStat(ArchiveStat stat) {
        this.stat = stat;
    }

    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }

    public VideoDimension getDimension() {
        return dimension;
    }

    public void setDimension(VideoDimension dimension) {
        this.dimension = dimension;
    }

    public Boolean getChargeableSeason() {
        return isChargeableSeason;
    }

    public void setChargeableSeason(Boolean chargeableSeason) {
        isChargeableSeason = chargeableSeason;
    }

    public Boolean getBlooper() {
        return isBlooper;
    }

    public void setBlooper(Boolean blooper) {
        isBlooper = blooper;
    }
}

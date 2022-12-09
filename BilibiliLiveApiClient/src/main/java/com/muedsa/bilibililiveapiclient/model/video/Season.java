package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

public class Season {

    @JSONField(name = "id")
    private Long id;

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "cover")
    private String cover;

    @JSONField(name = "mid")
    private Long mid;

    @JSONField(name = "intro")
    private String intro;

    @JSONField(name = "sign_state")
    private Integer signState;

    @JSONField(name = "attribute")
    private Integer attribute;

    @JSONField(name = "sections")
    private List<SeasonSection> sections;

    @JSONField(name = "stat")
    private SeasonStat stat;

    @JSONField(name = "ep_count")
    private Integer epCount;

    @JSONField(name = "season_type")
    private Integer seasonType;

    @JSONField(name = "is_pay_season")
    private Boolean isPaySeason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getSignState() {
        return signState;
    }

    public void setSignState(Integer signState) {
        this.signState = signState;
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public List<SeasonSection> getSections() {
        return sections;
    }

    public void setSections(List<SeasonSection> sections) {
        this.sections = sections;
    }

    public SeasonStat getStat() {
        return stat;
    }

    public void setStat(SeasonStat stat) {
        this.stat = stat;
    }

    public Integer getEpCount() {
        return epCount;
    }

    public void setEpCount(Integer epCount) {
        this.epCount = epCount;
    }

    public Integer getSeasonType() {
        return seasonType;
    }

    public void setSeasonType(Integer seasonType) {
        this.seasonType = seasonType;
    }

    public Boolean getPaySeason() {
        return isPaySeason;
    }

    public void setPaySeason(Boolean paySeason) {
        isPaySeason = paySeason;
    }
}

package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

public class SeasonSection {

    @JSONField(name = "season_id")
    private Long seasonId;

    @JSONField(name = "id")
    private Long id;

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "type")
    private Integer type;

    @JSONField(name = "episodes")
    private List<SectionEpisode> episodes;

    @JSONField(name = "isActive")
    private Boolean isActive;

    @JSONField(name = "height")
    private Integer height;

    public Long getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Long seasonId) {
        this.seasonId = seasonId;
    }

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<SectionEpisode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<SectionEpisode> episodes) {
        this.episodes = episodes;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}

package com.muedsa.bilibililiveapiclient.model;

import com.alibaba.fastjson.annotation.JSONField;

public class AnchorLiveInfo {

    @JSONField(name = "level")
    private Integer level;

    @JSONField(name = "level_color")
    private Long levelColor;

    @JSONField(name = "score")
    private Long score;

    @JSONField(name = "upgrade_score")
    private Long upgradeScore;

    @JSONField(name = "rank")
    private String rank;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getLevelColor() {
        return levelColor;
    }

    public void setLevelColor(Long levelColor) {
        this.levelColor = levelColor;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getUpgradeScore() {
        return upgradeScore;
    }

    public void setUpgradeScore(Long upgradeScore) {
        this.upgradeScore = upgradeScore;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}

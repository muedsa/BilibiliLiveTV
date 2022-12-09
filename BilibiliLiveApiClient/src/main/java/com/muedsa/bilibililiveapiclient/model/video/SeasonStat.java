package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

public class SeasonStat {

    @JSONField(name = "id")
    private Long id;

    @JSONField(name = "view")
    private Integer view;

    @JSONField(name = "danmaku")
    private Integer danmaku;

    @JSONField(name = "reply")
    private Integer reply;

    @JSONField(name = "fav")
    private Integer fav;

    @JSONField(name = "coin")
    private Integer coin;

    @JSONField(name = "share")
    private Integer share;

    @JSONField(name = "now_rank")
    private Integer nowRank;

    @JSONField(name = "his_rank")
    private Integer hisRank;

    @JSONField(name = "like")
    private Integer like;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Integer getDanmaku() {
        return danmaku;
    }

    public void setDanmaku(Integer danmaku) {
        this.danmaku = danmaku;
    }

    public Integer getReply() {
        return reply;
    }

    public void setReply(Integer reply) {
        this.reply = reply;
    }

    public Integer getFav() {
        return fav;
    }

    public void setFav(Integer fav) {
        this.fav = fav;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public Integer getNowRank() {
        return nowRank;
    }

    public void setNowRank(Integer nowRank) {
        this.nowRank = nowRank;
    }

    public Integer getHisRank() {
        return hisRank;
    }

    public void setHisRank(Integer hisRank) {
        this.hisRank = hisRank;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }
}

package com.muedsa.bilibililiveapiclient.model.search;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

public class SearchVideoInfo {

    @JSONField(name = "type")
    private String type;

    @JSONField(name = "id")
    private Long id;

    @JSONField(name = "author")
    private String author;

    @JSONField(name = "mid")
    private Long mid;

    @JSONField(name = "typeid")
    private Integer typeId;

    @JSONField(name = "typename")
    private String typeName;

    @JSONField(name = "arcurl")
    private String arcUrl;

    @JSONField(name = "aid")
    private Long aid;

    @JSONField(name = "bvid")
    private String bvId;

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "description")
    private String description;

    @JSONField(name = "arcrank")
    private String arcRank;

    @JSONField(name = "pic")
    private String pic;

    @JSONField(name = "play")
    private Integer play;

    @JSONField(name = "video_review")
    private Integer videoReview;

    @JSONField(name = "favorites")
    private Integer favorites;

    @JSONField(name = "tag")
    private String tag;

    @JSONField(name = "pubdate")
    private Long pubDate;

    @JSONField(name = "senddate")
    private Long sendDate;

    @JSONField(name = "duration")
    private String duration;

    @JSONField(name = "badgepay")
    private Boolean badgePay;

    @JSONField(name = "hit_columns")
    private List<String> hitColumns;

    @JSONField(name = "view_type")
    private String viewType;

    @JSONField(name = "is_pay")
    private Integer isPay;

    @JSONField(name = "is_union_video")
    private Integer isUnionVideo;

    @JSONField(name = "rec_tags")
    private String recTags;

    @JSONField(name = "new_rec_tags")
    private List<String> newRecTags;

    @JSONField(name = "rank_score")
    private Long rankScore;

    @JSONField(name = "like")
    private Integer like;

    @JSONField(name = "upic")
    private String upic;

    @JSONField(name = "corner")
    private String corner;

    @JSONField(name = "cover")
    private String cover;

    @JSONField(name = "desc")
    private String desc;

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "rec_reason")
    private String recReason;

    @JSONField(name = "danmaku")
    private Integer danmaku;

//    @JSONField(name = "biz_data")
//    private String bizData;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
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

    public String getArcUrl() {
        return arcUrl;
    }

    public void setArcUrl(String arcUrl) {
        this.arcUrl = arcUrl;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getBvId() {
        return bvId;
    }

    public void setBvId(String bvId) {
        this.bvId = bvId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArcRank() {
        return arcRank;
    }

    public void setArcRank(String arcRank) {
        this.arcRank = arcRank;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getPlay() {
        return play;
    }

    public void setPlay(Integer play) {
        this.play = play;
    }

    public Integer getVideoReview() {
        return videoReview;
    }

    public void setVideoReview(Integer videoReview) {
        this.videoReview = videoReview;
    }

    public Integer getFavorites() {
        return favorites;
    }

    public void setFavorites(Integer favorites) {
        this.favorites = favorites;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getPubDate() {
        return pubDate;
    }

    public void setPubDate(Long pubDate) {
        this.pubDate = pubDate;
    }

    public Long getSendDate() {
        return sendDate;
    }

    public void setSendDate(Long sendDate) {
        this.sendDate = sendDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Boolean getBadgePay() {
        return badgePay;
    }

    public void setBadgePay(Boolean badgePay) {
        this.badgePay = badgePay;
    }

    public List<String> getHitColumns() {
        return hitColumns;
    }

    public void setHitColumns(List<String> hitColumns) {
        this.hitColumns = hitColumns;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public Integer getIsUnionVideo() {
        return isUnionVideo;
    }

    public void setIsUnionVideo(Integer isUnionVideo) {
        this.isUnionVideo = isUnionVideo;
    }

    public String getRecTags() {
        return recTags;
    }

    public void setRecTags(String recTags) {
        this.recTags = recTags;
    }

    public List<String> getNewRecTags() {
        return newRecTags;
    }

    public void setNewRecTags(List<String> newRecTags) {
        this.newRecTags = newRecTags;
    }

    public Long getRankScore() {
        return rankScore;
    }

    public void setRankScore(Long rankScore) {
        this.rankScore = rankScore;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public String getUpic() {
        return upic;
    }

    public void setUpic(String upic) {
        this.upic = upic;
    }

    public String getCorner() {
        return corner;
    }

    public void setCorner(String corner) {
        this.corner = corner;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRecReason() {
        return recReason;
    }

    public void setRecReason(String recReason) {
        this.recReason = recReason;
    }

    public Integer getDanmaku() {
        return danmaku;
    }

    public void setDanmaku(Integer danmaku) {
        this.danmaku = danmaku;
    }
}

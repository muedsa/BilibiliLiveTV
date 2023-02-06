package com.muedsa.bilibililivetv.model;

import com.muedsa.bilibililiveapiclient.model.video.VideoSubtitle;

import java.io.Serializable;
import java.util.List;

public class VideoPlayInfo implements Serializable {

    private String bv;
    private Long cid;

    private String title;
    private String subTitle;

    private Integer quality;
    private String qualityDescription;
    private String codecs;

    private String videoUrl;
    private String audioUrl;

    private String referer;

    private List<VideoSubtitle> subtitleList;

    private Integer danmakuSegmentSize;

    public String getBv() {
        return bv;
    }

    public void setBv(String bv) {
        this.bv = bv;
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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public String getQualityDescription() {
        return qualityDescription;
    }

    public void setQualityDescription(String qualityDescription) {
        this.qualityDescription = qualityDescription;
    }

    public String getCodecs() {
        return codecs;
    }

    public void setCodecs(String codecs) {
        this.codecs = codecs;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public List<VideoSubtitle> getSubtitleList() {
        return subtitleList;
    }

    public void setSubtitleList(List<VideoSubtitle> subtitleList) {
        this.subtitleList = subtitleList;
    }

    public Integer getDanmakuSegmentSize() {
        return danmakuSegmentSize;
    }

    public void setDanmakuSegmentSize(Integer danmakuSegmentSize) {
        this.danmakuSegmentSize = danmakuSegmentSize;
    }
}

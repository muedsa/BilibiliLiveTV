package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

public class PlayInfo {

    @JSONField(name = "from")
    private String from;

    @JSONField(name = "result")
    private String result;

    @JSONField(name = "message")
    private String message;

    @JSONField(name = "quality")
    private Integer quality;

    @JSONField(name = "format")
    private String format;

    @JSONField(name = "timelength")
    private Long timeLength;

    @JSONField(name = "accept_format")
    private String acceptFormat;

    @JSONField(name = "acceptDescription")
    private List<String> acceptDescription;

    @JSONField(name = "accept_quality")
    private List<Integer> acceptQuality;

    @JSONField(name = "video_codecid")
    private Integer videoCodeCid;

    @JSONField(name = "seek_param")
    private String seekParam;

    @JSONField(name = "seek_type")
    private String seekType;

    @JSONField(name = "dash")
    private PlayDash dash;

    @JSONField(name = "support_formats")
    private List<SupportFormat> supportFormats;

    //high_format

    //volume

    @JSONField(name = "last_play_time")
    private Long lastPlayTime;

    @JSONField(name = "last_play_cid")
    private Long lastPlayCid;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Long getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(Long timeLength) {
        this.timeLength = timeLength;
    }

    public String getAcceptFormat() {
        return acceptFormat;
    }

    public void setAcceptFormat(String acceptFormat) {
        this.acceptFormat = acceptFormat;
    }

    public List<String> getAcceptDescription() {
        return acceptDescription;
    }

    public void setAcceptDescription(List<String> acceptDescription) {
        this.acceptDescription = acceptDescription;
    }

    public List<Integer> getAcceptQuality() {
        return acceptQuality;
    }

    public void setAcceptQuality(List<Integer> acceptQuality) {
        this.acceptQuality = acceptQuality;
    }

    public Integer getVideoCodeCid() {
        return videoCodeCid;
    }

    public void setVideoCodeCid(Integer videoCodeCid) {
        this.videoCodeCid = videoCodeCid;
    }

    public String getSeekParam() {
        return seekParam;
    }

    public void setSeekParam(String seekParam) {
        this.seekParam = seekParam;
    }

    public String getSeekType() {
        return seekType;
    }

    public void setSeekType(String seekType) {
        this.seekType = seekType;
    }

    public PlayDash getDash() {
        return dash;
    }

    public void setDash(PlayDash dash) {
        this.dash = dash;
    }

    public List<SupportFormat> getSupportFormats() {
        return supportFormats;
    }

    public void setSupportFormats(List<SupportFormat> supportFormats) {
        this.supportFormats = supportFormats;
    }

    public Long getLastPlayTime() {
        return lastPlayTime;
    }

    public void setLastPlayTime(Long lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    public Long getLastPlayCid() {
        return lastPlayCid;
    }

    public void setLastPlayCid(Long lastPlayCid) {
        this.lastPlayCid = lastPlayCid;
    }
}

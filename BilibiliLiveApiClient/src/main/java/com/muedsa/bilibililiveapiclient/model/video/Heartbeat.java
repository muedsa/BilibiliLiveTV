package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

public class Heartbeat {

    public static final String FIELD_BVID = "bvid";

    public static final String FIELD_CID = "cid";

    public static final String FIELD_MID = "mid";

    public static final String FIELD_TYPE = "type";

    public static final String FIELD_SUB_TYPE = "sub_type";

    public static final String FIELD_DT = "dt";

    public static final String FIELD_QUALITY = "quality";

    public static final String FIELD_VIDEO_DURATION = "video_duration";

    public static final String FIELD_PLAYED_TIME = "played_time";

    public static final String FIELD_REALTIME = "realtime";

    public static final String FIELD_START_TS = "start_ts";

    public static final String FIELD_LAST_PLAY_PROGRESS_TIME = "last_play_progress_time";

    public static final String FIELD_MAX_PLAY_PROGRESS_TIME = "max_play_progress_time";

    public static final String FIELD_PLAY_TYPE = "play_type";

    public static final String FIELD_CSRF = "csrf";

    @JSONField(name = "bvid")
    private String bvid;

    @JSONField(name = "cid")
    private Long cid;

    @JSONField(name = "type")
    private Integer type;

    @JSONField(name = "sub_type")
    private Integer subType;

    @JSONField(name = "quality")
    private Integer quality;

    @JSONField(name = "video_duration")
    private Long videoDuration;

    @JSONField(name = "played_time")
    private Long playedTime;

    @JSONField(name = "realtime")
    private Long realtime;

    @JSONField(name = "start_ts")
    private Long startTs;

    @JSONField(name = "last_play_progress_time")
    private Long lastPlayProgressTime;

    @JSONField(name = "max_play_progress_time")
    private Long maxPlayProgressTime;

    @JSONField(name = "play_type")
    private Integer playType;

    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Long getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(Long videoDuration) {
        this.videoDuration = videoDuration;
    }

    public Long getPlayedTime() {
        return playedTime;
    }

    public void setPlayedTime(Long playedTime) {
        this.playedTime = playedTime;
    }

    public Long getRealtime() {
        return realtime;
    }

    public void setRealtime(Long realtime) {
        this.realtime = realtime;
    }

    public Long getStartTs() {
        return startTs;
    }

    public void setStartTs(Long startTs) {
        this.startTs = startTs;
    }

    public Long getLastPlayProgressTime() {
        return lastPlayProgressTime;
    }

    public void setLastPlayProgressTime(Long lastPlayProgressTime) {
        this.lastPlayProgressTime = lastPlayProgressTime;
    }

    public Long getMaxPlayProgressTime() {
        return maxPlayProgressTime;
    }

    public void setMaxPlayProgressTime(Long maxPlayProgressTime) {
        this.maxPlayProgressTime = maxPlayProgressTime;
    }

    public Integer getPlayType() {
        return playType;
    }

    public void setPlayType(Integer playType) {
        this.playType = playType;
    }
}

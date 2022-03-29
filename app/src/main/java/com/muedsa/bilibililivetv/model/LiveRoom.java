package com.muedsa.bilibililivetv.model;

import android.content.res.Resources;

import com.muedsa.bilibililivetv.R;

import java.io.Serializable;
import java.util.Objects;

public class LiveRoom implements Serializable {
    static final long serialVersionUID = 727566175075960653L;
    private long id;
    private Long shortId;
    private String title;
    private String uname;
    private String description;
    private String coverImageUrl;
    private String systemCoverImageUrl;
    private String backgroundImageUrl;
    private String[] playUrlArr;
    private int liveStatus;
    private int onlineNum;
    private String danmuWsToken;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getShortId() {
        return shortId;
    }

    public void setShortId(Long shortId) {
        this.shortId = shortId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getSystemCoverImageUrl() {
        return systemCoverImageUrl;
    }

    public void setSystemCoverImageUrl(String systemCoverImageUrl) {
        this.systemCoverImageUrl = systemCoverImageUrl;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public String[] getPlayUrlArr() {
        return playUrlArr;
    }

    public void setPlayUrlArr(String[] playUrlArr) {
        this.playUrlArr = playUrlArr;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public int getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(int onlineNum) {
        this.onlineNum = onlineNum;
    }

    public String getLiveStatusDesc(Resources resources){
        String liveStatusDesc = resources.getString(R.string.room_no_live);
        if(liveStatus == 1){
            liveStatusDesc = resources.getString(R.string.room_living);
        }
        return liveStatusDesc;
    }

    public String getDanmuWsToken() {
        return danmuWsToken;
    }

    public void setDanmuWsToken(String danmuWsToken) {
        this.danmuWsToken = danmuWsToken;
    }
}
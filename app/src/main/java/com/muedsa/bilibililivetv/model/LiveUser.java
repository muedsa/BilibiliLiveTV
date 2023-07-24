package com.muedsa.bilibililivetv.model;


import android.content.res.Resources;

import com.muedsa.bilibililivetv.R;

public class LiveUser {

    private long roomId;
    private Long shortId;
    private int liveStatus;
    private String uface;
    private Long uid;
    private String uname;

    public LiveUser() {
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public Long getShortId() {
        return shortId;
    }

    public void setShortId(Long shortId) {
        this.shortId = shortId;
    }

    public String getLiveStatusDesc(Resources resources) {
        String liveStatusDesc = resources.getString(R.string.room_no_live);
        if (liveStatus == 1) {
            liveStatusDesc = resources.getString(R.string.room_living);
        }
        return liveStatusDesc;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getUface() {
        return uface;
    }

    public void setUface(String uface) {
        this.uface = uface;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}

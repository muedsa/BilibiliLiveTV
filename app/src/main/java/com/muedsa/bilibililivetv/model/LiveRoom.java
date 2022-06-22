package com.muedsa.bilibililivetv.model;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import com.muedsa.bilibililivetv.R;

import java.util.regex.Pattern;

public class LiveRoom implements Parcelable {

    public static final Pattern ID_PATTERN =  Pattern.compile("^\\d+$");

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

    protected LiveRoom(Parcel in) {
        id = in.readLong();
        if (in.readByte() == 0) {
            shortId = null;
        } else {
            shortId = in.readLong();
        }
        title = in.readString();
        uname = in.readString();
        description = in.readString();
        coverImageUrl = in.readString();
        systemCoverImageUrl = in.readString();
        backgroundImageUrl = in.readString();
        playUrlArr = in.createStringArray();
        liveStatus = in.readInt();
        onlineNum = in.readInt();
        danmuWsToken = in.readString();
    }

    public static final Creator<LiveRoom> CREATOR = new Creator<LiveRoom>() {
        @Override
        public LiveRoom createFromParcel(Parcel in) {
            return new LiveRoom(in);
        }

        @Override
        public LiveRoom[] newArray(int size) {
            return new LiveRoom[size];
        }
    };

    public LiveRoom() {
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeValue(shortId);
        dest.writeString(title);
        dest.writeString(uname);
        dest.writeString(description);
        dest.writeString(coverImageUrl);
        dest.writeString(systemCoverImageUrl);
        dest.writeString(backgroundImageUrl);
        dest.writeArray(playUrlArr);
        dest.writeInt(liveStatus);
        dest.writeInt(onlineNum);
        dest.writeString(danmuWsToken);
    }
}
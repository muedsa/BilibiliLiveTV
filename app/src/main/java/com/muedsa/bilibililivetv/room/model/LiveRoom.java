package com.muedsa.bilibililivetv.room.model;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.muedsa.bilibililivetv.R;

import java.io.Serializable;
import java.util.regex.Pattern;


@Entity(tableName = "live_room", indices = {@Index(value = "update_at", orders = Index.Order.DESC)})
public class LiveRoom extends BaseModel implements Serializable {

    @Ignore
    public static final Pattern ID_PATTERN = Pattern.compile("^\\d+$");

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "short_id")
    private Long shortId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "uid", defaultValue = "0")
    @NonNull
    private Long uid;

    @ColumnInfo(name = "uname")
    private String uname;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "cover_image_url")
    private String coverImageUrl;

    @ColumnInfo(name = "system_cover_image_url")
    private String systemCoverImageUrl;

    @ColumnInfo(name = "background_image_url")
    private String backgroundImageUrl;

    @Ignore
    private String[] playUrlArr;

    @Ignore
    private int liveStatus;

    @Ignore
    private int onlineNum;

    @Ignore
    private String danmuWssUrl;

    @Ignore
    private String danmuWsToken;

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

    public String getLiveStatusDesc(Resources resources) {
        String liveStatusDesc = resources.getString(R.string.room_no_live);
        if (liveStatus == 1) {
            liveStatusDesc = resources.getString(R.string.room_living);
        }
        return liveStatusDesc;
    }

    public String getDanmuWssUrl() {
        return danmuWssUrl;
    }

    public void setDanmuWssUrl(String danmuWssUrl) {
        this.danmuWssUrl = danmuWssUrl;
    }

    public String getDanmuWsToken() {
        return danmuWsToken;
    }

    public void setDanmuWsToken(String danmuWsToken) {
        this.danmuWsToken = danmuWsToken;
    }
}
package com.muedsa.bilibililiveapiclient.model.search;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

public class SearchResult {

    @JSONField(name = "video")
    private List<SearchVideoInfo> video;

    @JSONField(name = "live_room")
    private List<SearchLiveRoom> liveRoom;

    @JSONField(name = "live_user")
    private List<SearchLiveUser> liveUser;

    public List<SearchVideoInfo> getVideo() {
        return video;
    }

    public void setVideo(List<SearchVideoInfo> video) {
        this.video = video;
    }

    public List<SearchLiveRoom> getLiveRoom() {
        return liveRoom;
    }

    public void setLiveRoom(List<SearchLiveRoom> liveRoom) {
        this.liveRoom = liveRoom;
    }

    public List<SearchLiveUser> getLiveUser() {
        return liveUser;
    }

    public void setLiveUser(List<SearchLiveUser> searchLiveUser) {
        this.liveUser = searchLiveUser;
    }
}

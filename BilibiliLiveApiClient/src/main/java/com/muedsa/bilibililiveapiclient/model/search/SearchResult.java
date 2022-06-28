package com.muedsa.bilibililiveapiclient.model.search;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class SearchResult {

    @JSONField(name = "live_room")
    private List<LiveRoom> liveRoom;

    @JSONField(name = "live_user")
    private List<LiveUser> liveUser;

    public List<LiveRoom> getLiveRoom() {
        return liveRoom;
    }

    public void setLiveRoom(List<LiveRoom> liveRoom) {
        this.liveRoom = liveRoom;
    }

    public List<LiveUser> getLiveUser() {
        return liveUser;
    }

    public void setLiveUser(List<LiveUser> liveUser) {
        this.liveUser = liveUser;
    }
}

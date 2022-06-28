package com.muedsa.bilibililiveapiclient.model.search;

import com.alibaba.fastjson.annotation.JSONField;

public class PageInfoAggregation {

    @JSONField(name = "live_room")
    private PageInfo liveRoom;

    @JSONField(name = "live_user")
    private PageInfo liveUser;

    public PageInfo getLiveRoom() {
        return liveRoom;
    }

    public void setLiveRoom(PageInfo liveRoom) {
        this.liveRoom = liveRoom;
    }

    public PageInfo getLiveUser() {
        return liveUser;
    }

    public void setLiveUser(PageInfo liveUser) {
        this.liveUser = liveUser;
    }
}

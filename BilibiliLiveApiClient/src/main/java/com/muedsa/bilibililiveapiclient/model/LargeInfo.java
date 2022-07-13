package com.muedsa.bilibililiveapiclient.model;

import com.alibaba.fastjson2.annotation.JSONField;

public class LargeInfo {

    @JSONField(name = "room_info")
    private RoomInfo roomInfo;

    @JSONField(name = "anchor_info")
    private AnchorInfo anchorInfo;

    //todo more field

    public RoomInfo getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(RoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }

    public AnchorInfo getAnchorInfo() {
        return anchorInfo;
    }

    public void setAnchorInfo(AnchorInfo anchorInfo) {
        this.anchorInfo = anchorInfo;
    }
}

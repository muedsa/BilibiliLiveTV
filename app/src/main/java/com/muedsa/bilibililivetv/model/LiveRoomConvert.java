package com.muedsa.bilibililivetv.model;

import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.model.RoomInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class LiveRoomConvert {

    public static LiveRoom buildWithRoomId(long roomId) {
        LiveRoom liveRoom = new LiveRoom();
        liveRoom.setId(roomId);
        liveRoom.setTitle("");
        liveRoom.setDescription("");
        liveRoom.setUname("");
        liveRoom.setCoverImageUrl("");
        liveRoom.setSystemCoverImageUrl("");
        liveRoom.setBackgroundImageUrl("");
        return liveRoom;
    }

    public static void updateRoomInfo(LiveRoom liveRoom, RoomInfo roomInfo){
        liveRoom.setId(roomInfo.getRoomId());
        liveRoom.setShortId(roomInfo.getShortId());
        liveRoom.setTitle(roomInfo.getTitle());
        liveRoom.setDescription(roomInfo.getDescription());

        if(!Strings.isNullOrEmpty(roomInfo.getKeyframe())){
            liveRoom.setSystemCoverImageUrl(roomInfo.getKeyframe());
        }

        if(!Strings.isNullOrEmpty(roomInfo.getUserCover())){
            liveRoom.setCoverImageUrl(roomInfo.getUserCover());
        }else if(Strings.isNullOrEmpty(liveRoom.getCoverImageUrl()) && !Strings.isNullOrEmpty(liveRoom.getSystemCoverImageUrl())){
            liveRoom.setCoverImageUrl(liveRoom.getSystemCoverImageUrl());
        }

        if(!Strings.isNullOrEmpty(roomInfo.getBackground())){
            liveRoom.setBackgroundImageUrl(roomInfo.getBackground());
        }else if(Strings.isNullOrEmpty(liveRoom.getBackgroundImageUrl()) && !Strings.isNullOrEmpty(liveRoom.getSystemCoverImageUrl())){
            liveRoom.setBackgroundImageUrl((liveRoom.getSystemCoverImageUrl()));
        }

        if(!Objects.isNull(roomInfo.getLiveStatus())){
            liveRoom.setLiveStatus(roomInfo.getLiveStatus());
        }

        if(!Objects.isNull(roomInfo.getOnline())){
            liveRoom.setOnlineNum(roomInfo.getOnline());
        }
    }
}
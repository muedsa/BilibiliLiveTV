package com.muedsa.bilibililivetv.model;

import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.model.LargeInfo;
import com.muedsa.bilibililiveapiclient.model.RoomInfo;
import com.muedsa.bilibililiveapiclient.uitl.ApiUtil;

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

        if(!Strings.isNullOrEmpty(roomInfo.getCover())){
            liveRoom.setCoverImageUrl(roomInfo.getCover());
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

    public static void updateRoomInfo(LiveRoom liveRoom, LargeInfo largeInfo){
        if(!Objects.isNull(largeInfo.getRoomInfo())){
            updateRoomInfo(liveRoom, largeInfo.getRoomInfo());
        }

        if(!Objects.isNull(largeInfo.getAnchorInfo())
                && !Objects.isNull(largeInfo.getAnchorInfo().getBaseInfo())){
            liveRoom.setUname(largeInfo.getAnchorInfo().getBaseInfo().getUname());
        }
    }

    public static void updateRoomInfo(LiveRoom liveRoom, com.muedsa.bilibililiveapiclient.model.search.LiveRoom searchLiveRoom){
        liveRoom.setId(searchLiveRoom.getRoomId());
        liveRoom.setShortId(searchLiveRoom.getShortId());
        liveRoom.setTitle(ApiUtil.removeSearchHighlight(searchLiveRoom.getTitle()));

        if(!Strings.isNullOrEmpty(searchLiveRoom.getCover())){
            liveRoom.setSystemCoverImageUrl(searchLiveRoom.getCover());
        }

        if(!Strings.isNullOrEmpty(searchLiveRoom.getUserCover())){
            liveRoom.setCoverImageUrl(searchLiveRoom.getUserCover());
        }else if(Strings.isNullOrEmpty(liveRoom.getCoverImageUrl()) && !Strings.isNullOrEmpty(liveRoom.getSystemCoverImageUrl())){
            liveRoom.setCoverImageUrl(liveRoom.getSystemCoverImageUrl());
        }

        if(!Strings.isNullOrEmpty(searchLiveRoom.getUserCover())){
            liveRoom.setBackgroundImageUrl(searchLiveRoom.getUserCover());
        }else if(Strings.isNullOrEmpty(liveRoom.getBackgroundImageUrl()) && !Strings.isNullOrEmpty(liveRoom.getSystemCoverImageUrl())){
            liveRoom.setBackgroundImageUrl((liveRoom.getSystemCoverImageUrl()));
        }

        if(!Objects.isNull(searchLiveRoom.getLiveStatus())){
            liveRoom.setLiveStatus(searchLiveRoom.getLiveStatus());
        }

        if(!Strings.isNullOrEmpty(searchLiveRoom.getUname())) {
            liveRoom.setUname(ApiUtil.removeSearchHighlight(searchLiveRoom.getUname()));
        }
    }
}
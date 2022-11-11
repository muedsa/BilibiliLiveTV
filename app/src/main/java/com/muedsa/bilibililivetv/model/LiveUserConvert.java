package com.muedsa.bilibililivetv.model;

import com.muedsa.bilibililiveapiclient.util.ApiUtil;

public class LiveUserConvert {

    public static LiveUser convert(com.muedsa.bilibililiveapiclient.model.search.LiveUser liveUser) {
        LiveUser result = new LiveUser();
        result.setRoomId(liveUser.getRoomId());
        result.setShortId(liveUser.getShortId());
        result.setLiveStatus(liveUser.getLiveStatus());
        result.setUface(liveUser.getUface());
        result.setUid(liveUser.getUid());
        result.setUname(ApiUtil.removeSearchHighlight(liveUser.getUname()));
        return result;
    }
}

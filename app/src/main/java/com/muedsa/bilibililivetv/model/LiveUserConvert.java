package com.muedsa.bilibililivetv.model;

import com.muedsa.bilibililiveapiclient.model.search.SearchLiveUser;
import com.muedsa.bilibililiveapiclient.util.ApiUtil;

public class LiveUserConvert {

    public static LiveUser convert(SearchLiveUser searchLiveUser) {
        LiveUser result = new LiveUser();
        result.setRoomId(searchLiveUser.getRoomId());
        result.setShortId(searchLiveUser.getShortId());
        result.setLiveStatus(searchLiveUser.getLiveStatus());
        result.setUface(searchLiveUser.getUface());
        result.setUid(searchLiveUser.getUid());
        result.setUname(ApiUtil.removeSearchHighlight(searchLiveUser.getUname()));
        return result;
    }
}

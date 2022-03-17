package com.muedsa.bilibililiveapiclient;

import com.alibaba.fastjson.TypeReference;
import com.muedsa.bilibililiveapiclient.model.BilibiliPageInfo;
import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.LiveRoomInfo;
import com.muedsa.bilibililiveapiclient.model.PlayUrlData;
import com.muedsa.bilibililiveapiclient.model.Qn;
import com.muedsa.bilibililiveapiclient.model.RoomInfo;
import com.muedsa.httpjsonclient.HttpJsonClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BilibiliLiveApiClient {

    private final HttpJsonClient httpJsonClient;

    public BilibiliLiveApiClient(){
        httpJsonClient = new HttpJsonClient();
    }

    public BilibiliResponse<PlayUrlData> getPlayUrlMessage(Long roomId, Qn qn) throws IOException {
        String url = ApiUrlContainer.fillUrl(ApiUrlContainer.ROOM_PLAY_URL, roomId, qn.getCode());
        return httpJsonClient.GetJson(url, new TypeReference<BilibiliResponse<PlayUrlData>>(){});
    }

    public BilibiliResponse<BilibiliPageInfo<LiveRoomInfo>> pageOnlineLiveRoom(int page, int pageSize, int parentAreaId) throws IOException {
        String url = ApiUrlContainer.fillUrl(ApiUrlContainer.GET_ROOM_LIST, page, pageSize, parentAreaId, "online");
        return httpJsonClient.GetJson(url, new TypeReference<BilibiliResponse<BilibiliPageInfo<LiveRoomInfo>>>(){});
    }

    public BilibiliResponse<RoomInfo> getRoomInfo(Long roomId) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", roomId);
        return httpJsonClient.PostJson(ApiUrlContainer.GET_ROOM_INFO,
                params,
                new TypeReference<BilibiliResponse<RoomInfo>>(){});
    }
}
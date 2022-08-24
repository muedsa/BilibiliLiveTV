package com.muedsa.bilibililiveapiclient;

import com.alibaba.fastjson2.TypeReference;
import com.muedsa.bilibililiveapiclient.model.BilibiliPageInfo;
import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.DanmakuInfo;
import com.muedsa.bilibililiveapiclient.model.LargeInfo;
import com.muedsa.bilibililiveapiclient.model.LiveRoomInfo;
import com.muedsa.bilibililiveapiclient.model.PlayUrlData;
import com.muedsa.bilibililiveapiclient.model.Qn;
import com.muedsa.bilibililiveapiclient.model.search.SearchAggregation;
import com.muedsa.bilibililiveapiclient.uitl.ApiUtil;
import com.muedsa.httpjsonclient.HttpJsonClient;

import java.io.IOException;
import java.util.HashMap;

public class BilibiliLiveApiClient {

    private final HttpJsonClient httpJsonClient;

    private static final HashMap<String, String> BILIBILI_HTTP_HEADER = new HashMap<>(2);

    static {
        BILIBILI_HTTP_HEADER.put(HttpJsonClient.HEADER_KEY_USER_AGENT, HttpJsonClient.HEADER_VALUE_USER_AGENT);
        BILIBILI_HTTP_HEADER.put(HttpJsonClient.HEADER_KEY_COOKIE, "buvid3=undefined;");
    }

    public BilibiliLiveApiClient(){
        httpJsonClient = new HttpJsonClient();
    }

    public BilibiliResponse<PlayUrlData> getPlayUrlMessage(Long roomId, Qn qn) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.ROOM_PLAY_URL, roomId, qn.getCode());
        return httpJsonClient.GetJson(url, new TypeReference<BilibiliResponse<PlayUrlData>>(){}, BILIBILI_HTTP_HEADER);
    }

    public BilibiliResponse<BilibiliPageInfo<LiveRoomInfo>> pageOnlineLiveRoom(int page, int pageSize, int parentAreaId) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.GET_ROOM_LIST, page, pageSize, parentAreaId, "online");
        return httpJsonClient.GetJson(url, new TypeReference<BilibiliResponse<BilibiliPageInfo<LiveRoomInfo>>>(){}, BILIBILI_HTTP_HEADER);
    }

    public BilibiliResponse<LargeInfo> getLargeInfo(Long roomId) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.GET_INFO, roomId);
        return httpJsonClient.GetJson(url, new TypeReference<BilibiliResponse<LargeInfo>>(){}, BILIBILI_HTTP_HEADER);
    }

    public BilibiliResponse<DanmakuInfo> getDanmuInfo(Long roomId) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.GET_DANMU_INFO, roomId);
        return httpJsonClient.GetJson(url, new TypeReference<BilibiliResponse<DanmakuInfo>>(){}, BILIBILI_HTTP_HEADER);
    }

    public BilibiliResponse<SearchAggregation> searchLive(String keyword, int page, int pageSize) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.SEARCH_LIVE, page, pageSize, keyword);
        return httpJsonClient.GetJson(url, new TypeReference<BilibiliResponse<SearchAggregation>>(){}, BILIBILI_HTTP_HEADER);
    }
}
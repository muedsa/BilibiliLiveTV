package com.muedsa.bilibililiveapiclient;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.muedsa.bilibililiveapiclient.model.BilibiliPageInfo;
import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.UserNav;
import com.muedsa.bilibililiveapiclient.model.danmaku.DanmakuElem;
import com.muedsa.bilibililiveapiclient.model.danmaku.DmSegMobileReply;
import com.muedsa.bilibililiveapiclient.model.danmaku.DmWebViewReply;
import com.muedsa.bilibililiveapiclient.model.dynamic.DynamicFlow;
import com.muedsa.bilibililiveapiclient.model.history.HistoryTable;
import com.muedsa.bilibililiveapiclient.model.live.DanmakuInfo;
import com.muedsa.bilibililiveapiclient.model.live.LargeInfo;
import com.muedsa.bilibililiveapiclient.model.live.LiveRoomInfo;
import com.muedsa.bilibililiveapiclient.model.live.PlayUrlData;
import com.muedsa.bilibililiveapiclient.model.live.Qn;
import com.muedsa.bilibililiveapiclient.model.live.UserWebListResult;
import com.muedsa.bilibililiveapiclient.model.passport.LoginInfo;
import com.muedsa.bilibililiveapiclient.model.passport.LoginResponse;
import com.muedsa.bilibililiveapiclient.model.passport.LoginUrl;
import com.muedsa.bilibililiveapiclient.model.search.SearchAggregation;
import com.muedsa.bilibililiveapiclient.model.search.SearchResult;
import com.muedsa.bilibililiveapiclient.model.search.SearchVideoInfo;
import com.muedsa.bilibililiveapiclient.model.space.SpaceSearchResult;
import com.muedsa.bilibililiveapiclient.model.video.Heartbeat;
import com.muedsa.bilibililiveapiclient.model.video.PlayInfo;
import com.muedsa.bilibililiveapiclient.model.video.VideoData;
import com.muedsa.bilibililiveapiclient.model.video.VideoDetail;
import com.muedsa.bilibililiveapiclient.model.video.VideoInfo;
import com.muedsa.bilibililiveapiclient.util.ApiUtil;
import com.muedsa.bilibililiveapiclient.util.WbiUtil;
import com.muedsa.httpjsonclient.HttpClientContainer;
import com.muedsa.httpjsonclient.HttpJsonClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BilibiliLiveApiClient {
    private static final Logger logger = Logger.getGlobal();

    private final HttpJsonClient httpJsonClient;

    private final HashMap<String, String> requestHeader = new HashMap<>();

    private final HashMap<String, String> simpleCookie = new HashMap<>();

    public BilibiliLiveApiClient(){
        httpJsonClient = new HttpJsonClient();
        putHeader(HttpClientContainer.HEADER_KEY_USER_AGENT, HttpClientContainer.HEADER_VALUE_USER_AGENT);
        putHeader(HttpClientContainer.HEADER_KEY_ACCEPT_ENCODING, HttpClientContainer.HEADER_VALUE_PART_ENCODING_IDENTITY);
        putCookie(BilibiliApiContainer.COOKIE_KEY_BUVID3, BilibiliApiContainer.COOKIE_VALUE_BUVID3);
    }

    public void putHeader(String k, String v){
        requestHeader.put(k, v);
    }

    public String getCookie(String k){
        return simpleCookie.get(k);
    }

    public void putCookie(String k, String v){
        simpleCookie.put(k, v);
        updateHeaderCookie();
    }

    public void removeCookie(String k){
        simpleCookie.remove(k);
        updateHeaderCookie();
    }

    public void clearCookie() {
        simpleCookie.clear();
        updateHeaderCookie();
    }

    private void updateHeaderCookie(){
        Iterator<Map.Entry<String, String>> iterator = simpleCookie.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("; ");
        }
        putHeader(HttpClientContainer.HEADER_KEY_COOKIE, sb.toString());
    }

    public String getCookies() {
        return requestHeader.getOrDefault(HttpClientContainer.HEADER_KEY_COOKIE, "");
    }

    public BilibiliResponse<PlayUrlData> getPlayUrlMessage(Long roomId, Qn qn) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.ROOM_PLAY_URL, roomId, qn.getCode());
        return httpJsonClient.getJson(url, new TypeReference<BilibiliResponse<PlayUrlData>>() {
        }, requestHeader);
    }

    public BilibiliResponse<BilibiliPageInfo<LiveRoomInfo>> pageOnlineLiveRoom(int page, int pageSize, int parentAreaId) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.GET_ROOM_LIST, page, pageSize, parentAreaId, "online");
        return httpJsonClient.getJson(url, new TypeReference<BilibiliResponse<BilibiliPageInfo<LiveRoomInfo>>>() {
        }, requestHeader);
    }

    public BilibiliResponse<LargeInfo> getLargeInfo(Long roomId) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.GET_INFO, roomId);
        return httpJsonClient.getJson(url, new TypeReference<BilibiliResponse<LargeInfo>>(){}, requestHeader);
    }

    public BilibiliResponse<DanmakuInfo> getDanmuInfo(Long roomId) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.GET_DANMU_INFO, roomId);
        return httpJsonClient.getJson(url, new TypeReference<BilibiliResponse<DanmakuInfo>>(){}, requestHeader);
    }

    public BilibiliResponse<SearchAggregation<SearchResult>> searchLive(String keyword, int page, int pageSize) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.SEARCH_LIVE, page, pageSize, keyword);
        return httpJsonClient.getJson(url, new TypeReference<BilibiliResponse<SearchAggregation<SearchResult>>>(){}, requestHeader);
    }

    public BilibiliResponse<SearchAggregation<List<SearchVideoInfo>>> searchVideo(String keyword, int page, int pageSize) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.SEARCH_VIDEO, page, pageSize, keyword);
        return httpJsonClient.getJson(url, new TypeReference<BilibiliResponse<SearchAggregation<List<SearchVideoInfo>>>>(){}, requestHeader);
    }

    public BilibiliResponse<LoginUrl> getLoginUrl() throws IOException {
        return httpJsonClient.getJson(ApiUrlContainer.GET_LOGIN_URL, new TypeReference<BilibiliResponse<LoginUrl>>(){}, requestHeader);
    }

    public LoginResponse getLoginInfo(String oauthKey) throws IOException {
        String json = httpJsonClient.post(ApiUrlContainer.GET_LOGIN_INFO,
                Collections.singletonMap("oauthKey", oauthKey), requestHeader);
        JSONObject jsonObject = JSON.parseObject(json);
        LoginResponse response = jsonObject.to(new TypeReference<LoginResponse>(){});
        if(response.getStatus()){
            response.setData(jsonObject.getObject("data", LoginInfo.class));
        } else {
            response.setIntData(jsonObject.getIntValue("data"));
        }
        return response;
    }

    public BilibiliResponse<UserNav> nav() throws IOException {
        return httpJsonClient.getJson(ApiUrlContainer.USER_NAV, new TypeReference<BilibiliResponse<UserNav>>() {
        }, requestHeader);
    }

    public VideoDetail getVideoDetail(String bv) throws IOException {
        return getVideoDetail(bv, 1);
    }

    public VideoDetail getVideoDetail(String bv, int page) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.VIDEO_URL, bv, page);
        String html = httpJsonClient.get(url, requestHeader);
        VideoInfo videoInfo = parseVideoInfo(html);
        BilibiliResponse<PlayInfo> playInfo = parsePlayInfo(html);
        VideoDetail videoDetail = new VideoDetail();
        videoDetail.setVideoInfo(videoInfo);
        videoDetail.setPlayInfoResponse(playInfo);
        videoDetail.setUrl(url);
        return videoDetail;
    }

    private static final Pattern VIDEO_INFO_PATTERN = Pattern.compile("[\\s\\S]*?<script>window\\.__INITIAL_STATE__=([\\s\\S]*?);\\(function\\(\\)[\\s\\S]*?</script>[\\s\\S]*?", Pattern.MULTILINE);

    public static VideoInfo parseVideoInfo(String html) {
        VideoInfo videoInfo = null;
        Matcher matcher = VIDEO_INFO_PATTERN.matcher(html);
        if (matcher.matches()) {
            videoInfo = JSON.parseObject(matcher.group(1), new TypeReference<VideoInfo>() {
            });
        } else {
            logger.warning("parseVideoInfo fail");
            logger.warning("\n" + html);
        }
        return videoInfo;
    }

    private static final Pattern PLAY_INFO_PATTERN = Pattern.compile("[\\s\\S]*?<script>window\\.__playinfo__=([\\s\\S]*?)</script>[\\s\\S]*?", Pattern.MULTILINE);

    public static BilibiliResponse<PlayInfo> parsePlayInfo(String html) {
        BilibiliResponse<PlayInfo> playInfo = null;
        Matcher matcher = PLAY_INFO_PATTERN.matcher(html);
        if (matcher.matches()) {
            playInfo = JSON.parseObject(matcher.group(1), new TypeReference<BilibiliResponse<PlayInfo>>() {
            });
            if (Objects.isNull(playInfo.getData())) {
                logger.warning("parsePlayInfo fail, data is null");
                logger.warning("\n" + html);
            }
        } else {
            logger.warning("parsePlayInfo fail");
            logger.warning("\n" + html);
        }
        return playInfo;
    }

    public BilibiliResponse<HistoryTable> history() throws IOException {
        return httpJsonClient.getJson(ApiUrlContainer.HISTORY_LIST, new TypeReference<BilibiliResponse<HistoryTable>>() {
        }, requestHeader);
    }

    public DmWebViewReply videoDanmakuView(long oid) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.VIDEO_DANMAKU_VIEW, oid);
        byte[] data = httpJsonClient.getByteArray(url, requestHeader);
        return DmWebViewReply.parseFrom(data);
    }

    public DmSegMobileReply videoDanmakuSegment(long oid, int segmentIndex) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.VIDEO_DANMAKU_SEGMENT, oid, segmentIndex);
        byte[] data = httpJsonClient.getByteArray(url, requestHeader);
        return DmSegMobileReply.parseFrom(data);
    }

    public List<DanmakuElem> videoDanmakuElemList(long oid, int segmentSize) throws IOException {
        List<DanmakuElem> list = new ArrayList<>();
        for (int i = 1; i < segmentSize + 1; i++) {
            DmSegMobileReply dmSegMobileReply = videoDanmakuSegment(oid, i);
            if (dmSegMobileReply.getElemsCount() > 0) {
                list.addAll(dmSegMobileReply.getElemsList());
            }
        }
        return list.stream()
                .sorted(Comparator.comparingInt(DanmakuElem::getProgress))
                .collect(Collectors.toList());
    }

    public BilibiliResponse<BilibiliPageInfo<VideoData>> popular(int pageNum, int pageSize)
            throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.POPULAR, pageNum, pageSize);
        return httpJsonClient.getJson(url,
                new TypeReference<BilibiliResponse<BilibiliPageInfo<VideoData>>>() {},
                requestHeader);
    }

    public BilibiliResponse<Void> heartbeat(Heartbeat heartbeat) throws IOException {
        String csrf = getCookie(BilibiliApiContainer.COOKIE_KEY_BILI_JCT);
        String mid = getCookie(BilibiliApiContainer.COOKIE_KEY_USER_ID);
        HashMap<String, Object> params = new HashMap<>();
        params.put(Heartbeat.FIELD_BVID, heartbeat.getBvid());
        params.put(Heartbeat.FIELD_CID, heartbeat.getCid());
        params.put(Heartbeat.FIELD_MID, mid);
        params.put(Heartbeat.FIELD_TYPE, heartbeat.getType());
        params.put(Heartbeat.FIELD_SUB_TYPE, heartbeat.getSubType());
        params.put(Heartbeat.FIELD_DT, 2);
        params.put(Heartbeat.FIELD_QUALITY, heartbeat.getQuality());
        params.put(Heartbeat.FIELD_VIDEO_DURATION, heartbeat.getVideoDuration());
        params.put(Heartbeat.FIELD_PLAYED_TIME, heartbeat.getPlayedTime());
        params.put(Heartbeat.FIELD_REALTIME, heartbeat.getRealtime());
        params.put(Heartbeat.FIELD_START_TS, heartbeat.getStartTs());
        params.put(Heartbeat.FIELD_LAST_PLAY_PROGRESS_TIME, heartbeat.getLastPlayProgressTime());
        params.put(Heartbeat.FIELD_MAX_PLAY_PROGRESS_TIME, heartbeat.getMaxPlayProgressTime());
        params.put(Heartbeat.FIELD_PLAY_TYPE, heartbeat.getPlayType());
        params.put(Heartbeat.FIELD_REFER_URL, "https://www.bilibili.com/");
        params.put(Heartbeat.FIELD_CSRF, csrf);
        return httpJsonClient.postJson(ApiUrlContainer.VIDEO_HEARTBEAT, params,
                new TypeReference<BilibiliResponse<Void>>() {}, requestHeader);
    }

    public BilibiliResponse<UserWebListResult> liveUserWebList(int pageNum, int pageSize) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.LIVE_USER_WEB_LIST, pageNum, pageSize, System.currentTimeMillis());
        return httpJsonClient.getJson(url,
                new TypeReference<BilibiliResponse<UserWebListResult>>() {},
                requestHeader);
    }

    public BilibiliResponse<DynamicFlow> dynamicNew(List<Integer> typeList) throws IOException {
        String mid = getCookie(BilibiliApiContainer.COOKIE_KEY_USER_ID);
        String url = ApiUtil.fillUrl(ApiUrlContainer.DYNAMIC_NEW, Long.parseLong(mid), typeList.stream()
                .map(Object::toString).collect(Collectors.joining(",")));
        return httpJsonClient.getJson(url,
                new TypeReference<BilibiliResponse<DynamicFlow>>() {},
                requestHeader);
    }

    public BilibiliResponse<SpaceSearchResult> spaceSearch(int pageNum, int pageSize, long mid, String mixinKey)
            throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(BilibiliApiContainer.QUERY_KEY_MID, mid);
        params.put(BilibiliApiContainer.QUERY_KEY_PN, pageNum);
        params.put(BilibiliApiContainer.QUERY_KEY_PS, pageSize);
        params.put(BilibiliApiContainer.QUERY_KEY_INDEX, 1);
        params.put(BilibiliApiContainer.QUERY_KEY_ORDER, BilibiliApiContainer.ORDER_BY_PUBLIC_DATE);
        params.put(BilibiliApiContainer.QUERY_KEY_ORDER_AVOIDED, this);
        params.put(BilibiliApiContainer.QUERY_KEY_PLATFORM, BilibiliApiContainer.PLATFORM_WEB);
        params.put(BilibiliApiContainer.QUERY_KEY_WEB_LOCATION, BilibiliApiContainer.WEB_LOCATION_SPACE);
        WbiUtil.fillWbiParams(params, mixinKey);
        return httpJsonClient.getJson(ApiUtil.buildUrlWithParams(ApiUrlContainer.SPACE_SEARCH, params),
                new TypeReference<BilibiliResponse<SpaceSearchResult>>() {},
                requestHeader);
    }
}
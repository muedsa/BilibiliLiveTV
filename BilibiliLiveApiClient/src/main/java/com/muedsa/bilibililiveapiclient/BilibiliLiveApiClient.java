package com.muedsa.bilibililiveapiclient;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.muedsa.bilibililiveapiclient.model.BilibiliPageInfo;
import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.UserNav;
import com.muedsa.bilibililiveapiclient.model.live.DanmakuInfo;
import com.muedsa.bilibililiveapiclient.model.live.LargeInfo;
import com.muedsa.bilibililiveapiclient.model.live.LiveRoomInfo;
import com.muedsa.bilibililiveapiclient.model.live.PlayUrlData;
import com.muedsa.bilibililiveapiclient.model.live.Qn;
import com.muedsa.bilibililiveapiclient.model.passport.LoginInfo;
import com.muedsa.bilibililiveapiclient.model.passport.LoginResponse;
import com.muedsa.bilibililiveapiclient.model.passport.LoginUrl;
import com.muedsa.bilibililiveapiclient.model.search.SearchAggregation;
import com.muedsa.bilibililiveapiclient.model.video.PlayInfo;
import com.muedsa.bilibililiveapiclient.model.video.VideoDetail;
import com.muedsa.bilibililiveapiclient.model.video.VideoInfo;
import com.muedsa.bilibililiveapiclient.uitl.ApiUtil;
import com.muedsa.httpjsonclient.Container;
import com.muedsa.httpjsonclient.HttpJsonClient;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BilibiliLiveApiClient {
    private static final Logger logger = Logger.getGlobal();

    private final HttpJsonClient httpJsonClient;

    private final HashMap<String, String> requestHeader = new HashMap<>();

    private final HashMap<String, String> simpleCookie = new HashMap<>();

    public BilibiliLiveApiClient(){
        httpJsonClient = new HttpJsonClient();
        putHeader(Container.HEADER_KEY_USER_AGENT, Container.HEADER_VALUE_USER_AGENT);
        putHeader(Container.HEADER_KEY_ACCEPT_ENCODING, Container.HEADER_VALUE_PART_ENCODING_IDENTITY);
        putCookie(Container.COOKIE_KEY_BUVID3, Container.COOKIE_VALUE_BUVID3);
    }

    public void putHeader(String k, String v){
        requestHeader.put(k, v);
    }

    public void putCookie(String k, String v){
        simpleCookie.put(k, v);
        Iterator<Map.Entry<String, String>> iterator = simpleCookie.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("; ");
        }
        putHeader(Container.HEADER_KEY_COOKIE, sb.toString());
    }

    public String getCookies() {
        return requestHeader.getOrDefault(Container.HEADER_KEY_COOKIE, "");
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

    public BilibiliResponse<SearchAggregation> searchLive(String keyword, int page, int pageSize) throws IOException {
        String url = ApiUtil.fillUrl(ApiUrlContainer.SEARCH_LIVE, page, pageSize, keyword);
        return httpJsonClient.getJson(url, new TypeReference<BilibiliResponse<SearchAggregation>>(){}, requestHeader);
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

}
package com.muedsa.bilibililivetv.request;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.ErrorCode;
import com.muedsa.bilibililiveapiclient.model.BilibiliPageInfo;
import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.UserNav;
import com.muedsa.bilibililiveapiclient.model.dynamic.DynamicFlow;
import com.muedsa.bilibililiveapiclient.model.dynamic.VideoDynamicCard;
import com.muedsa.bilibililiveapiclient.model.history.HistoryTable;
import com.muedsa.bilibililiveapiclient.model.live.DanmakuInfo;
import com.muedsa.bilibililiveapiclient.model.live.LargeInfo;
import com.muedsa.bilibililiveapiclient.model.live.LiveRoomInfo;
import com.muedsa.bilibililiveapiclient.model.live.PlayUrlData;
import com.muedsa.bilibililiveapiclient.model.live.Qn;
import com.muedsa.bilibililiveapiclient.model.live.UserWebListResult;
import com.muedsa.bilibililiveapiclient.model.passport.LoginResponse;
import com.muedsa.bilibililiveapiclient.model.passport.LoginUrl;
import com.muedsa.bilibililiveapiclient.model.search.SearchAggregation;
import com.muedsa.bilibililiveapiclient.model.search.SearchResult;
import com.muedsa.bilibililiveapiclient.model.search.SearchVideoInfo;
import com.muedsa.bilibililiveapiclient.model.space.SpaceSearchResult;
import com.muedsa.bilibililiveapiclient.model.video.Heartbeat;
import com.muedsa.bilibililiveapiclient.model.video.VideoData;
import com.muedsa.bilibililiveapiclient.model.video.VideoDetail;
import com.muedsa.bilibililiveapiclient.util.WbiUtil;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;
import com.muedsa.bilibililivetv.container.GithubApi;
import com.muedsa.bilibililivetv.preferences.Prefs;
import com.muedsa.github.model.BaseResponse;
import com.muedsa.github.model.GithubReleaseTagInfo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;

public class RxRequestFactory {
    private static final String TAG = RxRequestFactory.class.getSimpleName();

    public static final String RESPONSE_DATA_EMPTY = "[%s] response data is empty";

    public static final String REQUEST_ERROR = "[%s] request error";

    public static final Function<Void, Integer> VOID_TO_200 = ignore -> 200;

    public static Single<SearchResult> bilibiliSearchLive(String query) {
        return Single.create(emitter -> {
            BilibiliResponse<SearchAggregation<SearchResult>> response = BilibiliLiveApi.client().searchLive(query, 1, 10);
            handleResponse(response, emitter, SearchAggregation::getResult, "BilibiliSearchLive", true, null);
        });
    }

    public static Single<List<SearchVideoInfo>> bilibiliSearchVideo(String query) {
        return Single.create(emitter -> {
            BilibiliResponse<SearchAggregation<List<SearchVideoInfo>>> response = BilibiliLiveApi.client().searchVideo(query, 1, 25);
            handleResponse(response, emitter, SearchAggregation::getResult, "BilibiliSearchVideo", true, null);
        });
    }

    public static Single<String> bilibiliDanmuToken(long roomId) {
        return Single.create(emitter -> {
            BilibiliResponse<DanmakuInfo> response = BilibiliLiveApi.client().getDanmuInfo(roomId);
            handleResponse(response, emitter, DanmakuInfo::getToken, "BilibiliDanmuToken", true, null);
        });
    }

    public static Single<LargeInfo> bilibiliDanmuRoomInfo(long roomId) {
        return Single.create(emitter -> {
            BilibiliResponse<LargeInfo> response = BilibiliLiveApi.client().getLargeInfo(roomId);
            handleResponse(response, emitter, Function.identity(), "BilibiliDanmuRoomInfo", true, null);
        });
    }

    public static Single<PlayUrlData> bilibiliPlayUrlMessage(long roomId) {
        return Single.create(emitter -> {
            BilibiliResponse<PlayUrlData> response = BilibiliLiveApi.client().getPlayUrlMessage(roomId, Qn.RAW);
            handleResponse(response, emitter, Function.identity(), "BilibiliPlayUrlMessage", true, null);
        });
    }

    public static Single<UserNav> bilibiliNav() {
        return Single.create(emitter -> {
            BilibiliResponse<UserNav> response = BilibiliLiveApi.client().nav();
            handleResponse(response, emitter, Function.identity(), "BilibiliNav", true, null);
        });
    }

    public static Single<LoginUrl> bilibiliLoginUrl() {
        return Single.create(emitter -> {
            BilibiliResponse<LoginUrl> response = BilibiliLiveApi.client().getLoginUrl();
            handleResponse(response, emitter, Function.identity(), "BilibiliLoginUrl", true, null);
        });
    }

    public static Single<LoginResponse> bilibiliLoginInfo(String oauthKey) {
        return Single.create(emitter -> {
            LoginResponse loginResponse = BilibiliLiveApi.client().getLoginInfo(oauthKey);
            if (Objects.nonNull(loginResponse)) {
                emitter.onSuccess(loginResponse);
            } else {
                emitter.onError(HttpRequestException.create("request loginResponse error"));
            }
        });
    }

    public static Single<VideoDetail> bilibiliVideoDetail(String bv, int page) {
        return Single.create(emitter -> {
            VideoDetail videoDetail = BilibiliLiveApi.client().getVideoDetail(bv, page);
            if (Objects.nonNull(videoDetail)) {
                emitter.onSuccess(videoDetail);
            } else {
                emitter.onError(HttpRequestException.create("request videoDetail error"));
            }
        });
    }

    public static Single<HistoryTable> bilibiliHistory() {
        return Single.create(emitter -> {
            BilibiliResponse<HistoryTable> response = BilibiliLiveApi.client().history();
            handleResponse(response, emitter, Function.identity(), "BilibiliHistory", true, null);
        });
    }

    public static Single<List<VideoData>> bilibiliVideoPopular(int pageNum, int pageSize) {
        return Single.create(emitter -> {
            BilibiliResponse<BilibiliPageInfo<VideoData>> response = BilibiliLiveApi.client().popular(pageNum, pageSize);
            handleResponse(response, emitter, BilibiliPageInfo::getList, "BilibiliPopular", true, null);
        });
    }

    public static Single<Integer> bilibiliVideoHeartbeat(Heartbeat heartbeat) {
        return Single.create(emitter -> {
            BilibiliResponse<Void> response = BilibiliLiveApi.client().heartbeat(heartbeat);
            handleResponse(response, emitter, VOID_TO_200, "BilibiliHeartbeat", false, null);
        });
    }

    public static Single<List<LiveRoomInfo>> bilibiliFollowedLivingRooms(int pageNum, int pageSize) {
        return Single.create(emitter -> {
            BilibiliResponse<UserWebListResult> response = BilibiliLiveApi.client().liveUserWebList(pageNum, pageSize);
            handleResponse(response, emitter, UserWebListResult::getRooms, "BilibiliFollowedLivingRooms", true, null);
        });
    }

    public static Single<List<VideoDynamicCard>> bilibiliVideoDynamic() {
        return Single.create(emitter -> {
            BilibiliResponse<DynamicFlow> response = BilibiliLiveApi.client().dynamicNew(Collections.singletonList(8));
            handleResponse(response, emitter, resp -> resp.getCards().stream()
                    .filter(card -> card.getDesc().getType() == 8).map(card -> {
                VideoDynamicCard videoDynamicCard = JSON.parseObject(card.getCard(), new TypeReference<VideoDynamicCard>(){});
                videoDynamicCard.setBvid(card.getDesc().getBvid());
                return videoDynamicCard;
            }).collect(Collectors.toList()), "BilibiliVideoDynamic", true, null);
        });
    }

    public static Single<SpaceSearchResult> bilibiliSpaceSearchVideos(int pageNum, int pageSize, long mid){
        return getWbiKey().map(wbiKey -> {
            BilibiliResponse<SpaceSearchResult> response = BilibiliLiveApi.client().spaceSearch(pageNum, pageSize, mid, wbiKey);
            return handleResponse(response, Function.identity(), "BilibiliSpaceSearchVideos", true, null);
        });
    }

    private static Single<String> getWbiKey() {
        return Single.create(emitter -> {
            String wbiKey = Prefs.getString(Prefs.BILIBILI_WBI_KEY);
            long ms = Prefs.getLong(Prefs.BILIBILI_WBI_KEY_TIME);
            LocalDateTime keyTime = LocalDateTime.ofInstant(new Date(ms).toInstant(), ZoneId.of("Asia/Shanghai"));
            LocalDateTime currentTime = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.of("Asia/Shanghai"));
            if (!Strings.isNullOrEmpty(wbiKey)
                    && keyTime.getYear() == currentTime.getYear()
                    && keyTime.getMonthValue() == currentTime.getMonthValue()
                    && keyTime.getDayOfMonth() == currentTime.getDayOfMonth()) {
                emitter.onSuccess(wbiKey);
            } else {
                BilibiliResponse<UserNav> response = BilibiliLiveApi.client().nav();
                handleResponse(response, emitter,
                        nav -> WbiUtil.getMixinKey(nav.getWbiImg().getImgKey(), nav.getWbiImg().getSubKey()),
                        "BilibiliNav wbi key", true,
                        (resp, result) -> {
                            Prefs.putString(Prefs.BILIBILI_WBI_KEY, result);
                            Prefs.putLong(Prefs.BILIBILI_WBI_KEY_TIME, System.currentTimeMillis());
                            return true;
                        }
                );
            }
        });
    }

    public static Single<GithubReleaseTagInfo> githubLatestRelease() {
        return Single.create(emitter -> {
            BaseResponse<GithubReleaseTagInfo> response = GithubApi.client().getLatestReleaseInfo(GithubApi.GITHUB_USER, GithubApi.GITHUB_REPO);
            if (response != null) {
                if (response.getCode() == ErrorCode.SUCCESS) {
                    emitter.onSuccess(response.getData());
                } else {
                    emitter.onError(HttpRequestException.create(response.getMsg()));
                }
            }else{
                emitter.onError(HttpRequestException.create(String.format(RESPONSE_DATA_EMPTY, "GithubLatestRelease")));
            }
        });
    }

    public static <T,R> void handleResponse(BilibiliResponse<T> response, SingleEmitter<R> emitter,
                                            Function<T, R> converter, String tag, boolean emptyValid,
                                            BeforeSuccess<T, R> beforeSuccess) {
        long code = ErrorCode.UNKNOWN;
        if(Objects.nonNull(response)){
            if(Objects.nonNull(response.getCode())) {
                code = response.getCode();
            }
            if(code == ErrorCode.SUCCESS){
                handleResponseConverter(code, response, emitter, converter, tag, emptyValid, beforeSuccess);
            }else{
                emitter.onError(HttpRequestException.create(code, response.getMessage()));
            }
        }else{
            emitter.onError(HttpRequestException.create(code, String.format(REQUEST_ERROR, tag)));
        }
    }

    private static <T,R> void handleResponseConverter(long code, BilibiliResponse<T> response,
                                                      SingleEmitter<R> emitter, Function<T, R> converter,
                                                      String tag, boolean emptyValid, BeforeSuccess<T, R> beforeSuccess) {
        R result = converter.apply(response.getData());
        if(emptyValid && !validEmpty(result)){
            emitter.onError(HttpRequestException.create(code, String.format(RESPONSE_DATA_EMPTY, tag)));
        }else{
            if(Objects.isNull(beforeSuccess) || beforeSuccess.before(response, result)){
                emitter.onSuccess(result);
            }
        }
    }

    private static boolean validEmpty(Object object){
        if(object instanceof String){
            return !Strings.isNullOrEmpty((String) object);
        }else if(object instanceof Collection){
            return !((Collection<?>) object).isEmpty();
        }else{
            return Objects.nonNull(object);
        }
    }

    public static <T,R> R handleResponse(BilibiliResponse<T> response,
                                            Function<T, R> converter,
                                            String tag,
                                            boolean emptyValid,
                                            BeforeSuccess<T, R> beforeSuccess) {
        long code = ErrorCode.UNKNOWN;
        if(Objects.nonNull(response)){
            if(Objects.nonNull(response.getCode())) {
                code = response.getCode();
            }
            if(code == ErrorCode.SUCCESS){
                return handleResponseConverter(code, response, converter, tag, emptyValid, beforeSuccess);
            }else{
                throw HttpRequestException.create(code, response.getMessage());
            }
        }else{
            throw HttpRequestException.create(code, String.format(REQUEST_ERROR, tag));
        }
    }

    private static <T,R> R handleResponseConverter(long code, BilibiliResponse<T> response,
                                                      Function<T, R> converter,
                                                      String tag, boolean emptyValid, BeforeSuccess<T, R> beforeSuccess) {
        R result = converter.apply(response.getData());
        if(emptyValid && !validEmpty(result)){
            throw HttpRequestException.create(code, String.format(RESPONSE_DATA_EMPTY, tag));
        }else{
            if(Objects.isNull(beforeSuccess) || beforeSuccess.before(response, result)){
                return result;
            }else{
                throw new IllegalStateException("not success for beforeSuccess call");
            }
        }
    }


    @FunctionalInterface
    interface BeforeSuccess<T, R> {
         boolean before(BilibiliResponse<T> response, R result);
    }
}

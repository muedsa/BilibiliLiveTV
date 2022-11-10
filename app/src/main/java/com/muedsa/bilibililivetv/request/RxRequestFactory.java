package com.muedsa.bilibililivetv.request;


import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.DanmakuInfo;
import com.muedsa.bilibililiveapiclient.model.LargeInfo;
import com.muedsa.bilibililiveapiclient.model.LoginResponse;
import com.muedsa.bilibililiveapiclient.model.LoginUrl;
import com.muedsa.bilibililiveapiclient.model.PlayUrlData;
import com.muedsa.bilibililiveapiclient.model.Qn;
import com.muedsa.bilibililiveapiclient.model.UserNav;
import com.muedsa.bilibililiveapiclient.model.search.SearchAggregation;
import com.muedsa.bilibililiveapiclient.model.search.SearchResult;
import com.muedsa.bilibililiveapiclient.ErrorCode;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;
import com.muedsa.bilibililivetv.container.GithubApi;
import com.muedsa.github.model.BaseResponse;
import com.muedsa.github.model.GithubReleaseTagInfo;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;

public class RxRequestFactory {
    private static final String TAG = RxRequestFactory.class.getSimpleName();

    public static final String RESPONSE_DATA_EMPTY = "[%s] response data is empty";

    public static final String REQUEST_ERROR = "[%s] request error";

    public static Single<SearchResult> bilibiliSearchLive(String query) {
        return Single.create(emitter -> {
            BilibiliResponse<SearchAggregation> response = BilibiliLiveApi.client().searchLive(query, 1, 10);
            handleResponse(response, emitter, SearchAggregation::getResult, "BilibiliDanmuToken", true, null);
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
            if(Objects.nonNull(loginResponse)){
                emitter.onSuccess(loginResponse);
            }else{
                emitter.onError(HttpRequestException.create("request loginResponse error"));
            }
        });
    }

    public static Single<GithubReleaseTagInfo> githubLatestRelease() {
        return Single.create(emitter -> {
            BaseResponse<GithubReleaseTagInfo> response = GithubApi.client().getLatestReleaseInfo(GithubApi.GITHUB_USER, GithubApi.GITHUB_REPO);
            if(response != null){
                if(response.getCode() == ErrorCode.SUCCESS) {
                    emitter.onSuccess(response.getData());
                }else{
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
                if(emptyValid && Objects.isNull(response.getData())){
                    if(Objects.nonNull(response.getData())){
                        handleResponseConverter(code, response, emitter, converter, tag, true, beforeSuccess);
                    }else{
                        emitter.onError(HttpRequestException.create(code, String.format(RESPONSE_DATA_EMPTY, tag)));
                    }
                }else{
                    handleResponseConverter(code, response, emitter, converter, tag, emptyValid, beforeSuccess);
                }
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
        if(emptyValid && validEmpty(result)){
            if(Objects.isNull(beforeSuccess) || !beforeSuccess.before(response, emitter)){
                emitter.onSuccess(result);
            }
        }else{
            emitter.onError(HttpRequestException.create(code, String.format(RESPONSE_DATA_EMPTY, tag)));
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

    @FunctionalInterface
    interface BeforeSuccess<T, R> {
         boolean before(BilibiliResponse<T> response, SingleEmitter<R> emitter);
    }
}

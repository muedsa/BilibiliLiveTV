package com.muedsa.bilibililivetv.task;

import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.DanmakuInfo;
import com.muedsa.bilibililiveapiclient.model.LargeInfo;
import com.muedsa.bilibililiveapiclient.model.PlayUrlData;
import com.muedsa.bilibililiveapiclient.model.Qn;
import com.muedsa.bilibililiveapiclient.model.search.SearchAggregation;
import com.muedsa.bilibililiveapiclient.model.search.SearchResult;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;
import com.muedsa.bilibililivetv.container.GithubApi;
import com.muedsa.github.model.BaseResponse;
import com.muedsa.github.model.GithubReleaseTagInfo;

import io.reactivex.rxjava3.core.Single;

public class RxRequestFactory {
    private static final String TAG = RxRequestFactory.class.getSimpleName();

    public static Single<SearchResult> bilibiliSearchLive(String query) {
        return Single.create(emitter -> {
            BilibiliResponse<SearchAggregation> response = BilibiliLiveApi.client().searchLive(query, 1, 10);
            if(response != null){
                if(response.getCode() == 0){
                    if(response.getData() != null && response.getData().getResult() != null){
                        emitter.onSuccess(response.getData().getResult());
                    }else{
                        emitter.onError(HttpRequestException.create("request result is empty"));
                    }
                }else{
                    emitter.onError(HttpRequestException.create(response.getMessage()));
                }
            }else{
                emitter.onError(HttpRequestException.create("request bilibiliSearchLive error"));
            }
        });
    }

    public static Single<String> bilibiliDanmuToken(long roomId) {
        return Single.create(emitter -> {
            BilibiliResponse<DanmakuInfo> response = BilibiliLiveApi.client().getDanmuInfo(roomId);
            if(response != null){
                if(response.getCode() == 0){
                    if(response.getData() != null
                            && !Strings.isNullOrEmpty(response.getData().getToken())) {
                        emitter.onSuccess(response.getData().getToken());
                    }else{
                        emitter.onError(HttpRequestException.create("request result is empty"));
                    }
                }else if(response.getMessage() != null){
                    emitter.onError(HttpRequestException.create(response.getMessage()));
                }
            }else{
                emitter.onError(HttpRequestException.create("request bilibiliDanmuToken error"));
            }
        });
    }

    public static Single<LargeInfo> bilibiliDanmuRoomInfo(long roomId) {
        return Single.create(emitter -> {
            BilibiliResponse<LargeInfo> response = BilibiliLiveApi.client().getLargeInfo(roomId);
            if(response != null){
                if(response.getCode() == 0){
                    if(response.getData() != null){
                        emitter.onSuccess(response.getData());
                    }else{
                        emitter.onError(HttpRequestException.create("request danmuRoomInfo is empty"));
                    }
                }else if(response.getMessage() != null){
                    emitter.onError(HttpRequestException.create(response.getMessage()));
                }
            }else{
                emitter.onError(HttpRequestException.create("request bilibiliDanmuRoomInfo error"));
            }
        });
    }

    public static Single<PlayUrlData> bilibiliPlayUrlMessage(long roomId) {
        return Single.create(emitter -> {
            BilibiliResponse<PlayUrlData> response = BilibiliLiveApi.client().getPlayUrlMessage(roomId, Qn.RAW);
            if(response != null){
                if(response.getCode() == 0){
                    if(response.getData() != null){
                        emitter.onSuccess(response.getData());
                    }else{
                        emitter.onError(HttpRequestException.create("request playUrlMessage is empty"));
                    }
                }else if(response.getMessage() != null){
                    emitter.onError(HttpRequestException.create(response.getMessage()));
                }
            }else{
                emitter.onError(HttpRequestException.create("request bilibiliPlayUrlMessage error"));
            }
        });
    }

    private static final String GITHUB_USER = "MUedsa";
    private static final String GITHUB_REPO = "BilibiliLiveTV";
    public static Single<GithubReleaseTagInfo> githubLatestRelease() {
        return Single.create(emitter -> {
            BaseResponse<GithubReleaseTagInfo> response = GithubApi.client().getLatestReleaseInfo(GITHUB_USER, GITHUB_REPO);
            if(response != null){
                if(response.getCode() == 0) {
                    emitter.onSuccess(response.getData());
                }else{
                    emitter.onError(HttpRequestException.create(response.getMsg()));
                }
            }else{
                emitter.onError(HttpRequestException.create("request githubLatestRelease error"));
            }
        });
    }
}

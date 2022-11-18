package com.muedsa.bilibililivetv.container;

import com.muedsa.bilibililiveapiclient.BilibiliApiContainer;
import com.muedsa.bilibililiveapiclient.BilibiliLiveApiClient;

public class BilibiliLiveApi {

    private BilibiliLiveApi() {

    }

    private static final class ClientHolder {
        static final BilibiliLiveApiClient client = new BilibiliLiveApiClient();
    }

    public static BilibiliLiveApiClient client() {
        return ClientHolder.client;
    }

    public static void login(String sessData) {
        BilibiliLiveApi.client().putCookie(BilibiliApiContainer.COOKIE_KEY_SESSDATA, sessData);
    }

    public static void logout() {
        BilibiliLiveApi.client().removeCookie(BilibiliApiContainer.COOKIE_KEY_SESSDATA);
    }

    public static final String VIDEO_BV_PREFIX = "BV";
}

package com.muedsa.bilibililivetv.container;

import com.muedsa.bilibililiveapiclient.BilibiliLiveApiClient;
import com.muedsa.httpjsonclient.Container;

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
        BilibiliLiveApi.client().putCookie(Container.COOKIE_KEY_SESSDATA, sessData);
    }

    public static void logout() {
        BilibiliLiveApi.client().removeCookie(Container.COOKIE_KEY_SESSDATA);
    }

    public static final String VIDEO_BV_PREFIX = "BV";
}

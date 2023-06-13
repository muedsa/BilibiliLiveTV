package com.muedsa.bilibililivetv.container;

import com.muedsa.bilibililiveapiclient.BilibiliLiveApiClient;

import java.util.Map;

public class BilibiliLiveApi {

    private BilibiliLiveApi() {

    }

    private static final class ClientHolder {
        static final BilibiliLiveApiClient client = new BilibiliLiveApiClient();
    }

    public static BilibiliLiveApiClient client() {
        return ClientHolder.client;
    }

    public static void login(Map<String,String> cookies) {
        BilibiliLiveApiClient client = BilibiliLiveApi.client();
        cookies.forEach(client::putCookie);
    }

    public static void logout() {
        // todo logout
        BilibiliLiveApi.client().clearCookie();
    }

    public static final String VIDEO_BV_PREFIX = "BV";
}

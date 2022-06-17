package com.muedsa.bilibililivetv.container;

import com.muedsa.bilibililiveapiclient.BilibiliLiveApiClient;

public class BilibiliLiveApi {

    private BilibiliLiveApi() {

    }

    private static final class ClientHolder {
        static final BilibiliLiveApiClient client = new BilibiliLiveApiClient();
    }

    public static BilibiliLiveApiClient client(){
        return ClientHolder.client;
    }
}

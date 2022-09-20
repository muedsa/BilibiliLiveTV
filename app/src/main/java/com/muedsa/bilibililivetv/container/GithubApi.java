package com.muedsa.bilibililivetv.container;

import com.muedsa.github.GithubApiClient;

public class GithubApi {
    public static final String GITHUB_USER = "MUedsa";
    public static final String GITHUB_REPO = "BilibiliLiveTV";

    private GithubApi() {

    }

    private static final class ClientHolder {
        static final GithubApiClient client = new GithubApiClient();
    }

    public static GithubApiClient client(){
        return ClientHolder.client;
    }
}

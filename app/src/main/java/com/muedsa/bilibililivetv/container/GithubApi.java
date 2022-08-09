package com.muedsa.bilibililivetv.container;

import com.muedsa.github.GithubApiClient;

public class GithubApi {

    private GithubApi() {

    }

    private static final class ClientHolder {
        static final GithubApiClient client = new GithubApiClient();
    }

    public static GithubApiClient client(){
        return ClientHolder.client;
    }
}

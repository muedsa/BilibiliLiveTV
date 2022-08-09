package com.muedsa.github;

import com.alibaba.fastjson2.TypeReference;
import com.muedsa.bilibililiveapiclient.uitl.ApiUtil;
import com.muedsa.github.model.BaseResponse;
import com.muedsa.github.model.GithubReleaseTagInfo;
import com.muedsa.httpjsonclient.HttpJsonClient;

import java.io.IOException;

public class GithubApiClient {

    private final HttpJsonClient httpJsonClient;

    public GithubApiClient(){
        httpJsonClient = new HttpJsonClient();
    }

    public BaseResponse<GithubReleaseTagInfo> getLatestReleaseInfo(String user, String repo) throws IOException {
        String url = ApiUtil.fillUrl(GithubApiUrlContainer.LATEST_RELEASE_TAG_INFO, user, repo);
        return httpJsonClient.GetJson(url, new TypeReference<BaseResponse<GithubReleaseTagInfo>>(){});
    }
}

package com.muedsa.bilibililivetv.task;

import com.muedsa.bilibililivetv.container.GithubApi;
import com.muedsa.github.model.BaseResponse;
import com.muedsa.github.model.GithubReleaseTagInfo;

public class RequestGithubLatestReleaseTask implements TaskRunner.Callable {
    private static final String TAG = RequestGithubLatestReleaseTask.class.getSimpleName();
    private static final String GITHUB_USER = "MUedsa";
    private static final String GITHUB_REPO = "BilibiliLiveTV";

    @Override
    public Message call() throws Exception {
        Message msg = Message.obtain();
        BaseResponse<GithubReleaseTagInfo> response = GithubApi.client().getLatestReleaseInfo(GITHUB_USER, GITHUB_REPO);
        if(response != null){
            if(response.getCode() == 0) {
                msg.what = Message.MessageType.SUCCESS;
                msg.obj = response.getData();
            }else{
                msg.what = Message.MessageType.FAIL;
                msg.obj = response.getMsg();
            }
        }
        return msg;
    }
}

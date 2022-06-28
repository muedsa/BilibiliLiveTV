package com.muedsa.bilibililivetv.task;

import android.util.Log;

import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.search.SearchAggregation;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;

public class RequestBilibiliSearchTask implements TaskRunner.Callable {
    private static final String TAG = RequestDanmuInfoTask.class.getSimpleName();

    private final String query;

    public RequestBilibiliSearchTask(String query){
        this.query = query;
    }

    @Override
    public Message call() throws Exception {
        Message msg = Message.obtain();

        BilibiliResponse<SearchAggregation> response = BilibiliLiveApi.client().searchLive(query, 1, 10);
        if(response != null){
            if(response.getCode() == 0){
                if(response.getData() != null && response.getData().getResult() != null){
                    msg.what = Message.MessageType.SUCCESS;
                    msg.obj = response.getData().getResult();
                }
            }else if(response.getMessage() != null){
                Log.e(TAG, "RequestBilibiliSearchTask error message: " + response.getMessage());
                msg.obj = response.getMessage();
            }
        }
        return msg;
    }
}

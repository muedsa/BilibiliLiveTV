package com.muedsa.bilibililivetv.task;

import android.util.Log;

import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.Durl;
import com.muedsa.bilibililiveapiclient.model.PlayUrlData;
import com.muedsa.bilibililiveapiclient.model.Qn;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;

import java.io.IOException;

public class RequestPlayUrlTask implements TaskRunner.Callable {
    private static final String TAG = RequestPlayUrlTask.class.getSimpleName();

    private final long roomId;

    public RequestPlayUrlTask(long roomId){
        this.roomId = roomId;
    }

    @Override
    public Message call() throws IOException {
        Message msg = Message.obtain();
        BilibiliResponse<PlayUrlData> response = BilibiliLiveApi.client().getPlayUrlMessage(roomId, Qn.RAW);
        if(response != null){
            if(response.getCode() == 0){
                if(response.getData() != null
                        && response.getData().getDurl() != null
                        && response.getData().getDurl().size() > 0){
                    msg.what = Message.MessageType.SUCCESS;
                    msg.obj = response.getData().getDurl().stream().map(Durl::getUrl).toArray(String[]::new);
                }
            }else if(response.getMessage() != null){
                Log.e(TAG, "RequestPlayUrlTask error message: " + response.getMessage());
                msg.obj = response.getMessage();
            }
        }
        return msg;
    }
}

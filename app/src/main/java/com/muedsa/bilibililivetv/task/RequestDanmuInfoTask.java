package com.muedsa.bilibililivetv.task;

import android.util.Log;

import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.DanmakuInfo;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;

public class RequestDanmuInfoTask implements TaskRunner.Callable {
    private static final String TAG = RequestDanmuInfoTask.class.getSimpleName();

    private final long roomId;

    public RequestDanmuInfoTask(long roomId){
        this.roomId = roomId;
    }

    @Override
    public Message call() throws Exception {
        Message msg = Message.obtain();

        BilibiliResponse<DanmakuInfo> response = BilibiliLiveApi.client().getDanmuInfo(roomId);
        if(response != null){
            if(response.getCode() == 0){
                if(response.getData() != null){
                    msg.what = Message.MessageType.SUCCESS;
                    msg.obj = response.getData().getToken();
                }
            }else if(response.getMessage() != null){
                Log.e(TAG, "RequestDanmuInfoTask error message: " + response.getMessage());
                msg.obj = response.getMessage();
            }
        }
        return msg;
    }
}

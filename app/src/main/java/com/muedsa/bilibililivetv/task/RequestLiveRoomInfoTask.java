package com.muedsa.bilibililivetv.task;


import android.util.Log;

import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.LargeInfo;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;

import java.io.IOException;

public class RequestLiveRoomInfoTask implements TaskRunner.Callable {
    private static final String TAG = RequestLiveRoomInfoTask.class.getSimpleName();

    private final long roomId;

    public RequestLiveRoomInfoTask(long roomId){
        this.roomId = roomId;
    }

    @Override
    public Message call() throws IOException {
        Message msg = Message.obtain();
        BilibiliResponse<LargeInfo> response = BilibiliLiveApi.client().getLargeInfo(roomId);
        if(response != null){
            if(response.getCode() == 0){
                if(response.getData() != null){
                    msg.what = Message.MessageType.SUCCESS;
                    msg.obj = response.getData();
                }
            }else if(response.getMessage() != null){
                Log.e(TAG, "RequestLiveRoomInfoTask error message: " + response.getMessage());
                msg.obj = response.getMessage();
            }
        }
        return msg;
    }
}

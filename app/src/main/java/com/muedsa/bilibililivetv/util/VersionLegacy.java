package com.muedsa.bilibililivetv.util;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson2.JSON;
import com.muedsa.bilibililivetv.room.dao.LiveRoomDao;
import com.muedsa.bilibililivetv.room.model.LiveRoom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class VersionLegacy {
    private static final String TAG = VersionLegacy.class.getSimpleName();

    private static final String ROOM_LIVE_HISTORY_FILENAME = "liveRoomHistory.json";

    public static void roomLiveHistory(Context context, LiveRoomDao liveRoomDao) {
        try {
            File file = new File(context.getFilesDir(), ROOM_LIVE_HISTORY_FILENAME);
            if(file.exists()){
                Log.d(TAG, "roomLiveHistory, file exists:" + file.getAbsolutePath());
                FileInputStream fileInputStream = context.openFileInput(file.getName());
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
                Log.d(TAG, "roomLiveHistory, file content:" + stringBuilder);
                List<LiveRoom> liveRoomList = JSON.parseArray(stringBuilder.toString(), LiveRoom.class);
                liveRoomDao.insertAll(liveRoomList).subscribe();
                context.deleteFile(file.getName());
                Log.d(TAG, "roomLiveHistory, file delete");
            }
        }
        catch (IOException e){
            Log.d(TAG, "roomLiveHistory: ", e);
        }
    }
}

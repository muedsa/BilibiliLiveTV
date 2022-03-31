package com.muedsa.bilibililivetv.model;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LiveRoomHistoryHolder {
    private static final List<LiveRoom> list = new ArrayList<>();
    private static final String filename = "liveRoomHistory.json";

    public static List<LiveRoom> getList() {
        return list;
    }

    public static void addHistory(LiveRoom liveRoom, Context context){
        removeHistory(liveRoom);
        list.add(0, liveRoom);
        saveToFile(context);
    }

    public static void removeHistory(LiveRoom liveRoom){
        Optional<LiveRoom> first = list.stream().filter(i -> {
            boolean flag = liveRoom.getId() == i.getId();
            if(liveRoom.getShortId() != null){
                flag = flag || i.getId() == liveRoom.getShortId();
            }
            return flag;
        }).findFirst();
        first.ifPresent(list::remove);
    }

    public static boolean saveToFile(Context context) {
        boolean flag = false;
        try {
            File file = new File(context.getFilesDir(), filename);
            FileOutputStream fileOutputStream = context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
            fileOutputStream.write(JSON.toJSONBytes(list));
            fileOutputStream.flush();
            fileOutputStream.close();
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean loadFormFile(Context context) {
        boolean flag = false;
        try {
            File file = new File(context.getFilesDir(), filename);
            if(file.exists()){
                FileInputStream fileInputStream = context.openFileInput(file.getName());
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
                list.clear();
                list.addAll(JSON.parseArray(stringBuilder.toString(), LiveRoom.class));
            }
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean clearHistory(Context context){
        boolean flag = false;
        try {
            list.clear();
            File file = new File(context.getFilesDir(), filename);
            context.deleteFile(file.getName());

            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}

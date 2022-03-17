package com.muedsa.bilibililiveapiclient;

public class ApiUrlContainer {
    public static String ROOM_PLAY_URL = "https://api.live.bilibili.com/room/v1/Room/playUrl?cid=%d&qn=%d&platform=web";

    public static String GET_ROOM_LIST = "https://api.live.bilibili.com/room/v3/area/getRoomList?page=%d&page_size=%d&parent_area_id=%d&sort_type=%s";

    public static String GET_ROOM_INFO = "https://api.live.bilibili.com/room/v1/Room/get_info";

    public static String fillUrl(String url, Object... args){
        return String.format(url, args);
    }
}

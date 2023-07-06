package com.muedsa.bilibililiveapiclient.model.live;

import java.util.List;

public class UserWebListResult {

    private List<LiveRoomInfo> rooms;

    private List<LiveRoomInfo> list;

    private int count;

    public List<LiveRoomInfo> getRooms() {
        return rooms;
    }

    public void setRooms(List<LiveRoomInfo> rooms) {
        this.rooms = rooms;
    }

    public List<LiveRoomInfo> getList() {
        return list;
    }

    public void setList(List<LiveRoomInfo> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

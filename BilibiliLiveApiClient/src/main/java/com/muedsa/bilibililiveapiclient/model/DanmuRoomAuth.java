package com.muedsa.bilibililiveapiclient.model;

import com.alibaba.fastjson.annotation.JSONField;

public class DanmuRoomAuth {
    @JSONField(name = "uid")
    private Long uid;

    @JSONField(name = "roomid")
    private Long roomId;

    @JSONField(name = "protover")
    private Integer protoVersion;

    @JSONField(name = "platform")
    private String platform;

    @JSONField(name = "type")
    private Integer type;

    @JSONField(name = "key")
    private String key;

    public DanmuRoomAuth(){

    }

    public DanmuRoomAuth(Long roomId, String key) {
        this.roomId = roomId;
        this.key = key;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Integer getProtoVersion() {
        return protoVersion;
    }

    public void setProtoVersion(Integer protoVersion) {
        this.protoVersion = protoVersion;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

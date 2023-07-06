package com.muedsa.bilibililiveapiclient.model.live;

import com.alibaba.fastjson2.annotation.JSONField;

public class LiveRoomInfo {

    @JSONField(name = "roomid", alternateNames = {"room_id"})
    private Long roomId;

    @JSONField(name = "uid")
    private Long uid;

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "uname", alternateNames = {"nickname"})
    private String uname;

    @JSONField(name = "online")
    private Long online;

    @JSONField(name = "user_cover", alternateNames = {"cover_from_user"})
    private String userCover;

    @JSONField(name = "user_cover_flag")
    private Integer userCoverFlag;

    @JSONField(name = "system_cover", alternateNames = {"keyframe"})
    private String systemCover;

    @JSONField(name = "cover")
    private String cover;

    @JSONField(name = "show_cover")
    private String showCover;

    @JSONField(name = "link")
    private String link;

    @JSONField(name = "face")
    private String face;

    @JSONField(name = "parent_id")
    private Integer parentId;

    @JSONField(name = "parent_name")
    private String parentName;

    @JSONField(name = "area_id")
    private Integer areaId;

    @JSONField(name = "area_name")
    private String areaName;

    @JSONField(name = "area_v2_parent_id")
    private Integer areaV2ParentId;

    @JSONField(name = "area_v2_parent_name")
    private String areaV2ParentName;

    @JSONField(name = "area_v2_id")
    private Integer areaV2Id;

    @JSONField(name = "area_v2_name")
    private String areaV2Name;

    @JSONField(name = "web_pendent")
    private String webPendent;

    @JSONField(name = "pk_id")
    private Integer pkId;


    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Long getOnline() {
        return online;
    }

    public void setOnline(Long online) {
        this.online = online;
    }

    public String getUserCover() {
        return userCover;
    }

    public void setUserCover(String userCover) {
        this.userCover = userCover;
    }

    public Integer getUserCoverFlag() {
        return userCoverFlag;
    }

    public void setUserCoverFlag(Integer userCoverFlag) {
        this.userCoverFlag = userCoverFlag;
    }

    public String getSystemCover() {
        return systemCover;
    }

    public void setSystemCover(String systemCover) {
        this.systemCover = systemCover;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getShowCover() {
        return showCover;
    }

    public void setShowCover(String showCover) {
        this.showCover = showCover;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getAreaV2ParentId() {
        return areaV2ParentId;
    }

    public void setAreaV2ParentId(Integer areaV2ParentId) {
        this.areaV2ParentId = areaV2ParentId;
    }

    public String getAreaV2ParentName() {
        return areaV2ParentName;
    }

    public void setAreaV2ParentName(String areaV2ParentName) {
        this.areaV2ParentName = areaV2ParentName;
    }

    public Integer getAreaV2Id() {
        return areaV2Id;
    }

    public void setAreaV2Id(Integer areaV2Id) {
        this.areaV2Id = areaV2Id;
    }

    public String getAreaV2Name() {
        return areaV2Name;
    }

    public void setAreaV2Name(String areaV2Name) {
        this.areaV2Name = areaV2Name;
    }

    public String getWebPendent() {
        return webPendent;
    }

    public void setWebPendent(String webPendent) {
        this.webPendent = webPendent;
    }

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }
}

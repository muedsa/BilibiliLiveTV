package com.muedsa.bilibililiveapiclient.model.dynamic;

import com.alibaba.fastjson2.annotation.JSONField;

public class DynamicBasic {

    @JSONField(name = "comment_id_str")
    private String commentIdStr;

    @JSONField(name = "comment_type")
    private Integer commentType;

    @JSONField(name = "jump_url")
    private String jump_url;

//    @JSONField(name = "like_icon")
//    private Object like_icon;

    @JSONField(name = "rid_str")
    private String ridStr;

    public String getCommentIdStr() {
        return commentIdStr;
    }

    public void setCommentIdStr(String commentIdStr) {
        this.commentIdStr = commentIdStr;
    }

    public Integer getCommentType() {
        return commentType;
    }

    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }

    public String getJump_url() {
        return jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = jump_url;
    }

    public String getRidStr() {
        return ridStr;
    }

    public void setRidStr(String ridStr) {
        this.ridStr = ridStr;
    }
}

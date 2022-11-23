package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

import java.io.Serializable;

public class VideoSubtitleAuthor implements Serializable {

    @JSONField(name = "mid")
    private Long mid;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "sex")
    private String sex;

    @JSONField(name = "face")
    private String face;

    @JSONField(name = "sign")
    private String sign;

    @JSONField(name = "rank")
    private Integer rank;

    @JSONField(name = "birthday")
    private Long birthday;

    @JSONField(name = "is_fake_account")
    private Integer isFakeAccount;

    @JSONField(name = "is_deleted")
    private Integer isDeleted;

    @JSONField(name = "in_reg_audit")
    private Integer inRegAudit;

    @JSONField(name = "is_senior_member")
    private Integer isSeniorMember;

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Integer getIsFakeAccount() {
        return isFakeAccount;
    }

    public void setIsFakeAccount(Integer isFakeAccount) {
        this.isFakeAccount = isFakeAccount;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getInRegAudit() {
        return inRegAudit;
    }

    public void setInRegAudit(Integer inRegAudit) {
        this.inRegAudit = inRegAudit;
    }

    public Integer getIsSeniorMember() {
        return isSeniorMember;
    }

    public void setIsSeniorMember(Integer isSeniorMember) {
        this.isSeniorMember = isSeniorMember;
    }
}

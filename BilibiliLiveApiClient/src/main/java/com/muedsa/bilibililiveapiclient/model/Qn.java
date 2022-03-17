package com.muedsa.bilibililiveapiclient.model;

public enum Qn {
    VIP_4k(40000),
    RAW(10000),
    BD_DOLBY(401),
    BD(400),
    UHD(250),
    HD(150),
    SD(80)
    ;

    private final Long code;

    Qn(long code){
        this.code = code;
    }

    public Long getCode() {
        return code;
    }
}
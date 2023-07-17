package com.muedsa.bilibililiveapiclient.model.dynamic;

import com.alibaba.fastjson2.annotation.JSONField;

public class ModuleDynamic {

//    @JSONField(name = "additional")
//    private Object additional;


//    @JSONField(name = "desc")
//    private Object desc;

    @JSONField(name = "major")
    private DynamicMajor major;

//    @JSONField(name = "topic")
//    private Object topic;


    public DynamicMajor getMajor() {
        return major;
    }

    public void setMajor(DynamicMajor major) {
        this.major = major;
    }
}

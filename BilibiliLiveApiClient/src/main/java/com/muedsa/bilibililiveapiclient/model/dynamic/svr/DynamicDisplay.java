package com.muedsa.bilibililiveapiclient.model.dynamic.svr;

import com.alibaba.fastjson2.annotation.JSONField;

public class DynamicDisplay {

    @JSONField(name = "usr_action_txt")
    private String userActionText;

    public String getUserActionText() {
        return userActionText;
    }

    public void setUserActionText(String userActionText) {
        this.userActionText = userActionText;
    }
}

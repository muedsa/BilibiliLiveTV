package com.muedsa.bilibililiveapiclient.model.chat;

import com.alibaba.fastjson2.annotation.JSONField;

public class ChatBroadcast {
    public static final String CMD_DANMU_MSG = "DANMU_MSG";



    @JSONField(name = "cmd")
    private String cmd;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}

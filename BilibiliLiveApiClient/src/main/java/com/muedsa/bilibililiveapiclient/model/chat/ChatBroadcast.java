package com.muedsa.bilibililiveapiclient.model.chat;

import com.alibaba.fastjson2.annotation.JSONField;

public class ChatBroadcast {
    public static final String CMD_DANMU_MSG = "DANMU_MSG";
    public static final String CMD_SUPER_CHAT_MESSAGE = "SUPER_CHAT_MESSAGE";
    public static final String CMD_SEND_GIFT = "SEND_GIFT";

    @JSONField(name = "cmd")
    private String cmd;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}

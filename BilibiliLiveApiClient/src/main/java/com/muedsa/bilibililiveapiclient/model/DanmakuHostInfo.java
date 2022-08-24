package com.muedsa.bilibililiveapiclient.model;

import com.alibaba.fastjson2.annotation.JSONField;

public class DanmakuHostInfo {
    @JSONField(name = "host")
    private String host;

    @JSONField(name = "port")
    private Integer port;

    @JSONField(name = "wss_port")
    private Integer wssPort;

    @JSONField(name = "ws_port")
    private Integer wsPort;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getWssPort() {
        return wssPort;
    }

    public void setWssPort(Integer wssPort) {
        this.wssPort = wssPort;
    }

    public Integer getWsPort() {
        return wsPort;
    }

    public void setWsPort(Integer wsPort) {
        this.wsPort = wsPort;
    }
}

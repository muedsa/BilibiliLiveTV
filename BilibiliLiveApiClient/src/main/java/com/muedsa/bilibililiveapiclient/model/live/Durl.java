package com.muedsa.bilibililiveapiclient.model.live;

public class Durl {
    private String url;
    private Long length;
    private Integer order;
    private Integer streamType;
    private Integer p2pType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getStreamType() {
        return streamType;
    }

    public void setStreamType(Integer streamType) {
        this.streamType = streamType;
    }

    public Integer getP2pType() {
        return p2pType;
    }

    public void setP2pType(Integer p2pType) {
        this.p2pType = p2pType;
    }
}

package com.muedsa.bilibililiveapiclient.model;

import com.alibaba.fastjson2.annotation.JSONField;

import java.net.URI;

public class WbiImg {
    @JSONField(name = "img_url")
    private String imgUrl;

    @JSONField(name = "sub_url")
    private String subUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSubUrl() {
        return subUrl;
    }

    public void setSubUrl(String subUrl) {
        this.subUrl = subUrl;
    }

    public String getImgKey() {
        return parseKey(imgUrl);
    }

    public String getSubKey() {
        return parseKey(subUrl);
    }

    public static String parseKey(String url) {
        URI uri = URI.create(url);
        String[] splitPaths = uri.getPath().split("/");
        String endPath = splitPaths[splitPaths.length - 1];
        if (endPath.endsWith(".png")) {
            return endPath.substring(0, endPath.length() - 4);
        }
        throw new IllegalArgumentException("not wbi url");
    }
}

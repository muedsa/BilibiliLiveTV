package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

public class VideoDimension {

    @JSONField(name = "width")
    private Integer width;

    @JSONField(name = "height")
    private Integer height;

    @JSONField(name = "rotate")
    private Integer rotate;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getRotate() {
        return rotate;
    }

    public void setRotate(Integer rotate) {
        this.rotate = rotate;
    }
}

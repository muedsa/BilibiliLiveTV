package com.muedsa.bilibililivetv.model;

import com.alibaba.fastjson2.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public class BilibiliSubtitleInfo implements Serializable {
    @JSONField(name = "font_size")
    private float fontSize;

    @JSONField(name = "font_color")
    private String fontColor;

    @JSONField(name = "background_alpha")
    private float backgroundAlpha;

    @JSONField(name = "background_color")
    private String backgroundColor;

    @JSONField(name = "Stroke")
    private String stroke;

    @JSONField(name = "body")
    private List<BilibiliSubtitle> body;

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public float getBackgroundAlpha() {
        return backgroundAlpha;
    }

    public void setBackgroundAlpha(float backgroundAlpha) {
        this.backgroundAlpha = backgroundAlpha;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public List<BilibiliSubtitle> getBody() {
        return body;
    }

    public void setBody(List<BilibiliSubtitle> body) {
        this.body = body;
    }
}

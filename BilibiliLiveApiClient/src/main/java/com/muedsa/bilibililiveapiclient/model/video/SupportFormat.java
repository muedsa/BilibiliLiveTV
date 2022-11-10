package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

public class SupportFormat {

    @JSONField(name = "quality")
    private Integer quality;

    @JSONField(name = "format")
    private String format;

    @JSONField(name = "new_description")
    private String newDescription;

    @JSONField(name = "display_desc")
    private String displayDesc;

    @JSONField(name = "superscript")
    private String superScript;

    @JSONField(name = "codecs")
    private List<String> codecs;

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }

    public String getDisplayDesc() {
        return displayDesc;
    }

    public void setDisplayDesc(String displayDesc) {
        this.displayDesc = displayDesc;
    }

    public String getSuperScript() {
        return superScript;
    }

    public void setSuperScript(String superScript) {
        this.superScript = superScript;
    }

    public List<String> getCodecs() {
        return codecs;
    }

    public void setCodecs(List<String> codecs) {
        this.codecs = codecs;
    }
}

package com.muedsa.bilibililiveapiclient.model.video;

import com.alibaba.fastjson2.annotation.JSONField;

public class SegmentBase {
    @JSONField(name = "initialization", alternateNames = {"Initialization"})
    private String initialization;

    @JSONField(name = "indexRange", alternateNames = {"IndexRange", "index_range"})
    private String indexRange;

    public String getInitialization() {
        return initialization;
    }

    public void setInitialization(String initialization) {
        this.initialization = initialization;
    }

    public String getIndexRange() {
        return indexRange;
    }

    public void setIndexRange(String indexRange) {
        this.indexRange = indexRange;
    }
}

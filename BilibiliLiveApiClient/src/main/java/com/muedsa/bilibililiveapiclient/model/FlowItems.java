package com.muedsa.bilibililiveapiclient.model;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

public class FlowItems<T> {
    @JSONField(name = "has_more")
    private boolean hasMore;

    @JSONField(name = "items")
    private List<T> items;

    @JSONField(name = "offset")
    private String offset;

    @JSONField(name = "update_baseline")
    private String updateBaseline;

    @JSONField(name = "update_num")
    private int updateNum;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getUpdateBaseline() {
        return updateBaseline;
    }

    public void setUpdateBaseline(String updateBaseline) {
        this.updateBaseline = updateBaseline;
    }

    public int getUpdateNum() {
        return updateNum;
    }

    public void setUpdateNum(int updateNum) {
        this.updateNum = updateNum;
    }
}

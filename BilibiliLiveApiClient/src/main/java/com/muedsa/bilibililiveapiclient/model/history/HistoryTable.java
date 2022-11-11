package com.muedsa.bilibililiveapiclient.model.history;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

public class HistoryTable {

    @JSONField(name = "cursor")
    private HistoryCursor cursor;

    @JSONField(name = "tab")
    private List<HistoryTab> tab;

    @JSONField(name = "list")
    private List<HistoryRecord> list;

    public HistoryCursor getCursor() {
        return cursor;
    }

    public void setCursor(HistoryCursor cursor) {
        this.cursor = cursor;
    }

    public List<HistoryTab> getTab() {
        return tab;
    }

    public void setTab(List<HistoryTab> tab) {
        this.tab = tab;
    }

    public List<HistoryRecord> getList() {
        return list;
    }

    public void setList(List<HistoryRecord> list) {
        this.list = list;
    }
}

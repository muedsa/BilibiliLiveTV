package com.muedsa.bilibililiveapiclient.model.space;

import com.alibaba.fastjson2.annotation.JSONField;
import com.muedsa.bilibililiveapiclient.model.search.SearchVideoInfo;

import java.util.List;

public class SpaceUpList {

    @JSONField(name = "vlist")
    private List<SearchVideoInfo> vlist;

    public List<SearchVideoInfo> getVlist() {
        return vlist;
    }

    public void setVlist(List<SearchVideoInfo> vlist) {
        this.vlist = vlist;
    }
}

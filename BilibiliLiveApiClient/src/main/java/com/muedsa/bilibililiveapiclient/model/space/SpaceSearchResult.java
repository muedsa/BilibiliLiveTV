package com.muedsa.bilibililiveapiclient.model.space;

import com.alibaba.fastjson2.annotation.JSONField;

public class SpaceSearchResult {

    @JSONField(name = "list")
    private SpaceUpList list;

    @JSONField(name = "page")
    private SpacePageInfo page;

    @JSONField(name = "is_risk")
    private Boolean isRisk;

    public SpaceUpList getList() {
        return list;
    }

    public void setList(SpaceUpList list) {
        this.list = list;
    }

    public SpacePageInfo getPage() {
        return page;
    }

    public void setPage(SpacePageInfo page) {
        this.page = page;
    }

    public Boolean getRisk() {
        return isRisk;
    }

    public void setRisk(Boolean risk) {
        isRisk = risk;
    }
}

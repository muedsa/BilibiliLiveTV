package com.muedsa.bilibililiveapiclient.model.dynamic.svr;

import java.util.List;

public class DynamicAttentions {

    private List<BangumiAttention> bangumis;

    private List<Long> uids;

    public List<BangumiAttention> getBangumis() {
        return bangumis;
    }

    public void setBangumis(List<BangumiAttention> bangumis) {
        this.bangumis = bangumis;
    }

    public List<Long> getUids() {
        return uids;
    }

    public void setUids(List<Long> uids) {
        this.uids = uids;
    }
}

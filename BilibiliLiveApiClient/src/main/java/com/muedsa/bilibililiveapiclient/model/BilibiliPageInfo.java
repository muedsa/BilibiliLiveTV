package com.muedsa.bilibililiveapiclient.model;

import java.util.List;

public class BilibiliPageInfo<T> {

    private Integer count;

    private List<T> list;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}

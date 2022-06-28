package com.muedsa.bilibililiveapiclient.model.search;

import com.alibaba.fastjson.annotation.JSONField;

public class PageInfo {

    @JSONField(name = "numPages")
    private Integer numPages;

    @JSONField(name = "numResults")
    private Integer numResults;

    @JSONField(name = "total")
    private Integer total;

    @JSONField(name = "pages")
    private Integer pages;

    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }

    public Integer getNumResults() {
        return numResults;
    }

    public void setNumResults(Integer numResults) {
        this.numResults = numResults;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}

package com.muedsa.bilibililiveapiclient.model.search;

import com.alibaba.fastjson2.annotation.JSONField;

public class SearchAggregation {

    @JSONField(name = "seid")
    private String seId;

    @JSONField(name = "page")
    private Integer page;

    @JSONField(name = "pagesize")
    private Integer pageSize;

    @JSONField(name = "numResults")
    private Integer numResults;

    @JSONField(name = "numPages")
    private Integer numPages;

    @JSONField(name = "suggest_keyword")
    private String suggestKeyword;

    @JSONField(name = "rqt_type")
    private String rqtType;

    @JSONField(name = "pageinfo")
    private PageInfoAggregation pageInfo;

    @JSONField(name = "result")
    private SearchResult result;

    @JSONField(name = "show_column")
    private Integer showColumn;

    @JSONField(name = "inWhiteKey")
    private Integer inBlackKey;

    @JSONField(name = "in_white_key")
    private Integer inWhiteKey;

    public String getSeId() {
        return seId;
    }

    public void setSeId(String seId) {
        this.seId = seId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getNumResults() {
        return numResults;
    }

    public void setNumResults(Integer numResults) {
        this.numResults = numResults;
    }

    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }

    public String getSuggestKeyword() {
        return suggestKeyword;
    }

    public void setSuggestKeyword(String suggestKeyword) {
        this.suggestKeyword = suggestKeyword;
    }

    public String getRqtType() {
        return rqtType;
    }

    public void setRqtType(String rqtType) {
        this.rqtType = rqtType;
    }

    public PageInfoAggregation getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoAggregation pageInfo) {
        this.pageInfo = pageInfo;
    }

    public SearchResult getResult() {
        return result;
    }

    public void setResult(SearchResult result) {
        this.result = result;
    }

    public Integer getShowColumn() {
        return showColumn;
    }

    public void setShowColumn(Integer showColumn) {
        this.showColumn = showColumn;
    }

    public Integer getInBlackKey() {
        return inBlackKey;
    }

    public void setInBlackKey(Integer inBlackKey) {
        this.inBlackKey = inBlackKey;
    }

    public Integer getInWhiteKey() {
        return inWhiteKey;
    }

    public void setInWhiteKey(Integer inWhiteKey) {
        this.inWhiteKey = inWhiteKey;
    }
}

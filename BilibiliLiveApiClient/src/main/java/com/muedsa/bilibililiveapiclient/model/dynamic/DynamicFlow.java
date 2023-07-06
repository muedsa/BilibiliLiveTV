package com.muedsa.bilibililiveapiclient.model.dynamic;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.List;

public class DynamicFlow {

    @JSONField(name = "new_num")
    private Integer newNum;

    @JSONField(name = "update_num")
    private Integer updateNum;

    @JSONField(name = "exist_gap")
    private Integer existGap;

    @JSONField(name = "open_rcmd")
    private Integer openRcmd;

    @JSONField(name = "attentions")
    private DynamicAttentions attentions;

    @JSONField(name = "cards")
    private List<DynamicCard> cards;

    @JSONField(name = "max_dynamic_id")
    private Long maxDynamicId;

    @JSONField(name = "history_offset")
    private Long historyOffset;

    @JSONField(name = "_gt_")
    private Integer gt;

    public Integer getNewNum() {
        return newNum;
    }

    public void setNewNum(Integer newNum) {
        this.newNum = newNum;
    }

    public Integer getUpdateNum() {
        return updateNum;
    }

    public void setUpdateNum(Integer updateNum) {
        this.updateNum = updateNum;
    }

    public Integer getExistGap() {
        return existGap;
    }

    public void setExistGap(Integer existGap) {
        this.existGap = existGap;
    }

    public Integer getOpenRcmd() {
        return openRcmd;
    }

    public void setOpenRcmd(Integer openRcmd) {
        this.openRcmd = openRcmd;
    }

    public DynamicAttentions getAttentions() {
        return attentions;
    }

    public void setAttentions(DynamicAttentions attentions) {
        this.attentions = attentions;
    }

    public List<DynamicCard> getCards() {
        return cards;
    }

    public void setCards(List<DynamicCard> cards) {
        this.cards = cards;
    }

    public Long getMaxDynamicId() {
        return maxDynamicId;
    }

    public void setMaxDynamicId(Long maxDynamicId) {
        this.maxDynamicId = maxDynamicId;
    }

    public Long getHistoryOffset() {
        return historyOffset;
    }

    public void setHistoryOffset(Long historyOffset) {
        this.historyOffset = historyOffset;
    }

    public Integer getGt() {
        return gt;
    }

    public void setGt(Integer gt) {
        this.gt = gt;
    }
}

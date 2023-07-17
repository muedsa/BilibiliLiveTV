package com.muedsa.bilibililiveapiclient.model.dynamic;

import com.alibaba.fastjson2.annotation.JSONField;

public class DynamicItem {

    @JSONField(name = "basic")
    private DynamicBasic basic;

    @JSONField(name = "id_str")
    private String idStr;

    @JSONField(name = "modules")
    private DynamicModules modules;

    @JSONField(name = "type")
    private String type;

    @JSONField(name = "visible")
    private Boolean visible;

    public DynamicBasic getBasic() {
        return basic;
    }

    public void setBasic(DynamicBasic basic) {
        this.basic = basic;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public DynamicModules getModules() {
        return modules;
    }

    public void setModules(DynamicModules modules) {
        this.modules = modules;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}

package com.muedsa.bilibililiveapiclient.model.dynamic;

import com.alibaba.fastjson2.annotation.JSONField;

public class DynamicModules {

    @JSONField(name = "module_author")
    private ModuleAuthor moduleAuthor;

    @JSONField(name = "module_dynamic")
    private ModuleDynamic moduleDynamic;

//    @JSONField(name = "module_more")
//    private Object moduleMore;

//    @JSONField(name = "module_stat")
//    private Object moduleStat;


    public ModuleAuthor getModuleAuthor() {
        return moduleAuthor;
    }

    public void setModuleAuthor(ModuleAuthor moduleAuthor) {
        this.moduleAuthor = moduleAuthor;
    }

    public ModuleDynamic getModuleDynamic() {
        return moduleDynamic;
    }

    public void setModuleDynamic(ModuleDynamic moduleDynamic) {
        this.moduleDynamic = moduleDynamic;
    }
}

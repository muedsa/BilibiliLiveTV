package com.muedsa.bilibililivetv.room.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public abstract class BaseModel {

    @ColumnInfo(name = "update_at")
    private long updateAt;

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }
}

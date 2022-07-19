package com.muedsa.bilibililivetv.room;

import androidx.room.TypeConverter;


public class TypeConvertor {

    public static final String STR_SPLIT = ",";

    @TypeConverter
    public String[] ConvertStringToArray(String value) {
        return value == null ? null : value.split(STR_SPLIT);
    }

    @TypeConverter
    public String ConvertArrayToString(String[] arr) {
        return arr == null ? null : String.join(STR_SPLIT, arr);
    }
}

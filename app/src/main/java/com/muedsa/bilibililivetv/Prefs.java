package com.muedsa.bilibililivetv;

import android.content.Context;
import android.content.SharedPreferences;

import com.muedsa.bilibililiveapiclient.BilibiliApiContainer;
import com.muedsa.bilibililivetv.player.DefaultDanmakuContext;

import java.util.Objects;


public class Prefs {
    public static DefaultValuePref<String> SESS_DATA = new DefaultValuePref<>(BilibiliApiContainer.COOKIE_KEY_SESSDATA, "");
    public static DefaultValuePref<Float> DANMAKU_SCALE_TEXT_SIZE = new DefaultValuePref<>("SCALE_TEXT_SIZE", DefaultDanmakuContext.DEFAULT_SCALE_TEXT_SIZE);
    public static DefaultValuePref<Float> DANMAKU_TRANSPARENCY = new DefaultValuePref<>("DANMAKU_TRANSPARENCY", DefaultDanmakuContext.DEFAULT_DANMAKU_TRANSPARENCY);
    public static DefaultValuePref<Integer> DANMAKU_MARGIN = new DefaultValuePref<>("DANMAKU_MARGIN",DefaultDanmakuContext.DEFAULT_DANMAKU_MARGIN);

    public static final String SP_NAME = "BILIBILI_LIVE_TV";
    public static SharedPreferences SHARED_PREFERENCES;

    public static synchronized void init(Context context){
        if(Objects.isNull(SHARED_PREFERENCES)){
            SHARED_PREFERENCES = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
    }

    private static void checkInit(){
        if(Objects.isNull(SHARED_PREFERENCES)){
            throw new IllegalStateException("shared preferences not init");
        }
    }

    public static String getString(Pref pref){
        checkInit();
        return SHARED_PREFERENCES.getString(pref.key, null);
    }

    public static String getString(DefaultValuePref<String> pref){
        checkInit();
        return SHARED_PREFERENCES.getString(pref.key, pref.getValue());
    }

    public static int getInt(Pref pref){
        checkInit();
        return SHARED_PREFERENCES.getInt(pref.key, 0);
    }

    public static int getInt(DefaultValuePref<Integer> pref){
        checkInit();
        return SHARED_PREFERENCES.getInt(pref.key, pref.getValue());
    }

    public static float getFloat(Pref pref){
        checkInit();
        return SHARED_PREFERENCES.getFloat(pref.key, 0);
    }

    public static float getFloat(DefaultValuePref<Float> pref){
        checkInit();
        return SHARED_PREFERENCES.getFloat(pref.key, pref.getValue());
    }

    public static void putString(Pref pref, String value){
        checkInit();
        SHARED_PREFERENCES.edit().putString(pref.key, value).apply();
    }

    public static void putInt(Pref pref, int value){
        checkInit();
        SHARED_PREFERENCES.edit().putInt(pref.key, value).apply();
    }

    public static void putFloat(Pref pref, float value){
        checkInit();
        SHARED_PREFERENCES.edit().putFloat(pref.key, value).apply();
    }

    public static void remove(Pref pref){
        checkInit();
        SHARED_PREFERENCES.edit().remove(pref.key).apply();
    }

    public static class Pref {
        public final String key;
        public Pref(String key) {
            this.key = key;
        }
    }

    public static class DefaultValuePref<T> extends Pref {
        public final T value;

        public DefaultValuePref(String key, T value) {
            super(key);
            if(Objects.isNull(value)){
                throw new IllegalArgumentException("default value not be null");
            }
            this.value = value;
        }

        public T getValue() {
            return value;
        }
    }

    private Prefs(){}
}

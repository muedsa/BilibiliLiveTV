package com.muedsa.bilibililivetv.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.muedsa.bilibililiveapiclient.BilibiliApiContainer;

import java.util.Objects;


public class Prefs {
    public static DefaultValuePref<String> BILIBILI_COOKIE_JSON =  new DefaultValuePref<>("BILIBILI_COOKIE_JSON", "{}");

    public static DefaultValuePref<String> BILIBILI_REFRESH_TOKEN =  new DefaultValuePref<>("BILIBILI_REFRESH_TOKEN", "");

    public static DefaultValuePref<String> SESS_DATA = new DefaultValuePref<>(BilibiliApiContainer.COOKIE_KEY_SESSDATA, "");

    public static DefaultValuePref<String> BILIBILI_WBI_KEY =  new DefaultValuePref<>("BILIBILI_WBI_KEY", "");
    public static DefaultValuePref<Long> BILIBILI_WBI_KEY_TIME =  new DefaultValuePref<>("BILIBILI_WBI_KEY_TIME", 0L);
    public static DefaultValuePref<Integer> DANMAKU_SCALE_TEXT_SIZE = new DefaultValuePref<>("SCALE_TEXT_SIZE", 120);
    public static DefaultValuePref<Integer> DANMAKU_TRANSPARENCY = new DefaultValuePref<>("DANMAKU_TRANSPARENCY", 85);
    public static DefaultValuePref<Integer> DANMAKU_MARGIN = new DefaultValuePref<>("DANMAKU_MARGIN", 5);
    public static DefaultValuePref<Integer> DANMAKU_MAX_LINES_SCROLL = new DefaultValuePref<>("DANMAKU_MAX_LINES_SCROLL", 5);
    public static DefaultValuePref<Integer> DANMAKU_MAX_LINES_FIX_TOP = new DefaultValuePref<>("DANMAKU_MAX_LINES_FIX_TOP", 5);
    public static DefaultValuePref<Integer> DANMAKU_MAX_LINES_FIX_BOTTOM = new DefaultValuePref<>("DANMAKU_MAX_LINES_FIX_BOTTOM", 8);
    public static DefaultValuePref<Boolean> DANMAKU_OVERLAPPING_ENABLE_SCROLL = new DefaultValuePref<>("DANMAKU_OVERLAPPING_ENABLE_SCROLL", true);
    public static DefaultValuePref<Boolean> DANMAKU_OVERLAPPING_ENABLE_FIX_TOP = new DefaultValuePref<>("DANMAKU_OVERLAPPING_ENABLE_FIX_TOP", true);
    public static DefaultValuePref<Boolean> DANMAKU_OVERLAPPING_ENABLE_FIX_BOTTOM = new DefaultValuePref<>("DANMAKU_OVERLAPPING_ENABLE_FIX_BOTTOM", true);

    public static SharedPreferences SHARED_PREFERENCES;

    public static synchronized void init(Context context){
        if(Objects.isNull(SHARED_PREFERENCES)){
            SHARED_PREFERENCES = PreferenceManager.getDefaultSharedPreferences(context);
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

    public static long getLong(Pref pref){
        checkInit();
        return SHARED_PREFERENCES.getLong(pref.key, 0);
    }

    public static long getLong(DefaultValuePref<Long> pref){
        checkInit();
        return SHARED_PREFERENCES.getLong(pref.key, pref.getValue());
    }

    public static float getFloat(Pref pref){
        checkInit();
        return SHARED_PREFERENCES.getFloat(pref.key, 0);
    }

    public static float getFloat(DefaultValuePref<Float> pref){
        checkInit();
        return SHARED_PREFERENCES.getFloat(pref.key, pref.getValue());
    }

    public static boolean getBoolean(Pref pref, boolean defValue){
        checkInit();
        return SHARED_PREFERENCES.getBoolean(pref.key, defValue);
    }

    public static boolean getBoolean(DefaultValuePref<Boolean> pref){
        checkInit();
        return SHARED_PREFERENCES.getBoolean(pref.key, pref.getValue());
    }

    public static void putString(Pref pref, String value){
        checkInit();
        SHARED_PREFERENCES.edit().putString(pref.key, value).apply();
    }

    public static void putInt(Pref pref, int value){
        checkInit();
        SHARED_PREFERENCES.edit().putInt(pref.key, value).apply();
    }

    public static void putLong(Pref pref, long value){
        checkInit();
        SHARED_PREFERENCES.edit().putLong(pref.key, value).apply();
    }

    public static void putFloat(Pref pref, float value){
        checkInit();
        SHARED_PREFERENCES.edit().putFloat(pref.key, value).apply();
    }

    public static void putBoolean(Pref pref, boolean value){
        checkInit();
        SHARED_PREFERENCES.edit().putBoolean(pref.key, value).apply();
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

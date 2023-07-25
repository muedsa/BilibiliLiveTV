package com.muedsa.bilibililivetv;

import android.app.Application;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.google.common.base.Strings;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;
import com.muedsa.bilibililivetv.preferences.Prefs;
import com.muedsa.bilibililivetv.room.AppDatabase;

import java.util.Map;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;

public class App extends Application {

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = AppDatabase.getDatabase(this);
        Prefs.init(getApplicationContext());
        RxJavaPlugins.setErrorHandler(throwable ->
                FirebaseCrashlytics.getInstance().recordException(throwable));
        String json = Prefs.getString(Prefs.BILIBILI_COOKIE_JSON);
        if (!Strings.isNullOrEmpty(json)) {
            BilibiliLiveApi.login(JSON.parseObject(json, new TypeReference<Map<String, String>>() {
            }));
        }
    }

    public AppDatabase getDatabase() {
        return database;
    }
}

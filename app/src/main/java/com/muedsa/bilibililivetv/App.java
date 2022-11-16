package com.muedsa.bilibililivetv;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.base.Strings;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;
import com.muedsa.bilibililivetv.room.AppDatabase;
import com.muedsa.bilibililivetv.util.VersionLegacy;
import com.muedsa.httpjsonclient.Container;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class App extends Application {

    private AppDatabase database;

    public static final String SP_NAME = "BILIBILI_LIVE_TV";

    @Override
    public void onCreate() {
        super.onCreate();
        database = AppDatabase.getDatabase(this);
        Completable.create(emitter -> {
            VersionLegacy.roomLiveHistory(App.this, database.getLiveRoomDaoWrapper());
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).subscribe();
        SharedPreferences sharedPreferences = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String sessData = sharedPreferences.getString(Container.COOKIE_KEY_SESSDATA, null);
        if (!Strings.isNullOrEmpty(sessData)) {
            BilibiliLiveApi.client().putCookie(Container.COOKIE_KEY_SESSDATA, sessData);
        }
    }



    public AppDatabase getDatabase() {
        return database;
    }
}

package com.muedsa.bilibililivetv;

import android.app.Application;
import android.util.Log;

import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.BilibiliApiContainer;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;
import com.muedsa.bilibililivetv.preferences.Prefs;
import com.muedsa.bilibililivetv.room.AppDatabase;
import com.muedsa.bilibililivetv.util.VersionLegacy;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class App extends Application {

    private AppDatabase database;
    @Override
    public void onCreate() {
        super.onCreate();
        database = AppDatabase.getDatabase(this);
        Completable.create(emitter -> {
            VersionLegacy.roomLiveHistory(App.this, database.getLiveRoomDaoWrapper());
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).subscribe();
        Prefs.init(getApplicationContext());
        String sessData = Prefs.getString(Prefs.SESS_DATA);
        if (!Strings.isNullOrEmpty(sessData)) {
            BilibiliLiveApi.client().putCookie(BilibiliApiContainer.COOKIE_KEY_SESSDATA, sessData);
        }
    }



    public AppDatabase getDatabase() {
        return database;
    }
}

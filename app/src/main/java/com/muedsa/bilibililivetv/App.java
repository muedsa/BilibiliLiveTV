package com.muedsa.bilibililivetv;

import android.app.Application;

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
    }



    public AppDatabase getDatabase() {
        return database;
    }
}

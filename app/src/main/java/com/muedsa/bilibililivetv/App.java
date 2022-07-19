package com.muedsa.bilibililivetv;

import android.app.Application;

import com.muedsa.bilibililivetv.room.AppDatabase;
import com.muedsa.bilibililivetv.task.TaskRunner;
import com.muedsa.bilibililivetv.util.VersionLegacy;

public class App extends Application {

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = AppDatabase.getDatabase(this);
        TaskRunner.getInstance().executeAsync(() -> VersionLegacy.roomLiveHistory(this, database.getLiveRoomDaoWrapper()));
    }

    public AppDatabase getDatabase() {
        return database;
    }
}

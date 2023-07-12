package com.muedsa.bilibililivetv.room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.muedsa.bilibililivetv.room.dao.LiveRoomDao;
import com.muedsa.bilibililivetv.room.model.LiveRoom;

@Database(entities = {LiveRoom.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();

    abstract LiveRoomDao liveRoomDao();

    private static LiveRoomDao.Wrapper liveRoomDaoWrapper;

    public LiveRoomDao getLiveRoomDaoWrapper() {
        return liveRoomDaoWrapper;
    }

    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                            "bilibili_live_tv_database")
                    .addMigrations(MIGRATION_1_2)
                    .build();
            liveRoomDaoWrapper = new LiveRoomDao.Wrapper(instance.liveRoomDao());
        }
        return instance;
    }

    public static Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'live_room' ADD COLUMN 'uid' INTEGER NOT NULL DEFAULT 0");
            Log.d(TAG, "Migration 1 to 2");
        }
    };
}

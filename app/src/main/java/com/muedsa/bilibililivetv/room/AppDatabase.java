package com.muedsa.bilibililivetv.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.muedsa.bilibililivetv.room.dao.LiveRoomDao;
import com.muedsa.bilibililivetv.room.model.LiveRoom;

@Database(entities = {LiveRoom.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

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
                    .build();
            liveRoomDaoWrapper = new LiveRoomDao.Wrapper(instance.liveRoomDao());
        }
        return instance;
    }
}

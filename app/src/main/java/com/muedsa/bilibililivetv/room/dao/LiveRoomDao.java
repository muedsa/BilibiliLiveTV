package com.muedsa.bilibililivetv.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.muedsa.bilibililivetv.room.model.LiveRoom;

import java.util.List;
import java.util.Optional;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface LiveRoomDao extends IBaseDao<LiveRoom> {

    @Query("SELECT * FROM live_room WHERE id = :id")
    Optional<LiveRoom> getById(long id);

    @Query("SELECT * FROM live_room ORDER BY update_at DESC")
    Flowable<List<LiveRoom>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertAll(List<LiveRoom> liveRoom);

    @Delete
    Completable delete(LiveRoom liveRoom);

    @Query("DELETE FROM live_room")
    Completable deleteAll();


    class Wrapper extends IBaseDao.Wrapper<LiveRoomDao, LiveRoom> implements LiveRoomDao {
        public Wrapper(LiveRoomDao dao) {
            super(dao);
        }

        @Override
        public Optional<LiveRoom> getById(long id) {
            return dao.getById(id);
        }

        @Override
        public Flowable<List<LiveRoom>> getAll() {
            return dao.getAll();
        }

        @Override
        public Completable insertAll(List<LiveRoom> liveRoom) {
            return dao.insertAll(liveRoom);
        }

        @Override
        public Completable delete(LiveRoom liveRoom) {
            return dao.delete(liveRoom);
        }

        @Override
        public Completable deleteAll() {
            return dao.deleteAll();
        }
    }
}

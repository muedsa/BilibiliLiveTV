package com.muedsa.bilibililivetv.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.muedsa.bilibililivetv.room.dao.LiveRoomDao;
import com.muedsa.bilibililivetv.room.model.LiveRoom;

import java.util.List;
import java.util.Optional;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LiveRoomViewModel extends ViewModel {
    private final LiveRoomDao liveRoomDao;

    public LiveRoomViewModel(LiveRoomDao liveRoomDao) {
        this.liveRoomDao = liveRoomDao;
    }

    public Flowable<List<LiveRoom>> getLiveRooms() {
        return liveRoomDao.getAll();
    }

    public Completable sync(LiveRoom liveRoom) {
        return Completable.create(emitter -> {
            Optional<LiveRoom> liveRoomOptional = liveRoomDao.getById(liveRoom.getId());
            if(liveRoomOptional.isPresent()){
                liveRoomDao.update(liveRoom);
            }else{
                liveRoomDao.insert(liveRoom);
            }
        });
    }

    public Completable clear() {
        return liveRoomDao.deleteAll();
    }

    public Completable delete(LiveRoom liveRoom) {
        return liveRoomDao.delete(liveRoom);
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final LiveRoomDao liveRoomDao;

        public Factory(LiveRoomDao liveRoomDao) {
            this.liveRoomDao = liveRoomDao;
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked cast")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if(modelClass.isAssignableFrom(LiveRoomViewModel.class)) {
                return (T) new LiveRoomViewModel(liveRoomDao);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}

package com.muedsa.bilibililivetv.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.muedsa.bilibililivetv.room.dao.LiveRoomDao;
import com.muedsa.bilibililivetv.room.model.LiveRoom;

import java.util.List;
import java.util.Optional;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LiveRoomViewModel extends ViewModel {
    private final LiveRoomDao liveRoomDao;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<List<LiveRoom>> liveRooms = new MutableLiveData<>();

    public LiveRoomViewModel(LiveRoomDao liveRoomDao) {
        this.liveRoomDao = liveRoomDao;
    }

    public MutableLiveData<List<LiveRoom>> getLiveRooms() {
        return liveRooms;
    }

    public void fetchLiveRooms() {
        disposables.add(liveRoomDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(liveRooms::setValue));
    }

    public Completable sync(LiveRoom liveRoom) {
        return Completable.create(emitter -> {
            Optional<LiveRoom> liveRoomOptional = liveRoomDao.getById(liveRoom.getId());
            if (liveRoomOptional.isPresent()) {
                liveRoomDao.update(liveRoom);
            } else {
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

    @Override
    protected void onCleared() {
        disposables.clear();
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
            if (modelClass.isAssignableFrom(LiveRoomViewModel.class)) {
                return (T) new LiveRoomViewModel(liveRoomDao);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}

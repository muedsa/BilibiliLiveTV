package com.muedsa.bilibililivetv.model.bilibili;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.muedsa.bilibililiveapiclient.model.live.LargeInfo;
import com.muedsa.bilibililiveapiclient.model.live.PlayUrlData;
import com.muedsa.bilibililivetv.model.RMessage;
import com.muedsa.bilibililivetv.request.RxRequestFactory;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LiveRoomInfoViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<RMessage<LiveRoomAllInfo>> result = new MutableLiveData<>();

    public MutableLiveData<RMessage<LiveRoomAllInfo>> getResult() {
        return result;
    }

    public void fetchAllInfo(long roomId) {
        Single.zip(RxRequestFactory.bilibiliDanmuRoomInfo(roomId),
                        RxRequestFactory.bilibiliDanmuToken(roomId),
                        RxRequestFactory.bilibiliPlayUrlMessage(roomId),
                        LiveRoomAllInfo::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> result.setValue(RMessage.loading()))
                .subscribe(r -> result.setValue(RMessage.success(r)),
                        t -> result.setValue(RMessage.error(t)),
                        disposables);
    }


    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public static class LiveRoomAllInfo {
        private LargeInfo largeInfo;
        private String token;
        private PlayUrlData playUrlData;

        public LiveRoomAllInfo(LargeInfo largeInfo, String token, PlayUrlData playUrlData) {
            this.largeInfo = largeInfo;
            this.token = token;
            this.playUrlData = playUrlData;
        }

        public LargeInfo getLargeInfo() {
            return largeInfo;
        }

        public void setLargeInfo(LargeInfo largeInfo) {
            this.largeInfo = largeInfo;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public PlayUrlData getPlayUrlData() {
            return playUrlData;
        }

        public void setPlayUrlData(PlayUrlData playUrlData) {
            this.playUrlData = playUrlData;
        }
    }
}

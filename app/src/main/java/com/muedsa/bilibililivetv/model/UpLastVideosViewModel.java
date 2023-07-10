package com.muedsa.bilibililivetv.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.muedsa.bilibililiveapiclient.model.space.SpaceSearchResult;
import com.muedsa.bilibililivetv.request.RxRequestFactory;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UpLastVideosViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<RMessage<SpaceSearchResult>> result = new MutableLiveData<>();

    public MutableLiveData<RMessage<SpaceSearchResult>> getResult() {
        return result;
    }

    public void loadVideos(int pageNum, int pageSize, long mid) {
        RxRequestFactory.bilibiliSpaceSearchVideos(pageNum, pageSize, mid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> result.setValue(RMessage.loading()))
                .subscribe(r -> result.setValue(RMessage.success(r)),
                        t -> result.setValue(RMessage.error(t)),
                        disposables);
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}

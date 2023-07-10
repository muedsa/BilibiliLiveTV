package com.muedsa.bilibililivetv.model.bilibili;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.muedsa.bilibililiveapiclient.model.video.VideoDetail;
import com.muedsa.bilibililivetv.model.RMessage;
import com.muedsa.bilibililivetv.request.RxRequestFactory;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class VideoDetailViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<RMessage<VideoDetail>> result = new MutableLiveData<>();

    public MutableLiveData<RMessage<VideoDetail>> getResult() {
        return result;
    }

    public void fetchVideoDetail(String bv, int page) {
        RxRequestFactory.bilibiliVideoDetail(bv, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> result.setValue(RMessage.loading()))
                .subscribe(videoDetail -> result.setValue(RMessage.success(videoDetail)),
                        throwable -> result.setValue(RMessage.error(throwable)),
                        disposables);
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}

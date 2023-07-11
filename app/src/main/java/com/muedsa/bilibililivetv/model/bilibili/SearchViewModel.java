package com.muedsa.bilibililivetv.model.bilibili;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.muedsa.bilibililiveapiclient.model.search.SearchResult;
import com.muedsa.bilibililivetv.model.RMessage;
import com.muedsa.bilibililivetv.request.RxRequestFactory;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<RMessage<SearchResult>> result = new MutableLiveData<>();

    public MutableLiveData<RMessage<SearchResult>> getResult() {
        return result;
    }

    public void search(String query) {
        Single.zip(RxRequestFactory.bilibiliSearchLive(query),
                        RxRequestFactory.bilibiliSearchVideo(query),
                        (liveSearchResult, videoSearchResult) -> {
                            liveSearchResult.setVideo(videoSearchResult);
                            return liveSearchResult;
                        })
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
}

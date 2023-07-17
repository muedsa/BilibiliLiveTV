package com.muedsa.bilibililivetv.model.bilibili;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.muedsa.bilibililiveapiclient.model.FlowItems;
import com.muedsa.bilibililiveapiclient.model.dynamic.DynamicItem;
import com.muedsa.bilibililivetv.model.RMessage;
import com.muedsa.bilibililivetv.request.RxRequestFactory;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DynamicFeedViewModel extends ViewModel {

    private String type;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<RMessage<FlowItems<DynamicItem>>> result = new MutableLiveData<>();

    public MutableLiveData<RMessage<FlowItems<DynamicItem>>> getResult() {
        return result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void fetchDynamicFeedAll(String offset, int page) {
        RxRequestFactory.bilibiliDynamicFeedAll(offset, page, type)
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

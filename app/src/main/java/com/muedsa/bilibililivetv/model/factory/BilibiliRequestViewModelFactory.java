package com.muedsa.bilibililivetv.model.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.muedsa.bilibililivetv.model.bilibili.DynamicFeedViewModel;
import com.muedsa.bilibililivetv.model.bilibili.LiveRoomInfoViewModel;
import com.muedsa.bilibililivetv.model.bilibili.SearchViewModel;
import com.muedsa.bilibililivetv.model.bilibili.UpLastVideosViewModel;
import com.muedsa.bilibililivetv.model.bilibili.VideoDetailViewModel;

public class BilibiliRequestViewModelFactory implements ViewModelProvider.Factory {

    private BilibiliRequestViewModelFactory() {
    }

    private static final class Holder {
        static final BilibiliRequestViewModelFactory INSTANCE = new BilibiliRequestViewModelFactory();
    }

    public static BilibiliRequestViewModelFactory getInstance() {
        return Holder.INSTANCE;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked cast")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UpLastVideosViewModel.class)) {
            return (T) new UpLastVideosViewModel();
        } else if (modelClass.isAssignableFrom(VideoDetailViewModel.class)) {
            return (T) new VideoDetailViewModel();
        } else if (modelClass.isAssignableFrom(LiveRoomInfoViewModel.class)) {
            return (T) new LiveRoomInfoViewModel();
        } else if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel();
        } else if (modelClass.isAssignableFrom(DynamicFeedViewModel.class)) {
            return (T) new DynamicFeedViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

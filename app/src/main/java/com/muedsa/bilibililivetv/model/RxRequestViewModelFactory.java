package com.muedsa.bilibililivetv.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RxRequestViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked cast")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(UpLastVideosViewModel.class)) {
            return (T) new UpLastVideosViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

package com.muedsa.bilibililivetv.model;

import androidx.annotation.Nullable;

public class RMessage<T> {

    public enum Status {
        LOADING,
        SUCCESS,
        ERROR
    }

    private final Status status;

    @Nullable
    private final T data;

    @Nullable
    public final Throwable error;

    public static <T> RMessage<T> loading() {
        return new RMessage<>(Status.LOADING, null, null);
    }

    public static <T> RMessage<T> success(T data) {
        return new RMessage<>(Status.SUCCESS, data, null);
    }

    public static <T> RMessage<T> error(Throwable throwable) {
        return new RMessage<>(Status.ERROR, null, throwable);
    }

    public RMessage(Status status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }
}

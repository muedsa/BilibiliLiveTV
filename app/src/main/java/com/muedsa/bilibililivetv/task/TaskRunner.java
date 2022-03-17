package com.muedsa.bilibililivetv.task;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskRunner {

    private static final TaskRunner instance = new TaskRunner();

    private final Executor executor =  new ThreadPoolExecutor(5, 128, 1,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    private final Handler handler = new Handler(Looper.getMainLooper());


    private TaskRunner() {
    }

    public static TaskRunner getInstance() {
        return instance;
    }

    public interface Callback<R> {
        void onComplete(R result);
    }

    public <R> void executeAsync(@NonNull Callable<R> callable, @NonNull Callback<R> callback) {
        executor.execute(() -> {
            R result = null;
            try {
                result = callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                final R finalResult = result;
                handler.post(() -> {
                    callback.onComplete(finalResult);
                });
            }
        });
    }

    public <R> void executeAsync(@NonNull Runnable runnable){
        executor.execute(runnable);
    }
}

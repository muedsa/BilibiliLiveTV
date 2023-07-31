package com.muedsa.bilibililivetv.widget;

import android.app.Activity;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.leanback.app.BackgroundManager;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.common.base.Strings;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.GlideRequest;
import com.muedsa.bilibililivetv.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class BackgroundManagerDelegate {

    private static final int BACKGROUND_UPDATE_DELAY = 300;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Activity activity;

    private Drawable defaultBackground;
    private int defaultWidth;
    private int defaultHeight;
    private Timer backgroundTimer;
    private String backgroundUri;
    private BackgroundManager backgroundManager;
    private Function<GlideRequest<Drawable>, GlideRequest<Drawable>> glideTransform;

    public BackgroundManagerDelegate(@NonNull Activity activity) {
        this(activity, null, null);
    }

    public BackgroundManagerDelegate(@NonNull Activity activity,
                                     @Nullable Function<GlideRequest<Drawable>, GlideRequest<Drawable>> glideTransform) {
        this(activity, null, glideTransform);
    }


    public BackgroundManagerDelegate(@NonNull Activity activity,
                                     @Nullable Drawable defaultBackground,
                                     @Nullable Function<GlideRequest<Drawable>, GlideRequest<Drawable>> glideTransform) {
        this.activity = activity;
        if (defaultBackground == null) {
            setDefaultBackground(ContextCompat.getDrawable(activity, R.drawable.default_background));
        } else {
            setDefaultBackground(defaultBackground);
        }
        setGlideTransform(glideTransform);
        prepareBackgroundManager(activity);
    }

    public void setDefaultBackground(Drawable defaultBackground) {
        this.defaultBackground = defaultBackground;
    }


    public void setGlideTransform(Function<GlideRequest<Drawable>, GlideRequest<Drawable>> glideTransform) {
        if (glideTransform != null) {
            this.glideTransform = glideTransform;
        } else {
            this.glideTransform = GlideRequest::centerCrop;
        }
    }

    private void prepareBackgroundManager(Activity activity) {
        backgroundManager = BackgroundManager.getInstance(activity);
        backgroundManager.attach(activity.getWindow());
        WindowManager windowManager = activity.getWindowManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();
            Rect bounds = windowMetrics.getBounds();
            Insets insets = windowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            defaultWidth = bounds.width() - insets.left - insets.right;
            defaultHeight = bounds.height() - insets.top - insets.bottom;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            defaultWidth = displayMetrics.widthPixels;
            defaultHeight = displayMetrics.heightPixels;
        }
    }

    public void startBackgroundUpdate(String backgroundUri) {
        this.backgroundUri = backgroundUri;
        if (null != backgroundTimer) {
            backgroundTimer.cancel();
        }
        backgroundTimer = new Timer();
        backgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    private void updateBackground(String uri) {
        if (!Strings.isNullOrEmpty(uri) || activity != null && !activity.isDestroyed()) {
            glideTransform.andThen(r -> r.error(defaultBackground)
                            .into(new CustomTarget<Drawable>(defaultWidth, defaultHeight) {
                                @Override
                                public void onResourceReady(@NonNull Drawable drawable,
                                                            @Nullable Transition<? super Drawable> transition) {
                                    if (backgroundManager != null) {
                                        backgroundManager.setDrawable(drawable);
                                    }
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            }))
                    .apply(GlideApp.with(activity.getApplicationContext()).load(uri));
            backgroundTimer.cancel();
        }
    }

    public void dispose() {
        if (null != backgroundTimer) {
            backgroundTimer.cancel();
        }
    }

    private class UpdateBackgroundTask extends TimerTask {
        @Override
        public void run() {
            handler.post(() -> updateBackground(backgroundUri));
        }
    }
}

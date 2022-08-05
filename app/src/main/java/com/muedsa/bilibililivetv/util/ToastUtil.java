package com.muedsa.bilibililivetv.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ToastUtil {
    private final static String TAG = ToastUtil.class.getSimpleName();

    private final static Handler handler = new Handler(Looper.getMainLooper());

    public static void showShortToast(@Nullable Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(@Nullable Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }

    private static void showToast(Context context, CharSequence text, int duration) {
        if(isContextValid(context)) {
            handler.post(() -> Toast.makeText(context, text, duration).show());
        }else{
            Log.d(TAG, "Skipping toast, context is invalid: " + context);
            Log.d(TAG, "Skipping toast, text: " + text);
        }
    }

    public static boolean isContextValid(Context context) {
        return context instanceof Activity && !((Activity) context).isFinishing() && !((Activity) context).isDestroyed();
    }

}
package com.muedsa.bilibililivetv.util;

import androidx.annotation.Nullable;

import com.google.common.base.Strings;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class CrashlyticsUtil {
    public static final FirebaseCrashlytics CRASHLYTICS = FirebaseCrashlytics.getInstance();

    public static void log(@Nullable Throwable throwable) {
        if (throwable != null) {
            CRASHLYTICS.recordException(throwable);
        }
    }

    public static void log(@Nullable String message) {
        if (!Strings.isNullOrEmpty(message)) {
            CRASHLYTICS.log(message);
        }
    }
}

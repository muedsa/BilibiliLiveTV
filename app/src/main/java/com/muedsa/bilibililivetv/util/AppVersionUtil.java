package com.muedsa.bilibililivetv.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

public class AppVersionUtil {

    private final static String TAG = AppVersionUtil.class.getSimpleName();


    public static int getVersionCode(Context context) {
        int code = -1;
        try {
            code = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getVersionCode error", e);
        }
        return code;
    }
}

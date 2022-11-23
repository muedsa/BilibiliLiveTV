package com.muedsa.bilibililivetv.util;

import android.content.Context;

public class DpUtil {

    public static int convertDpToPixel(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static int convertPixelToDp(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) px / density);
    }
}

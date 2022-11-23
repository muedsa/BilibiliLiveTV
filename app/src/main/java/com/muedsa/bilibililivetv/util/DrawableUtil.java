package com.muedsa.bilibililivetv.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.core.graphics.drawable.DrawableCompat;

public class DrawableUtil {

    public static Drawable getWhiteDrawable(Context context, int drawableId) {
        Drawable drawable = context.getDrawable(drawableId);
        Drawable whiteDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(whiteDrawable, Color.WHITE);
        return whiteDrawable;
    }
}

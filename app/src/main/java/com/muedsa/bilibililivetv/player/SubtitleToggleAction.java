package com.muedsa.bilibililivetv.player;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.leanback.widget.Action;

import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.util.DrawableUtil;

public class SubtitleToggleAction extends Action {

    public SubtitleToggleAction(Context context) {
        super(R.id.playback_controls_subtitle_toggle);
        Drawable drawable = DrawableUtil.getWhiteDrawable(context, R.drawable.ic_baseline_subtitles_24);
        setIcon(drawable);
        setLabel1(context.getString(R.string.playback_controls_subtitle_toggle));
    }
}

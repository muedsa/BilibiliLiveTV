package com.muedsa.bilibililivetv.player;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.leanback.widget.PlaybackControlsRow;

import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.util.DrawableUtil;

public class DanmakuPlayToggleAction extends PlaybackControlsRow.MultiAction {

    public static final int INDEX_ON = 0;
    public static final int INDEX_OFF = 1;

    public DanmakuPlayToggleAction(Context context) {
        super(R.id.playback_controls_danmaku_play_toggle);
        Drawable[] drawables = new Drawable[2];
        drawables[INDEX_ON] = DrawableUtil.getWhiteDrawable(context, R.drawable.ic_danmaku_enable);
        drawables[INDEX_OFF] = DrawableUtil.getWhiteDrawable(context, R.drawable.ic_danmaku_disable);

        setDrawables(drawables);
        String[] labels = new String[drawables.length];
        labels[INDEX_ON] = context.getString(R.string.playback_controls_danmu_play);
        labels[INDEX_OFF] = context.getString(R.string.playback_controls_danmu_stop);
        setLabels(labels);
    }
}

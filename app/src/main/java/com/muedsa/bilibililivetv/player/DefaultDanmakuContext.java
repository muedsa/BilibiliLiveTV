package com.muedsa.bilibililivetv.player;

import com.muedsa.bilibililivetv.preferences.Prefs;

import java.util.HashMap;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;

public class DefaultDanmakuContext {

    public static DanmakuContext create() {
        DanmakuContext danmakuContext = DanmakuContext.create();
        danmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f)
                .setScaleTextSize(Prefs.getInt(Prefs.DANMAKU_SCALE_TEXT_SIZE) / 100f)
                .setMaximumLines(getMaxLinesPair())
                .preventOverlapping(getOverlappingEnablePair())
                .setDanmakuTransparency(Prefs.getInt(Prefs.DANMAKU_TRANSPARENCY) / 100f)
                .setDanmakuMargin(Prefs.getInt(Prefs.DANMAKU_MARGIN));
        return danmakuContext;
    }

    private static HashMap<Integer, Integer> getMaxLinesPair(){
        HashMap<Integer, Integer> pari = new HashMap<>();
        pari.put(BaseDanmaku.TYPE_SCROLL_RL, Prefs.getInt(Prefs.DANMAKU_MAX_LINES_SCROLL));
        pari.put(BaseDanmaku.TYPE_FIX_TOP, Prefs.getInt(Prefs.DANMAKU_MAX_LINES_FIX_TOP));
        pari.put(BaseDanmaku.TYPE_FIX_BOTTOM, Prefs.getInt(Prefs.DANMAKU_MAX_LINES_FIX_BOTTOM));
        return pari;
    }

    private static HashMap<Integer, Boolean> getOverlappingEnablePair(){
        HashMap<Integer, Boolean> pari = new HashMap<>();
        pari.put(BaseDanmaku.TYPE_SCROLL_RL, Prefs.getBoolean(Prefs.DANMAKU_OVERLAPPING_ENABLE_SCROLL));
        pari.put(BaseDanmaku.TYPE_FIX_TOP, Prefs.getBoolean(Prefs.DANMAKU_OVERLAPPING_ENABLE_FIX_TOP));
        pari.put(BaseDanmaku.TYPE_FIX_BOTTOM, Prefs.getBoolean(Prefs.DANMAKU_OVERLAPPING_ENABLE_FIX_BOTTOM));
        return pari;
    }
}

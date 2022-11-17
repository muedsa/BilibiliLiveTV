package com.muedsa.bilibililivetv.player;

import com.muedsa.bilibililivetv.preferences.Prefs;

import java.util.HashMap;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;

public class DefaultDanmakuContext {
    public static final HashMap<Integer, Integer> MAX_LINES_PAIR = new HashMap<>();
    public static final HashMap<Integer, Boolean> OVERLAPPING_ENABLE_PAIR = new HashMap<>();

    static {
        // 设置最大显示行数
        MAX_LINES_PAIR.put(BaseDanmaku.TYPE_SCROLL_RL, 5);
        MAX_LINES_PAIR.put(BaseDanmaku.TYPE_FIX_TOP, 5);
        MAX_LINES_PAIR.put(BaseDanmaku.TYPE_FIX_BOTTOM, 8);

        // 设置是否禁止重叠
        OVERLAPPING_ENABLE_PAIR.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        OVERLAPPING_ENABLE_PAIR.put(BaseDanmaku.TYPE_FIX_TOP, true);
        OVERLAPPING_ENABLE_PAIR.put(BaseDanmaku.TYPE_FIX_BOTTOM, true);
    }

    public static DanmakuContext create() {
        DanmakuContext danmakuContext = DanmakuContext.create();
        danmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f)
                .setScaleTextSize(Prefs.getInt(Prefs.DANMAKU_SCALE_TEXT_SIZE) / 100f)
                .setMaximumLines(MAX_LINES_PAIR)
                .preventOverlapping(OVERLAPPING_ENABLE_PAIR)
                .setDanmakuTransparency(Prefs.getInt(Prefs.DANMAKU_TRANSPARENCY) / 100f)
                .setDanmakuMargin(Prefs.getInt(Prefs.DANMAKU_MARGIN));
        return danmakuContext;
    }
}

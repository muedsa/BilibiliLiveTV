package com.muedsa.bilibililivetv.danmuku;

import android.graphics.Color;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;

public class BiliDanmukuParser extends BaseDanmakuParser {
    @Override
    protected IDanmakus parse() {

        IDanmakus danmakus = new Danmakus();

        for (int i = 0; i < 100; i++) {
            BaseDanmaku item = mContext.mDanmakuFactory.createDanmaku(1, mContext);
            item.setTime(i * 100);
            item.textSize = 30 * (mDispDensity - 0.6f);
            item.textColor = Color.WHITE;
            item.textShadowColor = Color.BLACK;
            item.setTimer(mTimer);
            item.flags = mContext.mGlobalFlagValues;
            item.text = "2333333" + i;
            danmakus.addItem(item);
        }

        return danmakus;
    }
}

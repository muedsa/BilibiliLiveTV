package com.muedsa.bilibililivetv.player.video;

import android.graphics.Color;
import android.util.Log;

import com.muedsa.bilibililiveapiclient.model.danmaku.DanmakuElem;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;

public class BilibiliDanmakuParser extends BaseDanmakuParser {
    private static final String TAG = BilibiliDanmakuParser.class.getSimpleName();

    private final long cid;

    public BilibiliDanmakuParser(long cid) {
        this.cid = cid;
    }

    private List<DanmakuElem> initDanmakuList() {
        List<DanmakuElem> list = new ArrayList<>(1);
        try {
            list = BilibiliLiveApi.client().videoDanmakuElemList(cid);
            Log.d(TAG, "initDanmakuList: count=" + list.size());
        } catch (Exception e) {
            Log.d(TAG, "initDanmakuList:", e);
            list.add(DanmakuElem.newBuilder()
                    .setMode(BaseDanmaku.TYPE_FIX_TOP)
                    .setProgress(1000)
                    .setContent("初始化弹幕失败~~")
                    .setColor(Color.RED)
                    .build());
        }
        return list;
    }

    public BaseDanmaku buildDanmaku(DanmakuElem elem) {
        BaseDanmaku danmaku = mContext.mDanmakuFactory.createDanmaku(elem.getMode(), mContext);
        if (Objects.nonNull(danmaku)) {
            danmaku.text = elem.getContent();
            danmaku.padding = 0;
            danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
            danmaku.setTime(elem.getProgress());
            danmaku.textSize = elem.getFontSize() * (getDisplayer().getDensity() - 0.6f);
            danmaku.textColor = elem.getColor();
            danmaku.textShadowColor = elem.getColor() <= Color.BLACK ? Color.TRANSPARENT : Color.BLACK;
            danmaku.setTimer(mTimer);
            danmaku.flags = mContext.mGlobalFlagValues;
        }
        return danmaku;
    }

    private boolean supportDanmakuMode(int mode) {
        return BaseDanmaku.TYPE_SCROLL_RL == mode
                || BaseDanmaku.TYPE_FIX_TOP == mode
                || BaseDanmaku.TYPE_FIX_BOTTOM == mode
                || BaseDanmaku.TYPE_SCROLL_LR == mode;
    }

    @Override
    protected IDanmakus parse() {
        List<DanmakuElem> list = initDanmakuList();
        Danmakus danmakus = new Danmakus();
        for (DanmakuElem danmakuElem : list) {
            if (supportDanmakuMode(danmakuElem.getMode())) {
                BaseDanmaku danmaku = buildDanmaku(danmakuElem);
                if (Objects.nonNull(danmaku)) {
                    danmakus.addItem(danmaku);
                }
            }
        }
        Log.d(TAG, "parse danmaku, count:" + danmakus.size());
        return danmakus;
    }
}

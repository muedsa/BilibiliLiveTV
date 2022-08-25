package com.muedsa.bilibililivetv.player.danmaku;

import android.graphics.Color;
import android.util.Log;

import java.util.Arrays;

import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.Duration;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.DanmakuFactory;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;

public class GiftDanmakuManager {
    private static final String TAG = GiftDanmakuManager.class.toString();

    private static final long GIFT_DURATION = 1500;
    private static final long MIN_DURATION = 500;
    private static final long MAX_DANMAKU_TIME_DELTA = 5000;

    private final IDanmakuView danmakuView;

    private int GIFT_DANMAKU_POINT_HEIGHT = 0;
    private final int maxGiftDanmakuLine = 3;
    private final Long[] lastEndTimePreLine = new Long[maxGiftDanmakuLine];
    private int willEndIndex = 0;

    public GiftDanmakuManager(IDanmakuView danmakuView){
        this.danmakuView = danmakuView;
        Arrays.fill(lastEndTimePreLine, 0L);
    }

    public void prepare(){
        if(GIFT_DANMAKU_POINT_HEIGHT == 0){
            BaseDanmaku danmaku  = buildBaseDanmaku(" ", 0, 0);
            if (danmaku != null) {
                danmakuView.getConfig().getDisplayer().measure(danmaku, true);
                GIFT_DANMAKU_POINT_HEIGHT = (int) Math.ceil(danmaku.paintHeight);
            }
        }
    }

    public synchronized void add(String danmakuContent) {
        long originalTime = danmakuView.getCurrentTime() + 500;
        long time = originalTime;
        long duration = GIFT_DURATION;
        int useLineIndex = willEndIndex;
        long lastGiftDanmakuEndTime = lastEndTimePreLine[useLineIndex];
        if(lastGiftDanmakuEndTime > originalTime) {
            time = lastGiftDanmakuEndTime + 1;
            long timeDelta = time - originalTime;
            if(timeDelta < MAX_DANMAKU_TIME_DELTA){
                duration = duration * ((MAX_DANMAKU_TIME_DELTA - timeDelta) / MAX_DANMAKU_TIME_DELTA);
                if(duration < MIN_DURATION){
                    duration = MIN_DURATION;
                }
                lastEndTimePreLine[useLineIndex] = time + duration;
                flushWillEndIndex();
                addDanmaku(danmakuContent, time, duration, useLineIndex + 1);
            }else{
                Log.d(TAG, "ignore: " + danmakuContent);
            }
        }else{
            lastEndTimePreLine[useLineIndex] = time + duration;
            flushWillEndIndex();
            addDanmaku(danmakuContent, time, duration, useLineIndex + 1);
        }
    }

    private void flushWillEndIndex(){
        int index = 0;
        for (int r = 1; r < lastEndTimePreLine.length; r++) {
            if(lastEndTimePreLine[r] < lastEndTimePreLine[index]) {
                index = r;
            }
        }
        willEndIndex = index;
    }

    private void addDanmaku(String content, long time, long duration, int lineNum){
        BaseDanmaku danmaku  = buildBaseDanmaku(content, time, duration);
        if (danmaku != null) {
            DanmakuContext danmakuContext = danmakuView.getConfig();
            int y = danmakuContext.mDanmakuFactory.CURRENT_DISP_HEIGHT - GIFT_DANMAKU_POINT_HEIGHT * lineNum;
            danmakuContext.mDanmakuFactory.fillTranslationData(danmaku,
                    0, y,
                    0, y,
                    duration, 0,
                    1, 1);
            danmakuContext.mDanmakuFactory.fillAlphaData(danmaku, 255, 0,
                    duration);
            danmakuView.addDanmaku(danmaku);
        }
    }

    private BaseDanmaku buildBaseDanmaku(String content, long time, long duration){
        BaseDanmaku danmaku = null;
        if(danmakuView != null && danmakuView.getConfig() != null && danmakuView.getConfig().getDisplayer() != null) {
            DanmakuContext danmakuContext = danmakuView.getConfig();
            danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SPECIAL);
            if (danmaku != null) {
                float textSize = (float) DanmakuFactory.DANMAKU_MEDIUM_TEXTSIZE * (danmakuContext.getDisplayer().getDensity() - 0.6f);
                danmaku.text = content;
                danmaku.padding = 0;
                danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
                danmaku.isLive = true;
                danmaku.setTime(time);
                danmaku.textSize = textSize;
                danmaku.textColor = Color.WHITE;
                danmaku.textShadowColor = Color.BLACK;
                danmaku.duration = new Duration(duration);
            }
        }
        return danmaku;
    }
}

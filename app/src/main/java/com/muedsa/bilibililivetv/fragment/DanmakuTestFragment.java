package com.muedsa.bilibililivetv.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.muedsa.bilibililivetv.R;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.Duration;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.DanmakuFactory;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuSurfaceView;

public class DanmakuTestFragment extends Fragment {
    private static final String TAG = DanmakuTestFragment.class.getSimpleName();

    private DanmakuSurfaceView danmakuView;
    private DanmakuContext danmakuContext;
    private BaseDanmakuParser danmakuParser;

    private Timer timer1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_danmaku_test, container, false);
        danmakuView = (DanmakuSurfaceView) LayoutInflater.from(getContext()).inflate(
                R.layout.danmaku_surface, root, false);
        if(root != null) root.addView(danmakuView, 0);
        init();
        return root;
    }

    public void init(){
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5);
        maxLinesPair.put(BaseDanmaku.TYPE_FIX_TOP, 5);
        maxLinesPair.put(BaseDanmaku.TYPE_FIX_BOTTOM, 5);
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_BOTTOM, true);

        danmakuContext = DanmakuContext.create();
        danmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f)
                .setScaleTextSize(1.2f)
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair)
                .setDanmakuTransparency(0.85f)
                .setDanmakuMargin(5);

        danmakuParser = new BaseDanmakuParser() {
            @Override
            protected IDanmakus parse() {
                return new Danmakus();
            }
        };
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.showFPS(true);
        danmakuView.show();
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                danmakuView.start();
                timer1();
            }

            @Override
            public void updateTimer(DanmakuTimer danmakuTimer) {
            }

            @Override
            public void danmakuShown(BaseDanmaku baseDanmaku) {
            }

            @Override
            public void drawingFinished() {
            }
        });
        danmakuView.prepare(danmakuParser, danmakuContext);
    }

    private void addDanmaku(String content, int textColor, boolean textShadowTransparent, int type){
        if(danmakuContext != null && danmakuView != null && danmakuParser !=null){
            BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(type);
            if (danmaku != null) {
                danmaku.text = content;
                danmaku.padding = 0;
                danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
                danmaku.isLive = true;
                danmaku.setTime(danmakuView.getCurrentTime() + 500);
                danmaku.textSize = (float) 25.0 * (danmakuParser.getDisplayer().getDensity() - 0.6f);
                danmaku.textColor = textColor;
                danmaku.textShadowColor = textShadowTransparent ? Color.TRANSPARENT : Color.BLACK;
                danmaku.duration = new Duration(DanmakuFactory.COMMON_DANMAKU_DURATION);
                //danmakuContext.getDisplayer().getWidth();
                if(type == BaseDanmaku.TYPE_SPECIAL) {
                    danmaku.duration = new Duration(1000);
                }
                danmakuContext.mDanmakuFactory.fillTranslationData(danmaku,
                        5, (float) danmakuContext.mDanmakuFactory.CURRENT_DISP_HEIGHT, 5, (float) danmakuContext.mDanmakuFactory.CURRENT_DISP_HEIGHT - 30, 1500, 0,
                        1, 1f);
                //DanmakuFactory.fillLinePathData(danmaku, new float[][]{{5, danmakuContext.mDanmakuFactory.CURRENT_DISP_HEIGHT - 100}}, 0, 0);
                danmakuView.addDanmaku(danmaku);
            }
        }
    }

    private void timer1(){
        timer1 = new Timer();
        timer1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                addDanmaku("这是一条弹幕", Color.WHITE, false, BaseDanmaku.TYPE_SCROLL_RL);
            }
        }, 0, 1000);
        addDanmaku("这是一条SPECIAL弹幕", Color.WHITE, false, BaseDanmaku.TYPE_SPECIAL);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if(danmakuView != null) {
            danmakuView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onResume");
        if(danmakuView != null) {
            danmakuView.pause();
        }
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
        if(danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
            danmakuContext = null;
            danmakuParser = null;
        }
        if(timer1 != null){
            timer1.cancel();
            timer1 = null;
        }
    }
}

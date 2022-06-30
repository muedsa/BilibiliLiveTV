package com.muedsa.bilibililivetv.player;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.muedsa.bilibililiveapiclient.ChatBroadcastWsClient;
import com.muedsa.bilibililivetv.BuildConfig;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.model.LiveRoom;
import com.muedsa.bilibililivetv.ui.PlaybackVideoFragment;

import java.util.HashMap;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;

public class DanmakuDelegate {
    private static final String TAG = DanmakuDelegate.class.getSimpleName();

    private final PlaybackVideoFragment fragment;
    private final LiveRoom liveRoom;

    private IDanmakuView danmakuView;
    private DanmakuContext danmakuContext;
    private BaseDanmakuParser danmakuParser;
    private ChatBroadcastWsClient chatBroadcastWsClient;

    public DanmakuDelegate(@NonNull PlaybackVideoFragment fragment, @NonNull LiveRoom liveRoom){
        this.fragment = fragment;
        this.liveRoom = liveRoom;
    }

    public void init(){
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        danmakuView = fragment.requireActivity().findViewById(R.id.sv_danmaku);

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
        danmakuView.showFPS(BuildConfig.DEBUG);
        danmakuView.show();
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                danmakuView.start();
                initChatBroadcast();
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

    private void initChatBroadcast(){
        chatBroadcastWsClient = new ChatBroadcastWsClient(liveRoom.getId(), liveRoom.getDanmuWsToken());
        chatBroadcastWsClient.setCallBack(new ChatBroadcastWsClient.CallBack() {
            @Override
            public void onStart() {
                if(BuildConfig.DEBUG){
                    FragmentActivity activity = fragment.requireActivity();
                    Toast.makeText(activity,
                                    activity.getString(R.string.toast_msg_danmu_connect_success),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onReceiveDanmu(String text, float textSize, int textColor, boolean textShadowTransparent) {
                addDanmaku(text, textSize, textColor, textShadowTransparent);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                FragmentActivity activity = fragment.requireActivity();
                Toast.makeText(activity,
                                String.format(activity.getString(R.string.toast_msg_danmu_connect_error), reason),
                                Toast.LENGTH_LONG)
                        .show();
                if(fragment.isPlaying()){
                    startChatBroadcastWsClient();
                }
            }
        });
        startChatBroadcastWsClient();
    }

    private void startChatBroadcastWsClient(){
        if(chatBroadcastWsClient != null){
            try {
                chatBroadcastWsClient.start();
            }
            catch (Exception error){
                Log.d(TAG, "startChatBroadcastWsClient: ", error);
                FragmentActivity activity = fragment.requireActivity();
                Toast.makeText(activity,
                                String.format(activity.getString(R.string.toast_msg_danmu_connect_error), error.getLocalizedMessage()),
                                Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    private void addDanmaku(String content, float textSize, int textColor, boolean textShadowTransparent){
        if(danmakuContext != null && danmakuView != null && danmakuParser !=null){
            BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
            if (danmaku != null) {
                danmaku.text = content;
                danmaku.padding = 0;
                danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
                danmaku.isLive = true;
                danmaku.setTime(danmakuView.getCurrentTime() + 500);
                danmaku.textSize = textSize * (danmakuParser.getDisplayer().getDensity() - 0.6f);
                danmaku.textColor = textColor;
                danmaku.textShadowColor = textShadowTransparent ? Color.TRANSPARENT : Color.BLACK;
                danmakuView.addDanmaku(danmaku);
            }
        }
    }

    public void pause(){
        if(danmakuView != null && !danmakuView.isPaused()){
            danmakuView.pause();
        }
    }

    public void resume(){
        if (danmakuView != null) {
            if(danmakuView.isPrepared() && danmakuView.isPaused()){
                danmakuView.resume();
            }
        }else{
            init();
        }
    }

    public void setPlayer(boolean isPlaying){
        if(isPlaying){
            if(danmakuView != null && danmakuView.isPaused()){
                danmakuView.resume();
            }
        }else{
            pause();
        }
    }

    public void danmakuReleaseSwitch(){
        FragmentActivity activity = fragment.requireActivity();
        if(danmakuView != null){
            release();
            Toast.makeText(activity,
                            activity.getString(R.string.toast_msg_danmu_open),
                            Toast.LENGTH_SHORT)
                    .show();
        }else{
            init();
            Toast.makeText(activity,
                            activity.getString(R.string.toast_msg_danmu_stop),
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void release(){
        if(danmakuView != null){
            danmakuView.hide();
            danmakuView.release();
            danmakuView = null;
        }
        if(danmakuContext != null){
            danmakuContext = null;
        }
        if(danmakuParser != null){
            danmakuParser = null;
        }
        if(chatBroadcastWsClient != null){
            chatBroadcastWsClient.close();
            chatBroadcastWsClient = null;
        }
    }
}

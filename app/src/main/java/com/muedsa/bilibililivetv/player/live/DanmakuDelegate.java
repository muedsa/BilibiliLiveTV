package com.muedsa.bilibililivetv.player.live;

import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.ChatBroadcastWsClient;
import com.muedsa.bilibililivetv.EnvConfig;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.fragment.LiveStreamPlaybackFragment;
import com.muedsa.bilibililivetv.player.DefaultDanmakuContext;
import com.muedsa.bilibililivetv.room.model.LiveRoom;
import com.muedsa.bilibililivetv.util.ToastUtil;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;

public class DanmakuDelegate {
    private static final String TAG = DanmakuDelegate.class.getSimpleName();

    private final LiveStreamPlaybackFragment fragment;
    private final LiveRoom liveRoom;

    private IDanmakuView danmakuView;
    private DanmakuContext danmakuContext;
    private BaseDanmakuParser danmakuParser;
    private GiftDanmakuManager giftDanmakuManager;
    private ChatBroadcastWsClient chatBroadcastWsClient;

    private boolean giftDanmakuEnable = false;
    private int scDanmakuType = BaseDanmaku.TYPE_FIX_BOTTOM;


    public DanmakuDelegate(@NonNull LiveStreamPlaybackFragment fragment, IDanmakuView danmakuView, @NonNull LiveRoom liveRoom) {
        this.fragment = fragment;
        this.danmakuView = danmakuView;
        this.liveRoom = liveRoom;
    }

    public void init(){
        danmakuContext = DefaultDanmakuContext.create();
        danmakuParser = new BaseDanmakuParser() {
            @Override
            protected IDanmakus parse() {
                return new Danmakus();
            }
        };
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.showFPS(EnvConfig.DEBUG);
        danmakuView.show();
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                giftDanmakuManager = new GiftDanmakuManager(danmakuView);
                danmakuView.start();
                giftDanmakuManager.prepare();
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
                FragmentActivity activity = fragment.requireActivity();
                String message = activity.getString(R.string.toast_msg_danmu_connect_success);
                Log.d(TAG, message);
                ToastUtil.showShortToast(activity, message);
            }

            @Override
            public void onReceiveDanmu(String text, float textSize, int textColor, boolean textShadowTransparent, String msg) {
                addDanmaku(text, textSize, textColor, textShadowTransparent);
            }

            @Override
            public void onReceiveSuperChatMessage(String message, String messageFontColor, String uname, String msg) {
                if(scDanmakuType == BaseDanmaku.TYPE_FIX_BOTTOM
                        || scDanmakuType == BaseDanmaku.TYPE_FIX_TOP
                        || scDanmakuType == BaseDanmaku.TYPE_SCROLL_RL
                        || scDanmakuType == BaseDanmaku.TYPE_SCROLL_LR
                        || scDanmakuType == BaseDanmaku.TYPE_SPECIAL) {
                    String text = uname + ":" + message;
                    int color;
                    if(Strings.isNullOrEmpty(message)){
                        color = Color.WHITE;
                    }else{
                        color = Color.parseColor(messageFontColor);
                    }
                    addDanmaku(text, 25, color, false, scDanmakuType);
                }
            }

            @Override
            public void onReceiveSendGift(String action, String giftName, Integer num, String uname, String msg) {
                if(giftDanmakuEnable && giftDanmakuManager != null){
                    giftDanmakuManager.add(uname + action + giftName + "X" + num);
                }
            }

            @Override
            public void onReceiveOtherMessage(String message) {
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                FragmentActivity activity = fragment.requireActivity();
                String message = String.format(activity.getString(R.string.toast_msg_danmu_connect_error), reason);
                Log.d(TAG, message);
                ToastUtil.debug(fragment.requireActivity(), message);
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
                ToastUtil.showShortToast(
                        activity,
                        String.format(activity.getString(R.string.toast_msg_danmu_connect_error),
                                error.getLocalizedMessage()));
            }
        }
    }

    private void addDanmaku(String content, float textSize, int textColor, boolean textShadowTransparent) {
        addDanmaku(content, textSize, textColor, textShadowTransparent, BaseDanmaku.TYPE_SCROLL_RL);
    }

    private void addDanmaku(String content, float textSize, int textColor, boolean textShadowTransparent, int type){
        if(danmakuContext != null && danmakuParser !=null && danmakuView.isPrepared()){
            BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(type);
            if (danmaku != null) {
                textSize = textSize * (danmakuParser.getDisplayer().getDensity() - 0.6f);
                danmaku.text = content;
                if(type == BaseDanmaku.TYPE_FIX_BOTTOM){
                    int width = danmakuContext.getDisplayer().getWidth();
                    int maxSize = (int) (width / textSize) - 10;
                    int textLength = content.length();
                    int lineLength = (textLength + maxSize - 1) / maxSize;
                    String[] lines = new String[lineLength];
                    for(int i = 0; i < lineLength; i++){
                        lines[i] = content.substring(i * maxSize, Math.min(i * maxSize + maxSize, textLength));
                    }
                    danmaku.lines = lines;
                }
                danmaku.padding = 0;
                danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
                danmaku.isLive = true;
                danmaku.setTime(danmakuView.getCurrentTime() + 500);
                danmaku.textSize = textSize;
                danmaku.textColor = textColor;
                danmaku.textShadowColor = textShadowTransparent ? Color.TRANSPARENT : Color.BLACK;
                danmakuView.addDanmaku(danmaku);
            }
        }
    }

    public void pause(){
        if(danmakuContext != null && !danmakuView.isPaused()){
            danmakuView.pause();
        }
    }

    public void resume(){
        if (danmakuContext != null) {
            if(danmakuView.isPrepared() && danmakuView.isPaused()){
                danmakuView.resume();
            }
        }else{
            init();
        }
    }

    public void setPlayer(boolean isPlaying){
        if(isPlaying){
            if(danmakuContext != null && danmakuView.isPaused()){
                danmakuView.resume();
            }
        }else{
            pause();
        }
    }

    public void danmakuReleaseToggle(boolean enable){
        FragmentActivity activity = fragment.requireActivity();
        if(enable) {
            if(danmakuContext == null){
                init();
            }
            ToastUtil.showShortToast(activity, activity.getString(R.string.toast_msg_danmu_on));
        } else {
            if(danmakuContext != null){
                release();
            }
            ToastUtil.showShortToast(activity, activity.getString(R.string.toast_msg_danmu_off));
        }
    }

    public void danmakuSuperChatToggle(boolean enable){
        FragmentActivity activity = fragment.requireActivity();
        if(enable) {
            scDanmakuType = BaseDanmaku.TYPE_FIX_BOTTOM;
            ToastUtil.showShortToast(activity, activity.getString(R.string.toast_msg_sc_on));
        }else{
            if(scDanmakuType == BaseDanmaku.TYPE_FIX_BOTTOM
                    || scDanmakuType == BaseDanmaku.TYPE_FIX_TOP
                    || scDanmakuType == BaseDanmaku.TYPE_SCROLL_RL
                    || scDanmakuType == BaseDanmaku.TYPE_SCROLL_LR
                    || scDanmakuType == BaseDanmaku.TYPE_SPECIAL){
                scDanmakuType = 0;
            }
            ToastUtil.showShortToast(activity, activity.getString(R.string.toast_msg_sc_off));
        }
    }

    public void danmakuGiftToggle(boolean enable){
        FragmentActivity activity = fragment.requireActivity();
        giftDanmakuEnable = enable;
        String toastMsg = enable ? activity.getString(R.string.toast_msg_gift_on):
                activity.getString(R.string.toast_msg_gift_off);
        ToastUtil.showShortToast(activity, toastMsg);
    }

    public void release(){
        if(chatBroadcastWsClient != null){
            chatBroadcastWsClient.close();
            chatBroadcastWsClient = null;
        }
        if(danmakuView.isPrepared()){
            danmakuView.hide();
            danmakuView.removeAllDanmakus(true);
            danmakuView.release();
        }
        if(danmakuContext != null){
            danmakuContext = null;
        }
        if(danmakuParser != null){
            danmakuParser = null;
        }
    }
}

package com.muedsa.bilibililivetv.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.muedsa.bilibililiveapiclient.ChatBroadcastWsClient;
import com.muedsa.bilibililivetv.BuildConfig;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.player.danmaku.GiftDanmakuManager;
import com.muedsa.bilibililivetv.util.ToastUtil;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuSurfaceView;

public class DanmakuTestFragment extends Fragment {
    private static final String TAG = DanmakuTestFragment.class.getSimpleName();

    private DanmakuSurfaceView danmakuView;
    private DanmakuContext danmakuContext;
    private BaseDanmakuParser danmakuParser;
    private GiftDanmakuManager giftDanmakuManager;

    private ChatBroadcastWsClient chatBroadcastWsClient;

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
        maxLinesPair.put(BaseDanmaku.TYPE_FIX_BOTTOM, 10);
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
        //danmakuView.showFPS(true);
        danmakuView.show();
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                giftDanmakuManager = new GiftDanmakuManager(danmakuView);
                danmakuView.start();
                giftDanmakuManager.prepare();
                //timer1();
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

    private void timer1(){
        timer1 = new Timer();
        timer1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                giftDanmakuManager.add(randStr(5) + "投喂" + randStr(4) + "X3");
            }
        }, 1000, 200);
    }

    private void initChatBroadcast(){
        chatBroadcastWsClient = new ChatBroadcastWsClient(545068, "null");
        chatBroadcastWsClient.setCallBack(new ChatBroadcastWsClient.CallBack() {
            @Override
            public void onStart() {
                if(BuildConfig.DEBUG){
                    FragmentActivity activity = requireActivity();
                    ToastUtil.showLongToast(activity,
                            activity.getString(R.string.toast_msg_danmu_connect_success));
                }
            }

            @Override
            public void onReceiveDanmu(String text, float textSize, int textColor, boolean textShadowTransparent, String msg) {
                Log.d(TAG, "onReceiveDanmu: " + text);
            }

            @Override
            public void onReceiveSuperChatMessage(String message, String messageFontColor, String uname, String msg) {

            }

            @Override
            public void onReceiveSendGift(String action, String giftName, Integer num, String uname, String msg) {
                if(giftDanmakuManager != null) giftDanmakuManager.add(uname + action + giftName + "X" + num);
            }

            @Override
            public void onReceiveOtherMessage(String message) {
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.d(TAG, "ChatBroadcast close");
            }
        });
        try {
            chatBroadcastWsClient.start();
        }
        catch (Exception error){
            Log.d(TAG, "startChatBroadcastWsClient: ", error);
            FragmentActivity activity = requireActivity();
            ToastUtil.showShortToast(
                    activity,
                    String.format(activity.getString(R.string.toast_msg_danmu_connect_error),
                            error.getLocalizedMessage()));
        }
    }

    static final String CC = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
    static final int CC_LENGTH = CC.length();
    static final Random random = new Random();
    private static String randStr(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CC_LENGTH);
            sb.append(CC.charAt(index));
        }
        return sb.toString();
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

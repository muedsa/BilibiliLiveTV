package com.muedsa.bilibililivetv.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.leanback.app.VideoSupportFragment;
import androidx.leanback.app.VideoSupportFragmentGlueHost;
import androidx.leanback.media.PlaybackGlue;
import androidx.leanback.media.PlaybackTransportControlGlue;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.muedsa.bilibililiveapiclient.ChatBroadcastWsClient;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.model.LiveRoom;
import com.muedsa.bilibililivetv.player.LiveRoomPlaybackControlGlue;

import java.util.HashMap;
import java.util.Objects;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;

/**
 * Handles video playback with media controls.
 */
public class PlaybackVideoFragment extends VideoSupportFragment {

    private LiveRoomPlaybackControlGlue liveRoomPlaybackControlGlue;
    private LeanbackPlayerAdapter playerAdapter;
    private ExoPlayer exoPlayer;
    private LiveRoom liveRoom;
    private IDanmakuView danmakuView;
    private DanmakuContext danmakuContext;
    private BaseDanmakuParser danmakuParser;
    private ChatBroadcastWsClient chatBroadcastWsClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        liveRoom = (LiveRoom) getActivity().getIntent().getSerializableExtra(DetailsActivity.LIVE_ROOM);
        initDanmaku();
        initPlayer();
    }

    private void initDanmaku(){
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 9); // 滚动弹幕最大显示9行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        danmakuView = getActivity().findViewById(R.id.sv_danmaku);

        danmakuContext = DanmakuContext.create();
        danmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f)
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair)
                .setDanmakuTransparency(0.85f)
                .setDanmakuMargin(40);

        danmakuParser = new BaseDanmakuParser() {
            @Override
            protected IDanmakus parse() {
                return new Danmakus();
            }
        };
        danmakuView.prepare(danmakuParser, danmakuContext);
        danmakuView.showFPS(false);
        danmakuView.enableDanmakuDrawingCache(true);

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
    }

    private void initChatBroadcast(){
        chatBroadcastWsClient = new ChatBroadcastWsClient(liveRoom.getId(), liveRoom.getDanmuWsToken());
        chatBroadcastWsClient.setCallBack(new ChatBroadcastWsClient.CallBack() {
            @Override
            public void onReceiveDanmu(String text, float textSize, int textColor, boolean textShadowTransparent) {
                addDanmaku(text, textSize, textColor, textShadowTransparent);
            }

            @Override
            public void onClose() {
                if(exoPlayer.isPlaying()){
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
                error.printStackTrace();
                Toast.makeText(getActivity(), "连接弹幕服务器失败: " + error.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    private void addDanmaku(String content, float textSize, int textColor, boolean textShadowTransparent){
        if(danmakuContext != null){
            BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
            if (danmaku != null && danmakuView != null && danmakuParser != null) {
                danmaku.text = content;
                danmaku.padding = 5;
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


    private void initPlayer(){
        VideoSupportFragmentGlueHost glueHost =
                new VideoSupportFragmentGlueHost(PlaybackVideoFragment.this);

        Context context = getContext();
        Objects.requireNonNull(context);
        exoPlayer = new ExoPlayer.Builder(context)
                .setMediaSourceFactory(
                        new DefaultMediaSourceFactory(context).setLiveTargetOffsetMs(5000))
                .build();

        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(PlaybackException error) {
                Player.Listener.super.onPlayerError(error);
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }
        });

        playerAdapter = new LeanbackPlayerAdapter(context, exoPlayer, 50);
        liveRoomPlaybackControlGlue = new LiveRoomPlaybackControlGlue(context, playerAdapter);
        liveRoomPlaybackControlGlue.setHost(glueHost);
        liveRoomPlaybackControlGlue.setTitle(liveRoom.getTitle());
        liveRoomPlaybackControlGlue.setSubtitle(liveRoom.getUname());
        MediaItem mediaItem =
                new MediaItem.Builder()
                        .setUri(liveRoom.getPlayUrl())
                        .setLiveConfiguration(
                                new MediaItem.LiveConfiguration.Builder()
                                        .setMaxPlaybackSpeed(1.02f)
                                        .build())
                        .build();
        exoPlayer.setMediaItem(mediaItem);
        liveRoomPlaybackControlGlue.addPlayerCallback(new PlaybackGlue.PlayerCallback() {
            @Override
            public void onPlayStateChanged(PlaybackGlue glue) {
                super.onPlayStateChanged(glue);
                if(glue.isPlaying()){
                    if(danmakuView != null && danmakuView.isPaused()){
                        danmakuView.resume();
                    }
                }else{
                    if(danmakuView != null && !danmakuView.isPaused()){
                        danmakuView.pause();
                    }
                }
            }

            @Override
            public void onPlayCompleted(PlaybackGlue glue) {
                super.onPlayCompleted(glue);
            }
        });
        liveRoomPlaybackControlGlue.play();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (liveRoomPlaybackControlGlue != null && liveRoomPlaybackControlGlue.isPlaying()) {
            liveRoomPlaybackControlGlue.pause();
        }
        if(danmakuView != null && !danmakuView.isPaused()){
            danmakuView.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(liveRoomPlaybackControlGlue != null){
            if(liveRoomPlaybackControlGlue.isPrepared() && !liveRoomPlaybackControlGlue.isPlaying()){
                liveRoomPlaybackControlGlue.play();
            }
        }else{
            initPlayer();
        }
        if(liveRoomPlaybackControlGlue != null && liveRoomPlaybackControlGlue.isPrepared() && !liveRoomPlaybackControlGlue.isPlaying()){
            liveRoomPlaybackControlGlue.play();
        }
        if (danmakuView != null) {
            if(danmakuView.isPrepared() && danmakuView.isPaused()){
                danmakuView.resume();
            }
        }else{
            initDanmaku();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
            playerAdapter = null;
            liveRoomPlaybackControlGlue = null;
        }
        if(danmakuView != null){
            danmakuView.release();
            danmakuView = null;
        }
        if(chatBroadcastWsClient != null){
            chatBroadcastWsClient.close();
            chatBroadcastWsClient = null;
        }
    }
}
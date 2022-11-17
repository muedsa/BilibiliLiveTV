package com.muedsa.bilibililivetv.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.leanback.app.VideoSupportFragment;
import androidx.leanback.app.VideoSupportFragmentGlueHost;
import androidx.leanback.media.PlaybackTransportControlGlue;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.EventLogger;
import com.muedsa.bilibililivetv.BuildConfig;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.activity.VideoDetailsActivity;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;
import com.muedsa.bilibililivetv.model.VideoPlayInfo;
import com.muedsa.bilibililivetv.player.DefaultDanmakuContext;
import com.muedsa.bilibililivetv.player.video.BilibiliDanmakuParser;
import com.muedsa.bilibililivetv.util.ToastUtil;
import com.muedsa.httpjsonclient.HttpClientContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuSurfaceView;

public class VideoPlaybackFragment extends VideoSupportFragment {
    private static final String TAG = VideoPlaybackFragment.class.getSimpleName();

    private long lastSyncTime = 0;

    private ExoPlayer exoPlayer;
    private PlaybackTransportControlGlue<LeanbackPlayerAdapter> glue;

    private DanmakuSurfaceView danmakuView;
    private DanmakuContext danmakuContext;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);
        danmakuView = (DanmakuSurfaceView) LayoutInflater.from(getContext())
                .inflate(R.layout.danmaku_surface, root, false);
        if (root != null) root.addView(danmakuView, 0);
        VideoPlayInfo videoPlayInfo = (VideoPlayInfo) requireActivity().getIntent().getSerializableExtra(VideoDetailsActivity.PLAY_INFO);
        if (Objects.nonNull(videoPlayInfo)
                && Objects.nonNull(videoPlayInfo.getVideoUrl())
                && Objects.nonNull(videoPlayInfo.getAudioUrl())) {
            initExoPlayer();
            initDanmakuView();
            prepareVideo(videoPlayInfo);
            prepareDanmaku(videoPlayInfo);
        }
        return root;
    }

    private void initExoPlayer() {
        Context context = requireContext();
        VideoSupportFragmentGlueHost videoSupportFragmentGlueHost = new VideoSupportFragmentGlueHost(this);
        DefaultRenderersFactory renderersFactory =
                new DefaultRenderersFactory(context)
                        .forceEnableMediaCodecAsynchronousQueueing();
        exoPlayer = new ExoPlayer.Builder(context, renderersFactory)
                .setMediaSourceFactory(new DefaultMediaSourceFactory(context))
                .build();
        exoPlayer.addAnalyticsListener(new EventLogger());
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                Log.d(TAG, "onPlaybackStateChanged: " + playbackState);
                Player.Listener.super.onPlaybackStateChanged(playbackState);
                if (Player.STATE_BUFFERING == playbackState) {
                    if (Objects.nonNull(danmakuView)) {
                        danmakuView.pause();
                    }
                } else if (Player.STATE_READY == playbackState) {
                    checkAllReadyAndStart();
                }
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                Log.d(TAG, "onIsPlayingChanged: " + isPlaying);
                Player.Listener.super.onIsPlayingChanged(isPlaying);
                if (isPlaying) {
                    checkAllReadyAndStart();
                } else {
                    if (Objects.nonNull(danmakuView)) {
                        danmakuView.pause();
                    }
                }
            }

            @Override
            public void onPlayerError(@NonNull PlaybackException error) {
                Player.Listener.super.onPlayerError(error);
                ToastUtil.showLongToast(context, error.getMessage());
            }
        });
        LeanbackPlayerAdapter playerAdapter = new LeanbackPlayerAdapter(context, exoPlayer, 50);
        glue = new PlaybackTransportControlGlue<>(context, playerAdapter);
        glue.setHost(videoSupportFragmentGlueHost);
        glue.setSeekEnabled(true);
    }

    private void initDanmakuView() {
        danmakuContext = DefaultDanmakuContext.create();
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.showFPS(BuildConfig.DEBUG);
        danmakuView.show();
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                Log.d(TAG, "danmakuView prepared");
                checkAllReadyAndStart();
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

    private void prepareVideo(VideoPlayInfo videoPlayInfo) {
        glue.setTitle(videoPlayInfo.getTitle());
        glue.setSubtitle(videoPlayInfo.getSubTitle());
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpClientContainer.HEADER_KEY_COOKIE, BilibiliLiveApi.client().getCookies());
        headers.put(HttpClientContainer.HEADER_KEY_USER_AGENT, HttpClientContainer.HEADER_VALUE_USER_AGENT);
        headers.put(HttpClientContainer.HEADER_KEY_REFERER, videoPlayInfo.getReferer());
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory().setDefaultRequestProperties(headers);
        MediaSource videoMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(videoPlayInfo.getVideoUrl()));
        MediaSource audioMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(videoPlayInfo.getAudioUrl()));
        MergingMediaSource mergedSource = new MergingMediaSource(videoMediaSource, audioMediaSource);
        exoPlayer.setMediaSource(mergedSource);
        exoPlayer.prepare();
    }

    private void prepareDanmaku(VideoPlayInfo videoPlayInfo) {
        BaseDanmakuParser danmakuParser = new BilibiliDanmakuParser(videoPlayInfo.getCid());
        danmakuParser.setConfig(danmakuContext);
        danmakuView.prepare(danmakuParser, danmakuContext);
    }

    private void checkAllReadyAndStart() {
        long now = System.currentTimeMillis();
        if(lastSyncTime < now && now - lastSyncTime > 100) {
            lastSyncTime = now;
            requireActivity().runOnUiThread(() -> {
                if (Objects.nonNull(exoPlayer)
                        && Objects.nonNull(danmakuView)
                        && Player.STATE_READY == exoPlayer.getPlaybackState()
                        && danmakuView.isPrepared()) {
                    if (!glue.isPlaying()) {
                        glue.play();
                    }
                    danmakuView.start(exoPlayer.getCurrentPosition());
                }
            });
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        if (Objects.nonNull(exoPlayer) && Objects.nonNull(glue)) {
            glue.pause();
        }
        if (Objects.nonNull(danmakuView)) {
            danmakuView.pause();
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        if (Objects.nonNull(exoPlayer) && Objects.nonNull(glue) && Objects.nonNull(danmakuView)) {
            checkAllReadyAndStart();
        }
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
        if (Objects.nonNull(exoPlayer) && Objects.nonNull(glue)) {
            glue = null;
            exoPlayer.release();
            exoPlayer = null;
        }
        if (Objects.nonNull(danmakuView)) {
            danmakuView.release();
            danmakuView = null;
        }
        if (Objects.nonNull(danmakuContext)) {
            danmakuContext = null;
        }
    }
}
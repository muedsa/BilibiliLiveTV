package com.muedsa.bilibililivetv.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
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
import com.muedsa.bilibililivetv.activity.VideoDetailsActivity;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;
import com.muedsa.bilibililivetv.model.VideoPlayInfo;
import com.muedsa.bilibililivetv.util.ToastUtil;
import com.muedsa.httpjsonclient.Container;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VideoPlaybackFragment extends VideoSupportFragment {
    private static final String TAG = VideoPlaybackFragment.class.getSimpleName();

    private ExoPlayer exoPlayer;
    private PlaybackTransportControlGlue<LeanbackPlayerAdapter> playbackTransportControlGlue;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);
//        DanmakuSurfaceView danmakuView = (DanmakuSurfaceView) LayoutInflater.from(getContext()).inflate(
//                R.layout.danmaku_surface, root, false);
//        if(root != null) root.addView(danmakuView, 0);
        VideoPlayInfo playInfo = (VideoPlayInfo) requireActivity().getIntent().getSerializableExtra(VideoDetailsActivity.PLAY_INFO);
        if (Objects.nonNull(playInfo) && Objects.nonNull(playInfo.getVideoUrl()) && Objects.nonNull(playInfo.getAudioUrl())) {
            initPlayer();
            playVideo(playInfo);
        }
        return root;
    }

    private void initPlayer() {
        FragmentActivity activity = requireActivity();
        VideoSupportFragmentGlueHost glueHost =
                new VideoSupportFragmentGlueHost(this);
        DefaultRenderersFactory renderersFactory =
                new DefaultRenderersFactory(activity)
                        .forceEnableMediaCodecAsynchronousQueueing();
        exoPlayer = new ExoPlayer.Builder(activity, renderersFactory)
                .setMediaSourceFactory(new DefaultMediaSourceFactory(activity))
                .build();
        exoPlayer.addAnalyticsListener(new EventLogger());
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(@NonNull PlaybackException error) {
                Player.Listener.super.onPlayerError(error);
                ToastUtil.showLongToast(activity, error.getMessage());
            }
        });
        LeanbackPlayerAdapter playerAdapter = new LeanbackPlayerAdapter(activity, exoPlayer, 50);
        playbackTransportControlGlue = new PlaybackTransportControlGlue<>(activity, playerAdapter);
        playbackTransportControlGlue.setHost(glueHost);
    }

    private void playVideo(VideoPlayInfo videoPlayInfo) {
        playbackTransportControlGlue.setTitle(videoPlayInfo.getTitle());
        playbackTransportControlGlue.setSubtitle(videoPlayInfo.getSubTitle());
        Map<String, String> headers = new HashMap<>();
        headers.put(Container.HEADER_KEY_COOKIE, BilibiliLiveApi.client().getCookies());
        headers.put(Container.HEADER_KEY_USER_AGENT, Container.HEADER_VALUE_USER_AGENT);
        headers.put(Container.HEADER_KEY_REFERER, videoPlayInfo.getReferer());
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory().setDefaultRequestProperties(headers);
        MediaSource videoMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(videoPlayInfo.getVideoUrl()));
        MediaSource audioMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(videoPlayInfo.getAudioUrl()));
        MergingMediaSource mergedSource = new MergingMediaSource(videoMediaSource, audioMediaSource);
        exoPlayer.setMediaSource(mergedSource);
        exoPlayer.prepare();
        playbackTransportControlGlue.play();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        playbackTransportControlGlue.pause();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        playbackTransportControlGlue.play();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
        exoPlayer.release();
    }
}
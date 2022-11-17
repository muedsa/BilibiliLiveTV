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
import com.muedsa.bilibililivetv.util.ToastUtil;
import com.muedsa.httpjsonclient.HttpClientContainer;

import java.util.HashMap;
import java.util.Map;

public class VideoTestFragment extends VideoSupportFragment {
    private static final String TAG = VideoTestFragment.class.getSimpleName();

    private ExoPlayer exoPlayer;
    private PlaybackTransportControlGlue<LeanbackPlayerAdapter> playbackTransportControlGlue;
    private LeanbackPlayerAdapter playerAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
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
        playerAdapter = new LeanbackPlayerAdapter(activity, exoPlayer, 50);
        playbackTransportControlGlue = new PlaybackTransportControlGlue<>(activity, playerAdapter);
        playbackTransportControlGlue.setHost(glueHost);
        playbackTransportControlGlue.setTitle("TEST");
        playbackTransportControlGlue.setSubtitle("test 233");
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpClientContainer.HEADER_KEY_COOKIE, "");
        headers.put(HttpClientContainer.HEADER_KEY_USER_AGENT, HttpClientContainer.HEADER_VALUE_USER_AGENT);
        headers.put("referer", "https://www.bilibili.com/video/BV19e411K7LG");
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory()
                .setDefaultRequestProperties(headers);
        String videoUrl = "https://xy49x86x255x19xy.mcdn.bilivideo.cn:4483/upgcxcode/83/96/842729683/842729683_nb3-1-30080.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1667895250&gen=playurlv2&os=mcdn&oi=1961259069&trid=0000a6d8ba3e6cb34c05bd2429fc4a08a27au&mid=7443740&platform=pc&upsig=81d008f328ff666141e068756c036472&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,mid,platform&mcdnid=17000061&bvc=vod&nettype=0&orderid=0,3&buvid=&build=0&agrr=1&bw=41408&logo=A0010000";
        String audioUrl = "https://xy49x86x255x19xy.mcdn.bilivideo.cn:4483/upgcxcode/83/96/842729683/842729683_nb3-1-30280.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1667895250&gen=playurlv2&os=mcdn&oi=1961259069&trid=0000a6d8ba3e6cb34c05bd2429fc4a08a27au&mid=7443740&platform=pc&upsig=4abf0b6552fbb31b8efb21d6ed09fe09&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,mid,platform&mcdnid=17000061&bvc=vod&nettype=0&orderid=0,3&buvid=&build=0&agrr=1&bw=22962&logo=A0010000";
//        MediaSource videoMediaSource =
//                new DashMediaSource.Factory(dataSourceFactory)
//                        .createMediaSource(MediaItem.fromUri(videoUrl));
//        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
//        MediaSource audioMediaSource =
//                new DashMediaSource.Factory(dataSourceFactory)
//                        .createMediaSource(MediaItem.fromUri(audioUrl));
        MediaSource videoMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(videoUrl));
        MediaSource audioMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(audioUrl));
        MergingMediaSource mergedSource = new MergingMediaSource(videoMediaSource, audioMediaSource);
        exoPlayer.setMediaSource(mergedSource);
        exoPlayer.prepare();
        playbackTransportControlGlue.play();
        return root;
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

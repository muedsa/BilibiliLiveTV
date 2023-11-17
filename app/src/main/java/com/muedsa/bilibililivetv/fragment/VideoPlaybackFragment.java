package com.muedsa.bilibililivetv.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.app.VideoSupportFragment;
import androidx.leanback.app.VideoSupportFragmentGlueHost;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.common.text.CueGroup;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.DefaultRenderersFactory;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.MergingMediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;
import androidx.media3.exoplayer.util.EventLogger;
import androidx.media3.extractor.Extractor;
import androidx.media3.extractor.ExtractorsFactory;
import androidx.media3.extractor.text.SubtitleExtractor;
import androidx.media3.ui.SubtitleView;
import androidx.media3.ui.leanback.LeanbackPlayerAdapter;

import com.alibaba.fastjson2.JSON;
import com.muedsa.bilibililiveapiclient.model.video.Heartbeat;
import com.muedsa.bilibililiveapiclient.model.video.VideoSubtitle;
import com.muedsa.bilibililivetv.EnvConfig;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.activity.VideoDetailsActivity;
import com.muedsa.bilibililivetv.container.BilibiliLiveApi;
import com.muedsa.bilibililivetv.model.VideoPlayInfo;
import com.muedsa.bilibililivetv.player.DefaultDanmakuContext;
import com.muedsa.bilibililivetv.player.TrackSelectionDialogBuilder;
import com.muedsa.bilibililivetv.player.video.BilibiliDanmakuParser;
import com.muedsa.bilibililivetv.player.video.BilibiliJsonSubtitleParser;
import com.muedsa.bilibililivetv.player.video.BilibiliVideoPlaybackTransportControlGlue;
import com.muedsa.bilibililivetv.request.RxRequestFactory;
import com.muedsa.bilibililivetv.util.CrashlyticsUtil;
import com.muedsa.bilibililivetv.util.ToastUtil;
import com.muedsa.httpjsonclient.HttpClientContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.internal.functions.Functions;
import io.reactivex.rxjava3.schedulers.Schedulers;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuSurfaceView;

@OptIn(markerClass = UnstableApi.class)
public class VideoPlaybackFragment extends VideoSupportFragment {
    private static final String TAG = VideoPlaybackFragment.class.getSimpleName();

    private long lastSyncTime = 0;

    private DataSource.Factory dataSourceFactory;
    private MediaSource.Factory mediaSourceFactory;
    private DefaultTrackSelector trackSelector;
    private ExoPlayer exoPlayer;
    private BilibiliVideoPlaybackTransportControlGlue glue;

    private DanmakuSurfaceView danmakuView;
    private DanmakuContext danmakuContext;

    private SubtitleView subtitleView;

    private Dialog subtitleTrackSelectionDialog;

    private Timer heartbeatTimer;

    private Heartbeat heartbeat;

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);
        danmakuView = (DanmakuSurfaceView) LayoutInflater.from(getContext())
                .inflate(R.layout.danmaku_surface, root, false);
        View subtitleLayout = LayoutInflater.from(getContext())
                .inflate(R.layout.subtitle_layout, root, false);
        subtitleView = subtitleLayout.findViewById(R.id.subtitle_view);
        subtitleView.setUserDefaultStyle();
        if (Objects.nonNull(root)) {
            ViewGroup playbackRoot = root.findViewById(androidx.leanback.R.id.playback_fragment_root);
            if (Objects.nonNull(playbackRoot)) {
                playbackRoot.addView(subtitleLayout, 1);
                playbackRoot.addView(danmakuView, 1);
            }
        }
        VideoPlayInfo videoPlayInfo = (VideoPlayInfo) requireActivity().getIntent().getSerializableExtra(VideoDetailsActivity.PLAY_INFO);
        if (Objects.nonNull(videoPlayInfo)
                && Objects.nonNull(videoPlayInfo.getVideoUrl())
                && Objects.nonNull(videoPlayInfo.getAudioUrl())) {
            initExoPlayer(videoPlayInfo);
            initDanmakuView();
            prepareVideo(videoPlayInfo);
            prepareDanmaku(videoPlayInfo);
            prepareHeartbeatTimer(videoPlayInfo);
        }
        return root;
    }

    private void initExoPlayer(VideoPlayInfo videoPlayInfo) {
        Context context = requireContext();
        VideoSupportFragmentGlueHost videoSupportFragmentGlueHost = new VideoSupportFragmentGlueHost(this);
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpClientContainer.HEADER_KEY_COOKIE, BilibiliLiveApi.client().getCookies());
        headers.put(HttpClientContainer.HEADER_KEY_USER_AGENT, HttpClientContainer.HEADER_VALUE_USER_AGENT);
        headers.put(HttpClientContainer.HEADER_KEY_REFERER, videoPlayInfo.getReferer());
        dataSourceFactory = new DefaultHttpDataSource.Factory()
                .setDefaultRequestProperties(headers);
        mediaSourceFactory = new ProgressiveMediaSource.Factory(dataSourceFactory);
        DefaultRenderersFactory renderersFactory =
                new DefaultRenderersFactory(context)
                        .forceEnableMediaCodecAsynchronousQueueing();
        trackSelector = new DefaultTrackSelector(context);
        changeSubtitle("zh");
        exoPlayer = new ExoPlayer.Builder(context, renderersFactory)
                .setMediaSourceFactory(mediaSourceFactory)
                .setTrackSelector(trackSelector)
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
                    ToastUtil.debug(() -> requireContext(), "exoplayer ready");
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
            public void onCues(@NonNull CueGroup cueGroup) {
                Player.Listener.super.onCues(cueGroup);
                if (Objects.nonNull(subtitleView) && Objects.nonNull(cueGroup)) {
                    subtitleView.setCues(cueGroup.cues);
                }
            }

            @Override
            public void onPlayerError(@NonNull PlaybackException error) {
                Player.Listener.super.onPlayerError(error);
                ToastUtil.showLongToast(context, error.getMessage());
                CrashlyticsUtil.log(error);
                CrashlyticsUtil.log("playback error, source: " + JSON.toJSONString(videoPlayInfo));
            }
        });
        LeanbackPlayerAdapter playerAdapter = new LeanbackPlayerAdapter(context, exoPlayer, 50);
        glue = new BilibiliVideoPlaybackTransportControlGlue(context, playerAdapter);
        glue.setHost(videoSupportFragmentGlueHost);
        glue.setControlsOverlayAutoHideEnabled(true);
        glue.setSeekEnabled(true);
        glue.addPlayerCallback(new BilibiliVideoPlaybackTransportControlGlue.BilibiliVideoPlayerCallback() {
            @Override
            public void onSubtitleToggle() {
                Log.d(TAG, "onSubtitleToggle");
                super.onSubtitleToggle();
                showSubtitleDialog();
            }
        });
    }

    private void initDanmakuView() {
        danmakuContext = DefaultDanmakuContext.create();
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.showFPS(EnvConfig.DEBUG);
        danmakuView.show();
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                Log.d(TAG, "danmakuView prepared");
                ToastUtil.debug(() -> requireContext(), "danmaku ready");
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
        MediaSource videoMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(videoPlayInfo.getVideoUrl()));
        MediaSource audioMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(videoPlayInfo.getAudioUrl()));
        //MergingMediaSource mergedSource = new MergingMediaSource(videoMediaSource, audioMediaSource);
        MediaSource mergedSource = buildSubtitleMediaSourceList(videoPlayInfo.getSubtitleList(), videoMediaSource, audioMediaSource);
        exoPlayer.setMediaSource(mergedSource);
        exoPlayer.prepare();
    }

    private void prepareDanmaku(VideoPlayInfo videoPlayInfo) {
        BaseDanmakuParser danmakuParser = new BilibiliDanmakuParser(videoPlayInfo.getCid(), videoPlayInfo.getDanmakuSegmentSize());
        danmakuParser.setConfig(danmakuContext);
        danmakuView.prepare(danmakuParser, danmakuContext);
    }

    private MediaSource buildSubtitleMediaSourceList(List<VideoSubtitle> subtitleList,
                                                     MediaSource videoMediaSource,
                                                     MediaSource audioMediaSource) {
        subtitleList = subtitleList.stream().filter(i -> !"ai-zh".equals(i.getLan())).collect(Collectors.toList());
        MediaSource[] arr = new MediaSource[subtitleList.size() + 2];
        arr[0] = videoMediaSource;
        arr[1] = audioMediaSource;
        int i = 2;
        for (VideoSubtitle videoSubtitle : subtitleList) {
            Format format =
                    new Format.Builder()
                            .setSampleMimeType(MimeTypes.BASE_TYPE_APPLICATION + "/bilibili-json-sub")
                            .setLanguage(videoSubtitle.getLan())
                            .setSelectionFlags(C.SELECTION_FLAG_AUTOSELECT)
                            .setRoleFlags(0)
                            .setLabel(videoSubtitle.getLan_doc())
                            .setId(videoSubtitle.getIdStr())
                            .build();
            ExtractorsFactory extractorsFactory = () -> new Extractor[]{
                    new SubtitleExtractor(new BilibiliJsonSubtitleParser(), format)};
            ProgressiveMediaSource.Factory subtitleMediaSourceFactory =
                    new ProgressiveMediaSource.Factory(dataSourceFactory, extractorsFactory);
            arr[i++] = subtitleMediaSourceFactory
                    .createMediaSource(MediaItem.fromUri(videoSubtitle.getSubtitleUrl()));
        }
        return new MergingMediaSource(arr);
    }

    private void showSubtitleDialog() {
        if (Objects.isNull(subtitleTrackSelectionDialog) && Objects.nonNull(exoPlayer)) {
            subtitleTrackSelectionDialog = new TrackSelectionDialogBuilder(requireContext(),
                    "选择字幕", exoPlayer, C.TRACK_TYPE_TEXT)
                    .setShowDisableOption(true)
                    .setTrackNameProvider(f -> {
                        Log.d(TAG, "f:" + f.label + "|" + f.codecs + "|" + f.language);
                        return f.label;
                    })
                    .build();
        }
        if (Objects.nonNull(subtitleTrackSelectionDialog)) {
            subtitleTrackSelectionDialog.show();
        }
    }

    private void checkAllReadyAndStart() {
        long now = System.currentTimeMillis();
        if (lastSyncTime < now && now - lastSyncTime > 100) {
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

    private void changeSubtitle(String language) {
        if (Objects.nonNull(trackSelector)) {
            trackSelector.setParameters(trackSelector
                    .buildUponParameters()
                    .setPreferredTextLanguage(language)
                    .build());
        }
    }

    private void prepareHeartbeatTimer(VideoPlayInfo videoPlayInfo) {
        if (heartbeatTimer == null) {
            heartbeatTimer = new Timer();
            long current = System.currentTimeMillis() / 1000;
            heartbeat = new Heartbeat();
            heartbeat.setBvid(videoPlayInfo.getBv());
            heartbeat.setCid(videoPlayInfo.getCid());
            heartbeat.setType(3);
            heartbeat.setQuality(videoPlayInfo.getQuality());
            heartbeat.setVideoDuration(videoPlayInfo.getVideoDuration());
            heartbeat.setStartTs(current);
            heartbeat.setRealtime(0L);
            heartbeatTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    FragmentActivity activity = getActivity();
                    if (activity != null) {
                        activity.runOnUiThread(() -> {
                            if (exoPlayer.isPlaying()) {
                                long progress = glue.getCurrentPosition() / 1000;
                                heartbeat.setPlayedTime(progress);
                                heartbeat.setLastPlayProgressTime(progress);
                                heartbeat.setPlayType(0);
                                Log.d(TAG, "heartbeat");
                                RxRequestFactory.bilibiliVideoHeartbeat(heartbeat)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(Schedulers.io())
                                        .subscribe(Functions.emptyConsumer(),
                                                (throwable -> Log.d(TAG, "heartbeat error", throwable)),
                                                disposable);
                            }
                        });
                    }
                }
            }, 15000, 15000);
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
        if (Objects.nonNull(subtitleView)) {
            subtitleView = null;
        }

        if (Objects.nonNull(heartbeatTimer)) {
            heartbeatTimer.cancel();
            heartbeatTimer.purge();
        }
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
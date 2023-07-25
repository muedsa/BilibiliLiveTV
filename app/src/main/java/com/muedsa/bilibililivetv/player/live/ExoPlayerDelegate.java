package com.muedsa.bilibililivetv.player.live;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.app.VideoSupportFragmentGlueHost;
import androidx.leanback.media.PlaybackGlue;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory;
import androidx.media3.ui.leanback.LeanbackPlayerAdapter;

import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.fragment.LiveStreamPlaybackFragment;
import com.muedsa.bilibililivetv.room.model.LiveRoom;
import com.muedsa.bilibililivetv.util.CrashlyticsUtil;
import com.muedsa.bilibililivetv.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ExoPlayerDelegate {

    private final LiveStreamPlaybackFragment fragment;
    private final LiveRoom liveRoom;

    private BilibiliLivePlaybackTransportControlGlue glue;
    private LeanbackPlayerAdapter playerAdapter;
    private ExoPlayer exoPlayer;
    private List<MediaItem> mediaItemList;

    private final Listener listener;

    public ExoPlayerDelegate(@NonNull LiveStreamPlaybackFragment fragment, @Nullable Listener listener, LiveRoom liveRoom) {
        this.fragment = fragment;
        this.listener = listener;
        this.liveRoom = liveRoom;
    }

    public void init() {
        VideoSupportFragmentGlueHost glueHost =
                new VideoSupportFragmentGlueHost(fragment);

        FragmentActivity activity = fragment.requireActivity();
        exoPlayer = new ExoPlayer.Builder(activity)
                .setMediaSourceFactory(
                        new DefaultMediaSourceFactory(activity).setLiveTargetOffsetMs(5000))
                .build();

        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(@NonNull PlaybackException error) {
                Player.Listener.super.onPlayerError(error);
                ToastUtil.showLongToast(activity, error.getMessage());
                CrashlyticsUtil.log(error);
            }
        });
        playerAdapter = new LeanbackPlayerAdapter(activity, exoPlayer, 50);
        glue = new BilibiliLivePlaybackTransportControlGlue(activity, playerAdapter);
        glue.setHost(glueHost);
        glue.setControlsOverlayAutoHideEnabled(true);
        glue.setTitle(liveRoom.getTitle());
        glue.setSubtitle(liveRoom.getUname());
        glue.addPlayerCallback(new BilibiliLivePlaybackTransportControlGlue.LiveRoomPlayerCallback() {
            @Override
            public void onPlayStateChanged(PlaybackGlue glue) {
                super.onPlayStateChanged(glue);
                if (listener != null) listener.onPlayStateChanged(glue);
            }

            @Override
            public void onPlayCompleted(PlaybackGlue glue) {
                super.onPlayCompleted(glue);
            }

            @Override
            public void onDanmakuToggle(boolean enabled) {
                super.onDanmakuToggle(enabled);
                if (listener != null) listener.onDanmuToggle(enabled);
            }

            @Override
            public void onLiveUrlChange(PlaybackGlue glue) {
                super.onLiveUrlChange(glue);
                exoPlayer.seekToNextMediaItem();
                FragmentActivity activity = fragment.requireActivity();
                ToastUtil.showShortToast(activity,
                        String.format(activity.getString(R.string.total_msg_change_play_url),
                                exoPlayer.getCurrentMediaItemIndex() + 1));
            }

            @Override
            public void onSuperChatToggle(boolean enable) {
                super.onSuperChatToggle(enable);
                if (listener != null) listener.onDanmakuSuperChatToggle(enable);
            }

            @Override
            public void onGiftToggle(boolean enable) {
                super.onGiftToggle(enable);
                if (listener != null) listener.onDanmakuGiftToggle(enable);
            }
        });
        mediaItemList = new ArrayList<>();
        if (liveRoom.getPlayUrlArr() != null) {
            for (String playUrl : liveRoom.getPlayUrlArr()) {
                mediaItemList.add(new MediaItem.Builder()
                        .setUri(playUrl)
                        .setLiveConfiguration(
                                new MediaItem.LiveConfiguration.Builder()
                                        .setMaxPlaybackSpeed(1.02f)
                                        .build())
                        .build());
            }
        }
        if (mediaItemList.size() > 0) {
            exoPlayer.setMediaItems(mediaItemList);
            exoPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
            glue.play();
        } else {
            ToastUtil.showLongToast(activity, activity.getString(R.string.live_play_failure));
        }
    }

    public boolean isPlaying() {
        boolean flag = false;
        if (glue != null) {
            flag = glue.isPlaying();
        }
        return flag;
    }

    public void pause() {
        if (glue != null && glue.isPlaying()) {
            glue.pause();
        }
    }

    public void resume() {
        if (glue != null) {
            if (glue.isPrepared()
                    && !glue.isPlaying()) {
                glue.play();
            }
        } else {
            init();
        }
    }

    public void release() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
            playerAdapter = null;
            glue = null;
        }
    }

    public interface Listener {
        void onPlayStateChanged(PlaybackGlue glue);

        void onDanmuToggle(boolean enable);

        void onDanmakuSuperChatToggle(boolean enable);

        void onDanmakuGiftToggle(boolean enable);
    }
}

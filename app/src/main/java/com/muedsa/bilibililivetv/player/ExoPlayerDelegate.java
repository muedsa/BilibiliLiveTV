package com.muedsa.bilibililivetv.player;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.app.VideoSupportFragmentGlueHost;
import androidx.leanback.media.PlaybackGlue;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.fragment.PlaybackVideoFragment;
import com.muedsa.bilibililivetv.room.model.LiveRoom;
import com.muedsa.bilibililivetv.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ExoPlayerDelegate {

    private final PlaybackVideoFragment fragment;
    private final LiveRoom liveRoom;

    private PlaybackTransportControlGlue playbackTransportControlGlue;
    private LeanbackPlayerAdapter playerAdapter;
    private ExoPlayer exoPlayer;
    private List<MediaItem> mediaItemList;

    private Listener listener;

    public ExoPlayerDelegate(@NonNull PlaybackVideoFragment fragment, @Nullable Listener listener, LiveRoom liveRoom){
        this.fragment = fragment;
        this.listener = listener;
        this.liveRoom = liveRoom;
    }

    public void init(){
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
            }
        });
        playerAdapter = new LeanbackPlayerAdapter(activity, exoPlayer, 50);
        playbackTransportControlGlue = new PlaybackTransportControlGlue(activity, playerAdapter);
        playbackTransportControlGlue.setHost(glueHost);
        playbackTransportControlGlue.setTitle(liveRoom.getTitle());
        playbackTransportControlGlue.setSubtitle(liveRoom.getUname());
        playbackTransportControlGlue.addPlayerCallback(new PlaybackTransportControlGlue.LiveRoomPlayerCallback() {
            @Override
            public void onPlayStateChanged(PlaybackGlue glue) {
                super.onPlayStateChanged(glue);
                if(listener != null) listener.onPlayStateChanged(glue);
            }

            @Override
            public void onPlayCompleted(PlaybackGlue glue) {
                super.onPlayCompleted(glue);
            }

            @Override
            public void onDanmakuToggle(boolean enabled) {
                super.onDanmakuToggle(enabled);
                if(listener != null) listener.onDanmuToggle(enabled);
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
                if(listener != null) listener.onDanmakuSuperChatToggle(enable);
            }
        });
        mediaItemList = new ArrayList<>();
        if(liveRoom.getPlayUrlArr() != null){
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
        if(mediaItemList.size() > 0){
            exoPlayer.setMediaItems(mediaItemList);
            exoPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
            playbackTransportControlGlue.play();
        }else{
            ToastUtil.showLongToast(activity, activity.getString(R.string.live_play_failure));
        }
    }

    public boolean isPlaying(){
        boolean flag = false;
        if(playbackTransportControlGlue != null){
            flag = playbackTransportControlGlue.isPlaying();
        }
        return flag;
    }

    public void pause(){
        if (playbackTransportControlGlue != null && playbackTransportControlGlue.isPlaying()) {
            playbackTransportControlGlue.pause();
        }
    }

    public void resume(){
        if(playbackTransportControlGlue != null){
            if(playbackTransportControlGlue.isPrepared()
                    && !playbackTransportControlGlue.isPlaying()){
                playbackTransportControlGlue.play();
            }
        }else{
            init();
        }
    }

    public void release(){
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
            playerAdapter = null;
            playbackTransportControlGlue = null;
        }
    }

    public interface Listener {
        void onPlayStateChanged(PlaybackGlue glue);
        void onDanmuToggle(boolean on);
        void onDanmakuSuperChatToggle(boolean on);
    }
}

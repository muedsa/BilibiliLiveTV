package com.muedsa.bilibililivetv.player;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.app.VideoSupportFragment;
import androidx.leanback.app.VideoSupportFragmentGlueHost;
import androidx.leanback.media.PlaybackGlue;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.room.model.LiveRoom;

import java.util.ArrayList;
import java.util.List;

public class ExoPlayerDelegate {

    private final VideoSupportFragment fragment;
    private final LiveRoom liveRoom;

    private PlaybackTransportControlGlue playbackTransportControlGlue;
    private LeanbackPlayerAdapter playerAdapter;
    private ExoPlayer exoPlayer;
    private List<MediaItem> mediaItemList;

    private Listener listener;

    public ExoPlayerDelegate(@NonNull VideoSupportFragment fragment, @Nullable Listener listener, LiveRoom liveRoom){
        this.fragment = fragment;
        this.listener = listener;
        this.liveRoom = liveRoom;
    }

    public void init(){
        VideoSupportFragmentGlueHost glueHost =
                new VideoSupportFragmentGlueHost(fragment);

        Context context = fragment.requireContext();
        exoPlayer = new ExoPlayer.Builder(context)
                .setMediaSourceFactory(
                        new DefaultMediaSourceFactory(context).setLiveTargetOffsetMs(5000))
                .build();

        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(@NonNull PlaybackException error) {
                Player.Listener.super.onPlayerError(error);
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }
        });
        playerAdapter = new LeanbackPlayerAdapter(context, exoPlayer, 50);
        playbackTransportControlGlue = new PlaybackTransportControlGlue(context, playerAdapter);
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
            public void onDanmuStatusChange(PlaybackGlue glue) {
                super.onDanmuStatusChange(glue);
                if(listener != null) listener.onDanmuStatusChange(glue);
            }

            @Override
            public void onLiveUrlChange(PlaybackGlue glue) {
                super.onLiveUrlChange(glue);
                exoPlayer.seekToNextMediaItem();
                FragmentActivity activity = fragment.requireActivity();
                Toast.makeText(activity,
                                String.format(activity.getString(R.string.total_msg_change_play_url),
                                        exoPlayer.getCurrentMediaItemIndex() + 1),
                                Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onSuperChatToggle(PlaybackGlue glue) {
                super.onSuperChatToggle(glue);
                if(listener != null) listener.onDanmakuSuperChatToggle(glue);
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
            FragmentActivity activity = fragment.requireActivity();
            Toast.makeText(activity,
                            activity.getString(R.string.live_play_failure),
                            Toast.LENGTH_SHORT)
                    .show();
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
        void onDanmuStatusChange(PlaybackGlue glue);
        void onDanmakuSuperChatToggle(PlaybackGlue glue);
    }
}

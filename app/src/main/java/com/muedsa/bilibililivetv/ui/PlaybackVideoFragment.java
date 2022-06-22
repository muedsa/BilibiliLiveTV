package com.muedsa.bilibililivetv.ui;

import android.os.Bundle;

import androidx.leanback.app.VideoSupportFragment;
import androidx.leanback.media.PlaybackGlue;

import com.muedsa.bilibililivetv.model.LiveRoom;
import com.muedsa.bilibililivetv.player.DanmakuDelegate;
import com.muedsa.bilibililivetv.player.ExoPlayerDelegate;

/**
 * Handles video playback with media controls.
 */
public class PlaybackVideoFragment extends VideoSupportFragment {

    private LiveRoom liveRoom;
    private DanmakuDelegate danmakuDelegate;
    private ExoPlayerDelegate exoPlayerDelegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        liveRoom = (LiveRoom) requireActivity().getIntent().getParcelableExtra(DetailsActivity.LIVE_ROOM);
        danmakuDelegate = new DanmakuDelegate(this, liveRoom);
        danmakuDelegate.init();
        ExoPlayerDelegate.Listener listener = new ExoPlayerDelegate.Listener() {
            @Override
            public void onPlayStateChanged(PlaybackGlue glue) {
                danmakuDelegate.setPlayer(glue.isPlaying());
            }

            @Override
            public void onDanmuStatusChange(PlaybackGlue glue) {
                danmakuDelegate.danmakuReleaseSwitch();
            }
        };
        exoPlayerDelegate = new ExoPlayerDelegate(this, listener, liveRoom);
        exoPlayerDelegate.init();
    }

    @Override
    public void onPause() {
        super.onPause();
        exoPlayerDelegate.pause();
        danmakuDelegate.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        exoPlayerDelegate.resume();
        danmakuDelegate.resume();
    }

    @Override
    public void onStop() {
        super.onStop();
        exoPlayerDelegate.release();
        danmakuDelegate.release();
    }

    public boolean isPlaying() {
        boolean flag = false;
        if(exoPlayerDelegate != null){
            flag = exoPlayerDelegate.isPlaying();
        }
        return flag;
    }
}
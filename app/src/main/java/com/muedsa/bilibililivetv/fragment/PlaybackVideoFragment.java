package com.muedsa.bilibililivetv.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.leanback.app.VideoSupportFragment;
import androidx.leanback.media.PlaybackGlue;

import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.activity.DetailsActivity;
import com.muedsa.bilibililivetv.room.model.LiveRoom;
import com.muedsa.bilibililivetv.player.danmaku.DanmakuDelegate;
import com.muedsa.bilibililivetv.player.ExoPlayerDelegate;

import master.flame.danmaku.ui.widget.DanmakuSurfaceView;

/**
 * Handles video playback with media controls.
 */
public class PlaybackVideoFragment extends VideoSupportFragment {
    private static final String TAG = PlaybackVideoFragment.class.getSimpleName();

    private LiveRoom liveRoom;
    private DanmakuDelegate danmakuDelegate;
    private ExoPlayerDelegate exoPlayerDelegate;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        ViewGroup root = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);
        DanmakuSurfaceView danmakuView = (DanmakuSurfaceView) LayoutInflater.from(getContext()).inflate(
                R.layout.danmaku_surface, root, false);
        if(root != null) root.addView(danmakuView, 0);
        liveRoom = (LiveRoom) requireActivity().getIntent().getSerializableExtra(DetailsActivity.LIVE_ROOM);
        danmakuDelegate = new DanmakuDelegate(this, danmakuView, liveRoom);
        danmakuDelegate.init();
        ExoPlayerDelegate.Listener listener = new ExoPlayerDelegate.Listener() {
            @Override
            public void onPlayStateChanged(PlaybackGlue glue) {
                danmakuDelegate.setPlayer(glue.isPlaying());
            }

            @Override
            public void onDanmuToggle(boolean enable) {
                danmakuDelegate.danmakuReleaseToggle(enable);
            }

            @Override
            public void onDanmakuSuperChatToggle(boolean enable) {
                danmakuDelegate.danmakuSuperChatToggle(enable);
            }

            @Override
            public void onDanmakuGiftToggle(boolean enable) {
                danmakuDelegate.danmakuGiftToggle(enable);
            }
        };
        exoPlayerDelegate = new ExoPlayerDelegate(this, listener, liveRoom);
        exoPlayerDelegate.init();
        return root;
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        exoPlayerDelegate.pause();
        danmakuDelegate.pause();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        danmakuDelegate.resume();
        exoPlayerDelegate.resume();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
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
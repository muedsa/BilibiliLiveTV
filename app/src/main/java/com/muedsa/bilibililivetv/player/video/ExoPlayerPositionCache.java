package com.muedsa.bilibililivetv.player.video;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;

import java.util.Timer;
import java.util.TimerTask;

public class ExoPlayerPositionCache {
    private final String TAG = ExoPlayerPositionCache.class.getSimpleName();

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Timer timer;
    private ExoPlayer exoPlayer;
    private Player.Listener listener;
    private long position;
    private float speed;
    private long lastUpdateTime;

    public ExoPlayerPositionCache(ExoPlayer exoPlayer){
        this.exoPlayer = exoPlayer;
        this.speed = exoPlayer.getPlaybackParameters().speed;
        pullPosition(false);
        listener = new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                pullPosition(isPlaying);
            }
        };
        exoPlayer.addListener(listener);
    }

    private void pullPosition(boolean isPlaying) {
        releaseTimer();
        if(isPlaying){
            timer = new Timer("ExoPlayerPositionCacheTimer");
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    handler.post(() -> {
                        position = exoPlayer.getCurrentPosition();
                        speed = exoPlayer.getPlaybackParameters().speed;
                        lastUpdateTime = SystemClock.uptimeMillis();
                    });
                }
            }, 50, 500);
        }else{
            handler.post(() -> {
                position = exoPlayer.getCurrentPosition();
                speed = exoPlayer.getPlaybackParameters().speed;
                lastUpdateTime = -1;
            });
        }
    }

    private void releaseTimer(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }

    public long getPosition() {
        long currentTime = SystemClock.uptimeMillis();
        long pos = position;
        if(lastUpdateTime > 0){
            pos += (currentTime - lastUpdateTime) * speed;
        }
        return pos;
    }

    public void release(){
        releaseTimer();
        exoPlayer.removeListener(listener);
        exoPlayer = null;
    }
}

package com.muedsa.bilibililivetv.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.leanback.app.VideoSupportFragment;
import androidx.leanback.app.VideoSupportFragmentGlueHost;
import androidx.leanback.media.PlaybackTransportControlGlue;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.muedsa.bilibililivetv.model.LiveRoom;

import java.util.Objects;

/**
 * Handles video playback with media controls.
 */
public class PlaybackVideoFragment extends VideoSupportFragment {

    private PlaybackTransportControlGlue<LeanbackPlayerAdapter> mTransportControlGlue;
    private LeanbackPlayerAdapter playerAdapter;
    private ExoPlayer exoPlayer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LiveRoom liveRoom =
                (LiveRoom) getActivity().getIntent().getSerializableExtra(DetailsActivity.LIVE_ROOM);

        VideoSupportFragmentGlueHost glueHost =
                new VideoSupportFragmentGlueHost(PlaybackVideoFragment.this);

        Context context = getContext();
        Objects.requireNonNull(context);

        exoPlayer = new ExoPlayer.Builder(context)
                .setMediaSourceFactory(
                        new DefaultMediaSourceFactory(context).setLiveTargetOffsetMs(5000))
                .build();

        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(PlaybackException error) {
                Player.Listener.super.onPlayerError(error);
                Toast.makeText(getActivity(), error.getLocalizedMessage(), Toast.LENGTH_LONG)
                        .show();
            }
        });

        playerAdapter = new LeanbackPlayerAdapter(context, exoPlayer, 50);
        mTransportControlGlue = new PlaybackTransportControlGlue<>(context, playerAdapter);
        mTransportControlGlue.setHost(glueHost);
        mTransportControlGlue.setTitle(liveRoom.getTitle());
        mTransportControlGlue.setSubtitle(liveRoom.getUname());
        MediaItem mediaItem =
                new MediaItem.Builder()
                        .setUri(liveRoom.getPlayUrl())
                        .setLiveConfiguration(
                                new MediaItem.LiveConfiguration.Builder()
                                        .setMaxPlaybackSpeed(1.02f)
                                        .build())
                        .build();
        exoPlayer.setMediaItem(mediaItem);
        mTransportControlGlue.play();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTransportControlGlue != null) {
            mTransportControlGlue.pause();
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
            playerAdapter = null;
            mTransportControlGlue = null;
        }
    }
}
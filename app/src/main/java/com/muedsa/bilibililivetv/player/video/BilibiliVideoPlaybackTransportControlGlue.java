package com.muedsa.bilibililivetv.player.video;

import android.content.Context;

import androidx.leanback.media.PlaybackTransportControlGlue;
import androidx.leanback.widget.Action;
import androidx.leanback.widget.ArrayObjectAdapter;

import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter;
import com.muedsa.bilibililivetv.player.SubtitleToggleAction;

import java.util.List;
import java.util.Objects;

public class BilibiliVideoPlaybackTransportControlGlue extends PlaybackTransportControlGlue<LeanbackPlayerAdapter> {

    public BilibiliVideoPlaybackTransportControlGlue(Context context, LeanbackPlayerAdapter impl) {
        super(context, impl);
    }

    @Override
    protected void onCreatePrimaryActions(ArrayObjectAdapter primaryActionsAdapter) {
        super.onCreatePrimaryActions(primaryActionsAdapter);
        Context context = getContext();
        SubtitleToggleAction subtitleToggleAction = new SubtitleToggleAction(context);
        primaryActionsAdapter.add(subtitleToggleAction);
    }

    @Override
    public void onActionClicked(Action action) {
        if(action instanceof SubtitleToggleAction) {
            dispatchActionCallback(action);
        }else {
            super.onActionClicked(action);
        }
    }

    private void dispatchActionCallback(Action action){
        List<PlayerCallback> callbacks = getPlayerCallbacks();
        if(Objects.nonNull(callbacks)){
            for (PlayerCallback callback : callbacks) {
                if(callback instanceof BilibiliVideoPlayerCallback){
                    BilibiliVideoPlayerCallback bilibiliVideoPlayerCallback = (BilibiliVideoPlayerCallback) callback;
                    if(action instanceof SubtitleToggleAction){
                        bilibiliVideoPlayerCallback.onSubtitleToggle();
                    }
                }
            }
        }
    }

    public abstract static class BilibiliVideoPlayerCallback extends PlayerCallback {
        public void onSubtitleToggle() {}
    }
}

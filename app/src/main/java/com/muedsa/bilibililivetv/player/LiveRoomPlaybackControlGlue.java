package com.muedsa.bilibililivetv.player;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.leanback.media.PlaybackGlue;
import androidx.leanback.media.PlaybackTransportControlGlue;
import androidx.leanback.widget.Action;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.PlaybackControlsRow;

import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter;
import com.muedsa.bilibililivetv.R;

import java.util.List;

public class LiveRoomPlaybackControlGlue extends PlaybackTransportControlGlue<LeanbackPlayerAdapter> {

    DanmuPlayStopAction danmuPlayStopAction;
    ChangePlayUrlAction changePlayUrlAction;

    public LiveRoomPlaybackControlGlue(Context context, LeanbackPlayerAdapter impl) {
        super(context, impl);
    }

    @Override
    protected void onCreatePrimaryActions(ArrayObjectAdapter primaryActionsAdapter) {
        super.onCreatePrimaryActions(primaryActionsAdapter);
        danmuPlayStopAction = new DanmuPlayStopAction(getContext());
        danmuPlayStopAction.setIndex(DanmuPlayStopAction.INDEX_STOP);
        primaryActionsAdapter.add(danmuPlayStopAction);

        changePlayUrlAction = new ChangePlayUrlAction(getContext());
        primaryActionsAdapter.add(changePlayUrlAction);
    }

    @Override
    public void onActionClicked(Action action) {
        if (action instanceof DanmuPlayStopAction) {
            danmuStatusChange(action);
            ((DanmuPlayStopAction) action).nextIndex();
            notifyItemChanged((ArrayObjectAdapter) getControlsRow().getPrimaryActionsAdapter(),
                    action);
        } else if(action instanceof ChangePlayUrlAction) {
            danmuStatusChange(action);
        } else {
            super.onActionClicked(action);
        }
    }

    private void danmuStatusChange(Action action){
        List<PlayerCallback> callbacks = getPlayerCallbacks();
        if (callbacks != null) {
            for (int i = 0, size = callbacks.size(); i < size; i++) {
                if(callbacks.get(i) instanceof LiveRoomPlayerCallback){
                    LiveRoomPlayerCallback callback = (LiveRoomPlayerCallback) callbacks.get(i);
                    if (action instanceof DanmuPlayStopAction) {
                        callback.onDanmuStatusChange(this);
                    } else if(action instanceof ChangePlayUrlAction){
                        callback.onLiveUrlChange(this);
                    }
                }
            }
        }
    }


    static class DanmuPlayStopAction extends PlaybackControlsRow.MultiAction {

        public static final int INDEX_PLAY = 0;
        public static final int INDEX_STOP = 1;

        public DanmuPlayStopAction(Context context) {
            super(R.id.playback_controls_danmu_play_stop);
            Drawable[] drawables = new Drawable[2];
            drawables[INDEX_PLAY] = context.getDrawable(R.drawable.ic_danmu_enable);
            drawables[INDEX_STOP] = context.getDrawable(R.drawable.ic_danmu_disable);
            setDrawables(drawables);
            String[] labels = new String[drawables.length];
            labels[INDEX_PLAY] = context.getString(R.string.playback_controls_danmu_play);
            labels[INDEX_STOP] = context.getString(R.string.playback_controls_danmu_stop);
            setLabels(labels);
        }
    }

    static class ChangePlayUrlAction extends Action {

        public ChangePlayUrlAction(Context context) {
            super(R.id.playback_controls_change_play_url);
            setIcon(context.getDrawable(R.drawable.ic_baseline_swap_horizontal_circle));
            setLabel1(context.getString(R.string.playback_controls_change_play_url));
        }
    }

    public abstract static class LiveRoomPlayerCallback extends PlayerCallback {
        public void onDanmuStatusChange(PlaybackGlue glue) {}
        public void onLiveUrlChange(PlaybackGlue glue) {}
    }

}

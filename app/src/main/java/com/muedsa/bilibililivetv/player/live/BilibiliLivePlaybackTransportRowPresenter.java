package com.muedsa.bilibililivetv.player.live;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.leanback.widget.PlaybackTransportRowPresenter;
import androidx.leanback.widget.RowPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class BilibiliLivePlaybackTransportRowPresenter extends PlaybackTransportRowPresenter {
    private final Context context;
    private final View.OnKeyListener keyListener;
    private Timer timer;

    public BilibiliLivePlaybackTransportRowPresenter(Context context, View.OnKeyListener keyListener) {
        this.context = context;
        this.keyListener = keyListener;
    }

    @Override
    protected void onBindRowViewHolder(RowPresenter.ViewHolder vh, Object item) {
        super.onBindRowViewHolder(vh, item);
        vh.setOnKeyListener(keyListener);
        PlaybackTransportRowPresenter.ViewHolder mvh = (PlaybackTransportRowPresenter.ViewHolder) vh;
        TextView durationView = mvh.getDurationView();
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",
                        context.getResources().getConfiguration().getLocales().get(0));

                @Override
                public void run() {
                    if (durationView != null) {
                        final String clock = sdf.format(new Date());
                        vh.view.post(() -> durationView.setText(clock));
                    }
                }
            }, 100, 100);
        }
    }

    @Override
    protected void onUnbindRowViewHolder(RowPresenter.ViewHolder vh) {
        super.onUnbindRowViewHolder(vh);
        vh.setOnKeyListener(null);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}

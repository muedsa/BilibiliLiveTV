package com.muedsa.bilibililivetv.presenter;

import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.ViewGroup;

import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.room.model.LiveRoom;

/*
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an Image CardView
 */
public class LiveRoomPresenter extends Presenter {
    private static final String TAG = LiveRoomPresenter.class.getSimpleName();

    private static final int CARD_WIDTH = 313;
    private static final int CARD_HEIGHT = 176;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;
    private Drawable mDefaultCardImage;

    private CardLongClickListener cardLongClickListener;

    public LiveRoomPresenter(@Nullable CardLongClickListener cardLongClickListener) {
        this.cardLongClickListener = cardLongClickListener;
    }

    private static void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        // Both background colors should be set because the view"s background is temporarily visible
        // during animations.
        view.setBackgroundColor(color);
        view.setInfoAreaBackgroundColor(color);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "onCreateViewHolder");

        sDefaultBackgroundColor =
                ContextCompat.getColor(parent.getContext(), R.color.default_background);
        sSelectedBackgroundColor =
                ContextCompat.getColor(parent.getContext(), R.color.selected_background);
        /*
         * This template uses a default image in res/drawable, but the general case for Android TV
         * will require your resources in xhdpi. For more information, see
         * https://developer.android.com/training/tv/start/layouts.html#density-resources
         */
        mDefaultCardImage = ContextCompat.getDrawable(parent.getContext(), R.drawable.no_cover);

        ImageCardView cardView =
                new ImageCardView(parent.getContext()) {
                    @Override
                    public void setSelected(boolean selected) {
                        updateCardBackgroundColor(this, selected);
                        super.setSelected(selected);
                    }
                };

        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        LiveRoom liveRoom = (LiveRoom) item;
        ImageCardView cardView = (ImageCardView) viewHolder.view;

        Log.d(TAG, "onBindViewHolder");
        if (liveRoom.getCoverImageUrl() != null) {
            cardView.setTitleText(liveRoom.getTitle());
            cardView.setContentText(liveRoom.getUname());
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            if(cardLongClickListener != null) {
                cardView.setOnLongClickListener(v -> {
                    if(cardLongClickListener != null) {
                        cardLongClickListener.onLongClick(liveRoom);
                    }
                    return true;
                });
            }
            GlideApp.with(viewHolder.view.getContext())
                    .load(liveRoom.getCoverImageUrl())
                    .centerCrop()
                    .error(mDefaultCardImage)
                    .into(cardView.getMainImageView());
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        // Remove references to images so that the garbage collector can free up memory
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
        cardLongClickListener = null;
    }

    public interface CardLongClickListener {
        void onLongClick(LiveRoom liveRoom);
    }
}
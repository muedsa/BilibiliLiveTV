package com.muedsa.bilibililivetv.presenter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.model.LiveUser;


public class LiveUserPresenter extends Presenter {
    private static final String TAG = LiveUserPresenter.class.getSimpleName();

    private static final int CARD_WIDTH = 200;
    private static final int CARD_HEIGHT = 200;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;
    private Drawable mDefaultCardImage;

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
        mDefaultCardImage = ContextCompat.getDrawable(parent.getContext(), R.drawable.ic_baseline_account_circle_24);
        ImageCardView cardView =
                new ImageCardView(new ContextThemeWrapper(parent.getContext(), R.style.LiveUserImageCardTheme)) {
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
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        LiveUser liveUser = (LiveUser) item;
        ImageCardView cardView = (ImageCardView) viewHolder.view;

        Log.d(TAG, "onBindViewHolder");
        if (liveUser.getUface() != null) {
            cardView.setTitleText(liveUser.getUname());
            cardView.setContentDescription(liveUser.getLiveStatusDesc(cardView.getContext().getResources()));
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            GlideApp.with(viewHolder.view.getContext())
                    .load(liveUser.getUface())
                    .centerCrop()
                    .error(mDefaultCardImage)
                    .into(cardView.getMainImageView());
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        // Remove references to images so that the garbage collector can free up memory
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }
}
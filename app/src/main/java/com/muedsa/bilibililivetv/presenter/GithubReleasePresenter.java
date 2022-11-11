package com.muedsa.bilibililivetv.presenter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;

import com.muedsa.bilibililivetv.BuildConfig;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.github.model.GithubReleaseTagInfo;

public class GithubReleasePresenter extends Presenter {
    private static final String TAG = GithubReleasePresenter.class.getSimpleName();

    private static final int CARD_SCALE = 2;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;
    private Drawable mDefaultCardImage;

    public GithubReleasePresenter() {
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
        Log.d(TAG, "onBindViewHolder");

        GithubReleaseTagInfo info = (GithubReleaseTagInfo) item;
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        if (info.getImage() != null) {
            cardView.setTitleText("Latest Version: " + info.getTag());
            cardView.setContentText("Current Version:" + BuildConfig.VERSION_NAME);
            int width = info.getImageWidth() / CARD_SCALE;
            int height = info.getImageHeight() / CARD_SCALE;
            cardView.setMainImageDimensions(width, height);
            GlideApp.with(viewHolder.view.getContext())
                    .load(info.getImage())
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
    }
}

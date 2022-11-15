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

public class GithubReleasePresenter extends AbstractImageCardPresenter {
    private static final String TAG = GithubReleasePresenter.class.getSimpleName();

    private static final int CARD_SCALE = 2;
    private Drawable mDefaultCardImage;

    public GithubReleasePresenter() {
        super(0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        ViewHolder viewHolder = super.onCreateViewHolder(parent);
        mDefaultCardImage = ContextCompat.getDrawable(parent.getContext(), R.drawable.no_cover);
        return viewHolder;
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
}

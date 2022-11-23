package com.muedsa.bilibililivetv.presenter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.leanback.widget.ImageCardView;

import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.model.LiveUser;
import com.muedsa.bilibililivetv.util.DpUtil;

public class LiveUserPresenter extends AbstractImageCardPresenter {
    private static final String TAG = LiveUserPresenter.class.getSimpleName();

    private static final int CARD_SIZE_DP = 110;
    private Drawable mDefaultCardImage;

    public LiveUserPresenter() {
        super(R.style.LiveUserImageCardTheme);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        ViewHolder viewHolder = super.onCreateViewHolder(parent);
        mDefaultCardImage = ContextCompat.getDrawable(parent.getContext(), R.drawable.ic_baseline_account_circle_24);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Log.d(TAG, "onBindViewHolder");
        LiveUser liveUser = (LiveUser) item;
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        if (liveUser.getUface() != null) {
            cardView.setTitleText(liveUser.getUname());
            cardView.setContentDescription(liveUser.getLiveStatusDesc(cardView.getContext().getResources()));
            int size = DpUtil.convertDpToPixel(viewHolder.view.getContext(), CARD_SIZE_DP);
            cardView.setMainImageDimensions(size, size);
            GlideApp.with(viewHolder.view.getContext())
                    .load(liveUser.getUface())
                    .centerCrop()
                    .error(mDefaultCardImage)
                    .into(cardView.getMainImageView());
        }
    }
}
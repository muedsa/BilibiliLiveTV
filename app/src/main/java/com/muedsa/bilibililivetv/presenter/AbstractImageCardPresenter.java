package com.muedsa.bilibililivetv.presenter;

import android.content.Context;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;

import com.muedsa.bilibililivetv.R;

public abstract class AbstractImageCardPresenter extends Presenter {
    private static final String TAG = AbstractImageCardPresenter.class.getSimpleName();

    private final int imageViewThemeId;

    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;

    public AbstractImageCardPresenter(int imageViewThemeId) {
        this.imageViewThemeId = imageViewThemeId;
    }

    private static void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        view.setBackgroundColor(color);
        view.setInfoAreaBackgroundColor(color);
    }

    public static void setsSelectedBackgroundColor(int sSelectedBackgroundColor) {
        AbstractImageCardPresenter.sSelectedBackgroundColor = sSelectedBackgroundColor;
    }


    public static void setsDefaultBackgroundColor(int sDefaultBackgroundColor) {
        AbstractImageCardPresenter.sDefaultBackgroundColor = sDefaultBackgroundColor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        sDefaultBackgroundColor =
                ContextCompat.getColor(parent.getContext(), R.color.default_background);
        sSelectedBackgroundColor =
                ContextCompat.getColor(parent.getContext(), R.color.selected_background);
        Context context = parent.getContext();
        if (imageViewThemeId != 0) {
            context = new ContextThemeWrapper(parent.getContext(), R.style.LiveUserImageCardTheme);
        }
        ImageCardView cardView =
                new ImageCardView(context) {
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
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        // Remove references to images so that the garbage collector can free up memory
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }
}

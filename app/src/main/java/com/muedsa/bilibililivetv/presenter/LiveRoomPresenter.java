package com.muedsa.bilibililivetv.presenter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;

import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.room.model.LiveRoom;

public class LiveRoomPresenter extends AbstractImageCardPresenter {
    private static final String TAG = LiveRoomPresenter.class.getSimpleName();

    private static final int CARD_WIDTH = 313;
    private static final int CARD_HEIGHT = 176;
    private Drawable mDefaultCardImage;

    private CardLongClickListener cardLongClickListener;

    public LiveRoomPresenter(CardLongClickListener cardLongClickListener) {
        super(0);
        this.cardLongClickListener = cardLongClickListener;
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
        LiveRoom liveRoom = (LiveRoom) item;
        ImageCardView cardView = (ImageCardView) viewHolder.view;
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

    public interface CardLongClickListener {
        void onLongClick(LiveRoom liveRoom);
    }
}
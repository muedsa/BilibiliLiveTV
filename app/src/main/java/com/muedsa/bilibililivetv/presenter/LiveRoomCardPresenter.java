package com.muedsa.bilibililivetv.presenter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;

import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.model.search.SearchLiveRoom;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.model.LiveRoomConvert;
import com.muedsa.bilibililivetv.room.model.LiveRoom;
import com.muedsa.bilibililivetv.util.DpUtil;

public class LiveRoomCardPresenter extends AbstractImageCardPresenter {
    private static final String TAG = LiveRoomCardPresenter.class.getSimpleName();

    private static final int CARD_WIDTH_DP = 160;
    private static final int CARD_HEIGHT_DP = 90;

    private Drawable mDefaultCardImage;

    private CardLongClickListener cardLongClickListener;

    public LiveRoomCardPresenter(CardLongClickListener cardLongClickListener) {
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
        CharSequence title = "";
        CharSequence content = "";
        String url = null;
        if(item instanceof LiveRoom){
            LiveRoom liveRoom = (LiveRoom) item;
            title = liveRoom.getTitle();
            content = liveRoom.getUname();
            url = liveRoom.getCoverImageUrl();
        }else if(item instanceof SearchLiveRoom) {
            SearchLiveRoom searchLiveRoom = (SearchLiveRoom) item;
            title = HtmlCompat.fromHtml(searchLiveRoom.getTitle(), HtmlCompat.FROM_HTML_MODE_COMPACT);
            content = HtmlCompat.fromHtml(searchLiveRoom.getUname(), HtmlCompat.FROM_HTML_MODE_COMPACT);
            url = LiveRoomConvert.getImageUrl(searchLiveRoom);
        }
        if (!Strings.isNullOrEmpty(url)) {
            ImageCardView cardView = (ImageCardView) viewHolder.view;
            cardView.setTitleText(title);
            cardView.setContentText(content);
            int width = DpUtil.convertDpToPixel(viewHolder.view.getContext(), CARD_WIDTH_DP);
            int height = DpUtil.convertDpToPixel(viewHolder.view.getContext(), CARD_HEIGHT_DP);
            cardView.setMainImageDimensions(width, height);
            if(cardLongClickListener != null && item instanceof LiveRoom) {
                cardView.setOnLongClickListener(v -> {
                    if(cardLongClickListener != null) {
                        cardLongClickListener.onLongClick((LiveRoom) item);
                    }
                    return true;
                });
            }
            GlideApp.with(viewHolder.view.getContext())
                    .load(url)
                    .centerCrop()
                    .error(mDefaultCardImage)
                    .into(cardView.getMainImageView());
        }
    }

    public interface CardLongClickListener {
        void onLongClick(LiveRoom liveRoom);
    }
}
package com.muedsa.bilibililivetv.presenter;

import androidx.core.text.HtmlCompat;
import androidx.leanback.widget.AbstractDetailsDescriptionPresenter;

import com.muedsa.bilibililivetv.model.LiveRoom;

public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        LiveRoom liveRoom = (LiveRoom) item;

        if (liveRoom != null) {
            viewHolder.getTitle().setText(liveRoom.getTitle());
            viewHolder.getSubtitle().setText(liveRoom.getUname());
            viewHolder.getBody().setText(HtmlCompat.fromHtml(liveRoom.getDescription(), HtmlCompat.FROM_HTML_MODE_COMPACT));
        }
    }
}
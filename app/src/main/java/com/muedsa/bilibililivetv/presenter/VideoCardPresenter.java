package com.muedsa.bilibililivetv.presenter;


import androidx.core.content.ContextCompat;
import androidx.leanback.widget.ImageCardView;

import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.model.video.VideoData;
import com.muedsa.bilibililiveapiclient.model.video.VideoPage;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;

import java.util.Objects;

public class VideoCardPresenter extends AbstractImageCardPresenter {

    private static final int CARD_WIDTH = 313;
    private static final int CARD_HEIGHT = 176;

    public VideoCardPresenter() {
        super(0);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        String title = "";
        String content = "";
        String url = "";
        if (item instanceof VideoData) {
            VideoData videoData = (VideoData) item;
            title = videoData.getTitle();
            content = Objects.nonNull(videoData.getOwner()) ? videoData.getOwner().getName() : "";
            url = videoData.getPic();
        } else if (item instanceof VideoPage) {
            VideoPage videoPage = (VideoPage) item;
            title = videoPage.getPart();
            content = "P" + videoPage.getPage();
            url = videoPage.getFirstFrame();
        }
        if (!Strings.isNullOrEmpty(url)) {
            ImageCardView cardView = (ImageCardView) viewHolder.view;
            cardView.setTitleText(title);
            cardView.setContentText(content);
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            GlideApp.with(viewHolder.view.getContext())
                    .load(url)
                    .centerCrop()
                    .error(ContextCompat.getDrawable(viewHolder.view.getContext(), R.drawable.no_cover))
                    .into(cardView.getMainImageView());
        }
    }
}

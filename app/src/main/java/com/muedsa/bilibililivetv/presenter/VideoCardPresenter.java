package com.muedsa.bilibililivetv.presenter;


import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.leanback.widget.ImageCardView;

import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.model.dynamic.VideoDynamicCard;
import com.muedsa.bilibililiveapiclient.model.history.HistoryRecord;
import com.muedsa.bilibililiveapiclient.model.search.SearchVideoInfo;
import com.muedsa.bilibililiveapiclient.model.video.SectionEpisode;
import com.muedsa.bilibililiveapiclient.model.video.VideoData;
import com.muedsa.bilibililiveapiclient.model.video.VideoPage;
import com.muedsa.bilibililivetv.GlideApp;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.util.DpUtil;

import java.util.Objects;

public class VideoCardPresenter extends AbstractImageCardPresenter {

    private static final int CARD_WIDTH_DP = 160;
    private static final int CARD_HEIGHT_DP = 90;

    public VideoCardPresenter() {
        super(0);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        CharSequence title = "";
        CharSequence content = "";
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
        } else if (item instanceof HistoryRecord) {
            HistoryRecord historyRecord = (HistoryRecord) item;
            title = historyRecord.getTitle();
            content = historyRecord.getAuthorName();
            url = historyRecord.getCover();
        } else if (item instanceof SearchVideoInfo) {
            SearchVideoInfo searchVideoInfo = (SearchVideoInfo) item;
            title = HtmlCompat.fromHtml(searchVideoInfo.getTitle(), HtmlCompat.FROM_HTML_MODE_COMPACT);
            content = HtmlCompat.fromHtml(searchVideoInfo.getAuthor(), HtmlCompat.FROM_HTML_MODE_COMPACT);
            url = searchVideoInfo.getPic();
        } else if (item instanceof SectionEpisode) {
            SectionEpisode episode = (SectionEpisode) item;
            title = episode.getTitle();
            //content = "";
            url = episode.getArc().getPic();
        } else if (item instanceof VideoDynamicCard) {
            VideoDynamicCard videoDynamicCard = (VideoDynamicCard) item;
            title = videoDynamicCard.getTitle();
            content = videoDynamicCard.getOwner().getName();
            url = videoDynamicCard.getPic();
        }
        if (!Strings.isNullOrEmpty(url)) {
            ImageCardView cardView = (ImageCardView) viewHolder.view;
            cardView.setTitleText(title);
            cardView.setContentText(content);
            int width = DpUtil.convertDpToPixel(viewHolder.view.getContext(), CARD_WIDTH_DP);
            int height = DpUtil.convertDpToPixel(viewHolder.view.getContext(), CARD_HEIGHT_DP);
            cardView.setMainImageDimensions(width, height);
            GlideApp.with(viewHolder.view.getContext())
                    .load(url)
                    .centerCrop()
                    .error(ContextCompat.getDrawable(viewHolder.view.getContext(), R.drawable.no_cover))
                    .into(cardView.getMainImageView());
        }
    }
}

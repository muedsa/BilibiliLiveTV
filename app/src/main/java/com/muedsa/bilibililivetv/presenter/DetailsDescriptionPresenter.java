package com.muedsa.bilibililivetv.presenter;

import androidx.core.text.HtmlCompat;
import androidx.leanback.widget.AbstractDetailsDescriptionPresenter;

import com.google.common.base.Strings;
import com.muedsa.bilibililiveapiclient.model.video.VideoData;
import com.muedsa.bilibililiveapiclient.model.video.VideoInfo;
import com.muedsa.bilibililivetv.model.VideoInfoConvert;
import com.muedsa.bilibililivetv.room.model.LiveRoom;

import java.util.Objects;

public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        if (item instanceof LiveRoom) {
            LiveRoom liveRoom = (LiveRoom) item;
            viewHolder.getTitle().setText(liveRoom.getTitle());
            viewHolder.getSubtitle().setText(liveRoom.getUname());
            viewHolder.getBody().setText(HtmlCompat.fromHtml(liveRoom.getDescription(), HtmlCompat.FROM_HTML_MODE_COMPACT));
        } else if (item instanceof VideoInfo) {
            VideoInfo videoInfo = (VideoInfo) item;
            VideoData videoData = videoInfo.getVideoData();
            if (Objects.nonNull(videoData)) {
                viewHolder.getSubtitle().setText(VideoInfoConvert.findTitle(videoInfo));
                viewHolder.getSubtitle().setText(Objects.nonNull(videoData.getOwner()) ? Strings.nullToEmpty(videoData.getOwner().getName()) : "");
                viewHolder.getBody().setText(videoData.getDesc());
            }
        }
    }
}
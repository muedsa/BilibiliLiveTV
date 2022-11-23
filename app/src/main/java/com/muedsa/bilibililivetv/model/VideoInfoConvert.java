package com.muedsa.bilibililivetv.model;

import com.muedsa.bilibililiveapiclient.model.video.PlayDash;
import com.muedsa.bilibililiveapiclient.model.video.PlayDashInfo;
import com.muedsa.bilibililiveapiclient.model.video.PlayInfo;
import com.muedsa.bilibililiveapiclient.model.video.SupportFormat;
import com.muedsa.bilibililiveapiclient.model.video.VideoData;
import com.muedsa.bilibililiveapiclient.model.video.VideoInfo;
import com.muedsa.bilibililiveapiclient.model.video.VideoPage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class VideoInfoConvert {

    public static Map<Integer, String> buildQualityDescription(PlayInfo playInfo) {
        return playInfo.getSupportFormats().stream()
                .collect(Collectors.toMap(SupportFormat::getQuality, SupportFormat::getNewDescription));

    }

    public static List<VideoPlayInfo> buildVideoPlayInfoList(VideoInfo videoInfo, PlayInfo playInfo, String url) {
        PlayDash dash = playInfo.getDash();
        if (Objects.isNull(dash) || Objects.isNull(dash.getVideo()) || Objects.isNull(dash.getAudio())
                || dash.getVideo().isEmpty() || dash.getAudio().isEmpty()) {
            return Collections.emptyList();
        }
        Optional<PlayDashInfo> audioOption = dash.getAudio().stream().max(Comparator.comparingInt(PlayDashInfo::getId));
        if (!audioOption.isPresent()) {
            return Collections.emptyList();
        }
        Map<Integer, String> qualityMap = buildQualityDescription(playInfo);
        return dash.getVideo().stream().map(video -> {
            VideoPlayInfo videoPlayInfo = new VideoPlayInfo();
            videoPlayInfo.setBv(videoInfo.getBvid());
            videoPlayInfo.setCid(videoInfo.getVideoData().getCid());
            videoPlayInfo.setTitle(findTitle(videoInfo));
            videoPlayInfo.setSubTitle(Objects.nonNull(videoInfo.getVideoData().getOwner()) ? videoInfo.getVideoData().getOwner().getName() : "");
            videoPlayInfo.setQuality(video.getId());
            videoPlayInfo.setQualityDescription(qualityMap.getOrDefault(video.getId(), ""));
            videoPlayInfo.setCodecs(video.getCodecs().split("\\.")[0]);
            videoPlayInfo.setVideoUrl(video.getBaseUrl());
            videoPlayInfo.setAudioUrl(audioOption.get().getBaseUrl());
            videoPlayInfo.setReferer(url);
            if(Objects.nonNull(videoInfo.getVideoData().getSubtitle())
                    && Objects.nonNull(videoInfo.getVideoData().getSubtitle().getList())){
                videoPlayInfo.setSubtitleList(videoInfo.getVideoData().getSubtitle().getList());
            }else{
                videoPlayInfo.setSubtitleList(Collections.emptyList());
            }
            return videoPlayInfo;
        }).collect(Collectors.toList());
    }

    public static String findTitle(VideoInfo videoInfo) {
        String title = "";
        if (Objects.nonNull(videoInfo) && Objects.nonNull(videoInfo.getVideoData())) {
            VideoData videoData = videoInfo.getVideoData();
            if (Objects.nonNull(videoData.getTitle())) {
                title = videoData.getTitle();
            }
            List<VideoPage> pages = videoData.getPages();
            if (Objects.nonNull(videoInfo.getP()) && Objects.nonNull(pages)) {
                Optional<VideoPage> pageOptional = pages.stream()
                        .filter(p -> Objects.nonNull(p.getPage()) && p.getPage().equals(videoInfo.getP()))
                        .findFirst();
                if (pageOptional.isPresent() && Objects.nonNull(pageOptional.get().getPart())) {
                    title = pageOptional.get().getPart();
                }
            }
        }
        return title;
    }
}

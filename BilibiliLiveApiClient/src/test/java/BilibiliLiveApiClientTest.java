import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.muedsa.bilibililiveapiclient.BilibiliApiContainer;
import com.muedsa.bilibililiveapiclient.BilibiliLiveApiClient;
import com.muedsa.bilibililiveapiclient.ChatBroadcastWsClient;
import com.muedsa.bilibililiveapiclient.ErrorCode;
import com.muedsa.bilibililiveapiclient.model.BilibiliPageInfo;
import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.UserNav;
import com.muedsa.bilibililiveapiclient.model.WbiImg;
import com.muedsa.bilibililiveapiclient.model.danmaku.DanmakuElem;
import com.muedsa.bilibililiveapiclient.model.danmaku.DmSegMobileReply;
import com.muedsa.bilibililiveapiclient.model.danmaku.DmWebViewReply;
import com.muedsa.bilibililiveapiclient.model.dynamic.DynamicFlow;
import com.muedsa.bilibililiveapiclient.model.live.AnchorBaseInfo;
import com.muedsa.bilibililiveapiclient.model.live.AnchorInfo;
import com.muedsa.bilibililiveapiclient.model.live.DanmakuHostInfo;
import com.muedsa.bilibililiveapiclient.model.live.DanmakuInfo;
import com.muedsa.bilibililiveapiclient.model.live.LargeInfo;
import com.muedsa.bilibililiveapiclient.model.live.LiveRoomInfo;
import com.muedsa.bilibililiveapiclient.model.live.PlayUrlData;
import com.muedsa.bilibililiveapiclient.model.live.Qn;
import com.muedsa.bilibililiveapiclient.model.live.RoomInfo;
import com.muedsa.bilibililiveapiclient.model.live.UserWebListResult;
import com.muedsa.bilibililiveapiclient.model.passport.LoginResponse;
import com.muedsa.bilibililiveapiclient.model.passport.LoginUrl;
import com.muedsa.bilibililiveapiclient.model.search.SearchAggregation;
import com.muedsa.bilibililiveapiclient.model.search.SearchResult;
import com.muedsa.bilibililiveapiclient.model.search.SearchVideoInfo;
import com.muedsa.bilibililiveapiclient.model.space.SpaceSearchResult;
import com.muedsa.bilibililiveapiclient.model.video.PlayDash;
import com.muedsa.bilibililiveapiclient.model.video.PlayDashInfo;
import com.muedsa.bilibililiveapiclient.model.video.PlayInfo;
import com.muedsa.bilibililiveapiclient.model.video.Season;
import com.muedsa.bilibililiveapiclient.model.video.SeasonSection;
import com.muedsa.bilibililiveapiclient.model.video.SectionEpisode;
import com.muedsa.bilibililiveapiclient.model.video.VideoData;
import com.muedsa.bilibililiveapiclient.model.video.VideoDetail;
import com.muedsa.bilibililiveapiclient.model.video.VideoInfo;
import com.muedsa.bilibililiveapiclient.util.ApiUtil;
import com.muedsa.bilibililiveapiclient.util.WbiUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BilibiliLiveApiClientTest {

    private static BilibiliLiveApiClient client;
    private static long roomId;

    private static final Logger logger = Logger.getGlobal();

    @BeforeAll
    public static void init(){
        roomId = 3;
        client = new BilibiliLiveApiClient();
        client.putCookie(BilibiliApiContainer.COOKIE_KEY_USER_ID, "2333");
    }

    @Test
    public void getLargeInfo() throws IOException {
        BilibiliResponse<LargeInfo> response = client.getLargeInfo(roomId);
        Assertions.assertEquals(0L, response.getCode());
        LargeInfo largeInfo = response.getData();
        Assertions.assertNotNull(largeInfo);
        RoomInfo roomInfo = largeInfo.getRoomInfo();
        Assertions.assertNotNull(roomInfo.getRoomId());
        Assertions.assertNotNull(roomInfo.getTitle());
        AnchorInfo anchorInfo = largeInfo.getAnchorInfo();
        Assertions.assertNotNull(anchorInfo);
        AnchorBaseInfo baseInfo = anchorInfo.getBaseInfo();
        Assertions.assertNotNull(baseInfo);
        Assertions.assertNotNull(baseInfo.getUname());
        String message = String.format("roomId:%d, title:%s, uname:%s", roomInfo.getRoomId(), roomInfo.getTitle(), baseInfo.getUname());
        logger.info(message);
        roomId = roomInfo.getRoomId();

    }

    @Test
    public void playUrlTest() throws IOException {
        BilibiliResponse<PlayUrlData> response = client.getPlayUrlMessage(roomId, Qn.RAW);
        Assertions.assertEquals(0L, response.getCode());
        Assertions.assertNotNull(response.getData());
        Assertions.assertNotNull(response.getData().getDurl());
        Assertions.assertNotNull(response.getData().getDurl().get(0).getUrl());
        logger.info(response.getData().getDurl().get(0).getUrl());
    }

    @Test
    public void pageOnlineLiveRoom() throws IOException {
        BilibiliResponse<BilibiliPageInfo<LiveRoomInfo>> response = client.pageOnlineLiveRoom(0, 10, 0);
        Assertions.assertEquals(0L, response.getCode());
        Assertions.assertNotNull(response.getData());
        Assertions.assertNotNull(response.getData().getList());
        LiveRoomInfo liveRoomInfo = response.getData().getList().get(0);
        Assertions.assertNotNull(liveRoomInfo.getRoomId());
        String message = String.format("roomId:%d, title:%s", liveRoomInfo.getRoomId(), liveRoomInfo.getTitle());
        logger.info(message);
    }

    @Test
    public void getDanmuInfoTest() throws IOException {
        getLargeInfo();
        BilibiliResponse<DanmakuInfo> response = client.getDanmuInfo(roomId);
        Assertions.assertEquals(0L, response.getCode());
        DanmakuInfo danmakuInfo = response.getData();
        Assertions.assertNotNull(danmakuInfo);
        Assertions.assertNotNull(danmakuInfo.getToken());
        Assertions.assertNotNull(danmakuInfo.getHostList());
        String message = String.format("roomId:%d, token:%s", roomId, danmakuInfo.getToken());
        logger.info(message);
        for (DanmakuHostInfo danmakuHostInfo : danmakuInfo.getHostList()) {
            message = String.format("host:%s, wsPort:%d", danmakuHostInfo.getHost(), danmakuHostInfo.getWsPort());
            logger.info(message);
        }
    }

    @Test
    public void wsTest() throws IOException, InterruptedException {
        BilibiliResponse<DanmakuInfo> response = client.getDanmuInfo(roomId);
        Assertions.assertEquals(0L, response.getCode());
        DanmakuInfo danmakuInfo = response.getData();
        Assertions.assertNotNull(danmakuInfo);
        Assertions.assertNotNull(danmakuInfo.getToken());
        ChatBroadcastWsClient client = new ChatBroadcastWsClient(roomId, danmakuInfo.getToken());
        client.start();
        Assertions.assertTrue(client.isOpen());
    }

    @Test
    public void searchLive() throws IOException {
        BilibiliResponse<SearchAggregation<SearchResult>> response = client.searchLive("1000", 1, 10);
        Assertions.assertEquals(0L, response.getCode());
        SearchAggregation<SearchResult> searchAggregation = response.getData();
        Assertions.assertNotNull(searchAggregation);
        if(Objects.nonNull(searchAggregation.getResult())){
            if(Objects.nonNull(searchAggregation.getResult().getLiveRoom())){
                logger.info("liveRoom search result:");
                searchAggregation.getResult().getLiveRoom().forEach(liveRoom -> {
                    String message = String.format("roomId:%d, title:%s", liveRoom.getRoomId(), ApiUtil.removeSearchHighlight(liveRoom.getTitle()));
                    logger.info(message);
                });
            }
            if(Objects.nonNull(searchAggregation.getResult().getLiveUser())){
                logger.info("liveUser search result:");
                searchAggregation.getResult().getLiveUser().forEach(liveUser -> {
                    String message = String.format("roomId:%d, uname:%s", liveUser.getRoomId(),  ApiUtil.removeSearchHighlight(liveUser.getUname()));
                    logger.info(message);
                });
            }
        }
    }

    @Test
    public void searchVideo() throws IOException {
        BilibiliResponse<SearchAggregation<List<SearchVideoInfo>>> response = client.searchVideo("原神", 1, 10);
        Assertions.assertEquals(0L, response.getCode(), response::getMessage);
        SearchAggregation<List<SearchVideoInfo>> searchAggregation = response.getData();
        Assertions.assertNotNull(searchAggregation);
        List<SearchVideoInfo> videoInfoList = searchAggregation.getResult();
        if(Objects.nonNull(videoInfoList)){
            logger.info("video search result:");
            videoInfoList.forEach(video -> {
                String message = String.format("BV:%s, title:%s, author:%s", video.getBvId(), ApiUtil.removeSearchHighlight(video.getTitle()), ApiUtil.removeSearchHighlight(video.getAuthor()));
                logger.info(message);
            });
        }
    }

    @Test
    public LoginUrl getLoginUrlTest() throws IOException {
        BilibiliResponse<LoginUrl> response = client.getLoginUrl();
        Assertions.assertEquals(0L, response.getCode());
        LoginUrl loginUrl = response.getData();
        Assertions.assertNotNull(loginUrl);
        Assertions.assertNotNull(loginUrl.getUrl());
        Assertions.assertNotNull(loginUrl.getOauthKey());
        return loginUrl;
    }

    @Test
    public void getLoginInfoTest() throws IOException {
        LoginUrl loginUrl = getLoginUrlTest();
        LoginResponse loginResponse = client.getLoginInfo(loginUrl.getOauthKey());
        Assertions.assertNotNull(loginResponse);
        //Assertions.assertNotNull(loginResponse.getIntData());
        String message = String.format("oauthKey:%s, message:%s", loginUrl.getOauthKey(), loginResponse.getMessage());
        logger.info(message);
    }

    @Test
    public void navTest() throws IOException {
        BilibiliResponse<UserNav> response = client.nav();
        Assertions.assertNotNull(response);
        logger.info(response.getMessage());
    }

    @Test
    public void getVideDetailTest() throws IOException {
        VideoDetail videoDetail = client.getVideoDetail("BV11e411N7dy");
        Assertions.assertNotNull(videoDetail);
        VideoInfo videoInfo = videoDetail.getVideoInfo();
        Assertions.assertNotNull(videoInfo);
        VideoData videoData = videoInfo.getVideoData();
        Assertions.assertNotNull(videoData);
        BilibiliResponse<PlayInfo> playInfoResponse = videoDetail.getPlayInfoResponse();
        Assertions.assertNotNull(playInfoResponse);
        Assertions.assertNotNull(playInfoResponse.getCode());
        if (ErrorCode.SUCCESS == playInfoResponse.getCode()) {
            PlayInfo playInfo = playInfoResponse.getData();
            Assertions.assertNotNull(playInfo);
            PlayDash dash = playInfo.getDash();
            Assertions.assertNotNull(dash);
            List<PlayDashInfo> videoList = dash.getVideo();
            List<PlayDashInfo> audioList = dash.getAudio();
            Assertions.assertNotNull(videoList);
            Assertions.assertFalse(videoList.isEmpty());
            Assertions.assertNotNull(audioList);
            Assertions.assertFalse(audioList.isEmpty());
            String videoDataMessage = String.format("BV:%s, title:%s, desc:%s", videoInfo.getBvid(), videoData.getTitle(), videoData.getDesc());
            logger.info(videoDataMessage);
            Season season = videoData.getUgcSeason();
            if(Objects.nonNull(season)){
                String seasonMessage = String.format("Season title:%s, intro:%s, cover:%s", season.getTitle(), season.getIntro(), season.getCover());
                logger.info(seasonMessage);
                if(Objects.nonNull(season.getSections())){
                    for (SeasonSection section : season.getSections()) {
                        String sectionMessage = String.format("Section title:%s, type:%d, isActive:%b", section.getTitle(), section.getType(), section.getActive());
                        logger.info(sectionMessage);
                        if(Objects.nonNull(section.getEpisodes())){
                            for (SectionEpisode episode : section.getEpisodes()) {
                                String episodeMessage = String.format("Episode title:%s, bv:%s", episode.getTitle(), episode.getBvId());
                                if(Objects.nonNull(episode.getArc())){
                                    episodeMessage += String.format(", arc.pic:%s", episode.getArc().getPic());
                                }
                                logger.info(episodeMessage);
                            }
                        }
                    }
                }
            }
            for (PlayDashInfo video : videoList) {
                String message = String.format("mimeType:%s, codecs:%s, quality:%s, baseUrl:%s", video.getMimeType(), video.getCodecs(), video.getId(), video.getBaseUrl());
                logger.info(message);
            }
            for (PlayDashInfo audio : audioList) {
                String message = String.format("mimeType:%s, codecs:%s, quality:%s, baseUrl:%s", audio.getMimeType(), audio.getCodecs(), audio.getId(), audio.getBaseUrl());
                logger.info(message);
            }
        } else {
            logger.info("get play info fail:" + playInfoResponse.getMessage());
        }
    }

    //@Test
    public void getVideDetailWithLogin() throws IOException, WriterException, InterruptedException {
        LoginUrl loginUrl = getLoginUrlTest();
        printQRCode(loginUrl.getUrl());
        for (int i = 0; i < 60; i++) {
            Thread.sleep(1500);
            LoginResponse loginResponse = client.getLoginInfo(loginUrl.getOauthKey());
            if (Objects.nonNull(loginResponse)
                    && Objects.nonNull(loginResponse.getStatus())
                    && loginResponse.getStatus()) {
                logger.info("登录成功!");
                String sessData = getSessData(loginResponse.getData().getUrl());
                client.putCookie(BilibiliApiContainer.COOKIE_KEY_SESSDATA, sessData);
                getVideDetailTest();
                break;
            }
        }
    }

    private static void printQRCode(String content) throws WriterException {
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
        hintMap.put(EncodeHintType.MARGIN, 0);
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 16, 16, hintMap);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        StringBuilder stringQRCodeBuild = new StringBuilder("\n");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (bitMatrix.get(x, y)) {
                    stringQRCodeBuild.append("██");
                } else {
                    stringQRCodeBuild.append("  ");
                }
            }
            stringQRCodeBuild.append("\n");
        }
        logger.info("请扫码~~" + stringQRCodeBuild);
    }

    private static final Pattern regex = Pattern.compile("\\S*SESSDATA=(\\S*)&bili_jct\\S*");

    private static String getSessData(String loginUrl) {
        String sessData = "";
        Matcher matcher = regex.matcher(loginUrl);
        if (matcher.matches()) {
            sessData = matcher.group(1);
        }
        return sessData;
    }

    @Test
    public void videoDanmakuViewTest() throws IOException {
        DmWebViewReply dmWebViewReply = client.videoDanmakuView(885244431);
        Assertions.assertNotNull(dmWebViewReply);
        Assertions.assertTrue(dmWebViewReply.hasDmSge());
        Assertions.assertTrue(dmWebViewReply.getDmSge().hasTotal());
        logger.info("count:" + dmWebViewReply.getCount());
    }

    @Test
    public void videoDanmakuSegmentTest() throws IOException {
        long oid = 885244431;
        DmWebViewReply dmWebViewReply = client.videoDanmakuView(oid);
        Assertions.assertTrue(dmWebViewReply.hasDmSge());
        Assertions.assertTrue(dmWebViewReply.getDmSge().hasTotal());
        int count = 0;
        logger.info("oid:" + oid + ", page total:" + dmWebViewReply.getDmSge().getTotal());
        for (int i = 1; i < dmWebViewReply.getDmSge().getTotal() + 1; i++) {
            DmSegMobileReply dmSegMobileReply = client.videoDanmakuSegment(oid, i);
            Assertions.assertNotNull(dmSegMobileReply);
            count += dmSegMobileReply.getElemsCount();
            logger.info("oid:" + oid + ", index:" + i + " count:" + dmSegMobileReply.getElemsCount());
        }
        logger.info("oid:" + oid + ", danmaku count:" + count);
    }

    @Test
    public void videoDanmakuElemListTest() throws IOException {
        long oid = 885244431;
        List<DanmakuElem> danmakuElems = client.videoDanmakuElemList(oid, 1);
        for (DanmakuElem danmakuElem : danmakuElems) {
            logger.info(danmakuElem.getProgress() + ":" + danmakuElem.getMode() + ":" + danmakuElem.getContent());
        }
    }

    @Test
    public void popularTest() throws IOException {
        BilibiliResponse<BilibiliPageInfo<VideoData>> response = client.popular(1, 20);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getCode());
        Assertions.assertEquals(response.getCode(), ErrorCode.SUCCESS);
        Assertions.assertNotNull(response.getData());
        List<VideoData> list = response.getData().getList();
        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
        for (VideoData videoData : list) {
            Assertions.assertNotNull(videoData);
            Assertions.assertNotNull(videoData.getOwner());
            logger.info("推荐视频:" + videoData.getTitle() + "[" + videoData.getBvid() + "]" + " " + videoData.getOwner().getName());
        }
    }

    @Test
    public void liveUserWebListTest() throws IOException {
        BilibiliResponse<UserWebListResult> response = client.liveUserWebList(1, 10);
        Assertions.assertNotNull(response);
        // 需要Cookie
    }

    @Test
    public void dynamicNewTest() throws IOException {
        BilibiliResponse<DynamicFlow> response = client.dynamicNew(Collections.singletonList(8));
        Assertions.assertNotNull(response);
        // 需要Cookie
    }

    @Test
    public void spaceSearchTest() throws IOException {
        BilibiliResponse<UserNav> navResponse = client.nav();
        Assertions.assertNotNull(navResponse);
        Assertions.assertNotNull(navResponse.getData());
        WbiImg wbiImg = navResponse.getData().getWbiImg();
        Assertions.assertNotNull(wbiImg);
        BilibiliResponse<SpaceSearchResult> response = client.spaceSearch(1, 25, 423895,
                WbiUtil.getMixinKey(wbiImg.getImgKey(), wbiImg.getSubKey()));
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getData());
        Assertions.assertNotNull(response.getData().getList());
        List<SearchVideoInfo> list = response.getData().getList().getVlist();
        if(list != null){
            for (SearchVideoInfo video : list) {
                String message = String.format("BV:%s, title:%s, author:%s", video.getBvId(),
                        ApiUtil.removeSearchHighlight(video.getTitle()),
                        ApiUtil.removeSearchHighlight(video.getAuthor()));
                logger.info(message);
            }
        }
    }
}

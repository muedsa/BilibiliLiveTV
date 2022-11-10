import com.muedsa.bilibililiveapiclient.BilibiliLiveApiClient;
import com.muedsa.bilibililiveapiclient.ChatBroadcastWsClient;
import com.muedsa.bilibililiveapiclient.model.BilibiliPageInfo;
import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.UserNav;
import com.muedsa.bilibililiveapiclient.model.live.AnchorBaseInfo;
import com.muedsa.bilibililiveapiclient.model.live.AnchorInfo;
import com.muedsa.bilibililiveapiclient.model.live.DanmakuHostInfo;
import com.muedsa.bilibililiveapiclient.model.live.DanmakuInfo;
import com.muedsa.bilibililiveapiclient.model.live.LargeInfo;
import com.muedsa.bilibililiveapiclient.model.live.LiveRoomInfo;
import com.muedsa.bilibililiveapiclient.model.live.PlayUrlData;
import com.muedsa.bilibililiveapiclient.model.live.Qn;
import com.muedsa.bilibililiveapiclient.model.live.RoomInfo;
import com.muedsa.bilibililiveapiclient.model.passport.LoginResponse;
import com.muedsa.bilibililiveapiclient.model.passport.LoginUrl;
import com.muedsa.bilibililiveapiclient.model.search.SearchAggregation;
import com.muedsa.bilibililiveapiclient.model.video.PlayDash;
import com.muedsa.bilibililiveapiclient.model.video.PlayDashInfo;
import com.muedsa.bilibililiveapiclient.model.video.PlayInfo;
import com.muedsa.bilibililiveapiclient.model.video.VideoDetail;
import com.muedsa.bilibililiveapiclient.model.video.VideoInfo;
import com.muedsa.bilibililiveapiclient.model.video.videoData;
import com.muedsa.bilibililiveapiclient.uitl.ApiUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class BilibiliLiveApiClientTest {

    private static BilibiliLiveApiClient client;
    private static long roomId;

    private static final Logger logger = Logger.getGlobal();

    @BeforeAll
    public static void init(){
        roomId = 3;
        client = new BilibiliLiveApiClient();
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
        BilibiliResponse<SearchAggregation> response = client.searchLive("1000", 1, 10);
        Assertions.assertEquals(0L, response.getCode());
        SearchAggregation searchAggregation = response.getData();
        Assertions.assertNotNull(searchAggregation);
        if(searchAggregation.getResult().getLiveRoom() != null){
            logger.info("liveRoom search result:");
            searchAggregation.getResult().getLiveRoom().forEach(liveRoom -> {
                String message = String.format("roomId:%d, title:%s", liveRoom.getRoomId(), ApiUtil.removeSearchHighlight(liveRoom.getTitle()));
                logger.info(message);
            });
        }
        if(searchAggregation.getResult().getLiveUser() != null){
            logger.info("liveUser search result:");
            searchAggregation.getResult().getLiveUser().forEach(liveUser -> {
                String message = String.format("roomId:%d, uname:%s", liveUser.getRoomId(),  ApiUtil.removeSearchHighlight(liveUser.getUname()));
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
        VideoDetail videoDetail = client.getVideoDetail("BV1WY411Z7Cj");
        Assertions.assertNotNull(videoDetail);
        VideoInfo videoInfo = videoDetail.getVideoInfo();
        Assertions.assertNotNull(videoInfo);
        videoData videoData = videoInfo.getVideoData();
        Assertions.assertNotNull(videoData);
        PlayInfo playInfo = videoDetail.getPlayInfo();
        Assertions.assertNotNull(playInfo);
        PlayDash dash = playInfo.getDash();
        Assertions.assertNotNull(dash);
        List<PlayDashInfo> videoList = dash.getVideo();
        List<PlayDashInfo> audioList = dash.getAudio();
        Assertions.assertNotNull(videoList);
        Assertions.assertFalse(videoList.isEmpty());
        Assertions.assertNotNull(audioList);
        Assertions.assertFalse(audioList.isEmpty());
        String videoDataMessage = String.format("BV:%s, title:%s, desc:%s", videoInfo.getBvId(), videoData.getTitle(), videoData.getDesc());
        logger.info(videoDataMessage);
        for (PlayDashInfo video : videoList) {
            String message = String.format("mimeType:%s, codecs:%s, quality:%s, baseUrl:%s", video.getMimeType(), video.getCodecs(), video.getId(), video.getBaseUrl());
            logger.info(message);
        }
        for (PlayDashInfo audio : audioList) {
            String message = String.format("mimeType:%s, codecs:%s, quality:%s, baseUrl:%s", audio.getMimeType(), audio.getCodecs(), audio.getId(), audio.getBaseUrl());
            logger.info(message);
        }
    }

}

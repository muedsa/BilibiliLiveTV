import com.muedsa.bilibililiveapiclient.BilibiliLiveApiClient;
import com.muedsa.bilibililiveapiclient.ChatBroadcastWsClient;
import com.muedsa.bilibililiveapiclient.model.AnchorBaseInfo;
import com.muedsa.bilibililiveapiclient.model.AnchorInfo;
import com.muedsa.bilibililiveapiclient.model.BilibiliPageInfo;
import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.DanmuHostInfo;
import com.muedsa.bilibililiveapiclient.model.DanmuInfo;
import com.muedsa.bilibililiveapiclient.model.LargeInfo;
import com.muedsa.bilibililiveapiclient.model.LiveRoomInfo;
import com.muedsa.bilibililiveapiclient.model.PlayUrlData;
import com.muedsa.bilibililiveapiclient.model.Qn;
import com.muedsa.bilibililiveapiclient.model.RoomInfo;
import com.muedsa.bilibililiveapiclient.model.search.SearchAggregation;
import com.muedsa.bilibililiveapiclient.uitl.ApiUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
        BilibiliResponse<DanmuInfo> response = client.getDanmuInfo(roomId);
        Assertions.assertEquals(0L, response.getCode());
        DanmuInfo danmuInfo = response.getData();
        Assertions.assertNotNull(danmuInfo);
        Assertions.assertNotNull(danmuInfo.getToken());
        Assertions.assertNotNull(danmuInfo.getHostList());
        String message = String.format("roomId:%d, token:%s", roomId, danmuInfo.getToken());
        logger.info(message);
        for (DanmuHostInfo danmuHostInfo : danmuInfo.getHostList()) {
            message = String.format("host:%s, wsPort:%d", danmuHostInfo.getHost(), danmuHostInfo.getWsPort());
            logger.info(message);
        }
    }

    @Test
    public void wsTest() throws IOException, InterruptedException {
        BilibiliResponse<DanmuInfo> response = client.getDanmuInfo(roomId);
        Assertions.assertEquals(0L, response.getCode());
        DanmuInfo danmuInfo = response.getData();
        Assertions.assertNotNull(danmuInfo);
        Assertions.assertNotNull(danmuInfo.getToken());
        ChatBroadcastWsClient client = new ChatBroadcastWsClient(roomId, danmuInfo.getToken());
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
}

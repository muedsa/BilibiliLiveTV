import com.muedsa.bilibililiveapiclient.BilibiliLiveApiClient;
import com.muedsa.bilibililiveapiclient.model.BilibiliPageInfo;
import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.LiveRoomInfo;
import com.muedsa.bilibililiveapiclient.model.PlayUrlData;
import com.muedsa.bilibililiveapiclient.model.Qn;
import com.muedsa.bilibililiveapiclient.model.RoomInfo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.logging.Logger;

public class BilibiliLiveApiClientTest {

    private static BilibiliLiveApiClient client;

    private static final Logger logger = Logger.getGlobal();

    @BeforeAll
    public static void init(){
        client = new BilibiliLiveApiClient();
    }

    @Test
    public void playUrlTest() throws IOException {
        BilibiliResponse<PlayUrlData> response = client.getPlayUrlMessage(3L, Qn.RAW);
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
    public void getRoomInfo() throws IOException {
        BilibiliResponse<RoomInfo> response = client.getRoomInfo(3L);
        Assertions.assertEquals(0L, response.getCode());
        RoomInfo roomInfo = response.getData();
        Assertions.assertNotNull(roomInfo);
        Assertions.assertNotNull(roomInfo.getRoomId());
        Assertions.assertNotNull(roomInfo.getTitle());
        String message = String.format("roomId:%d, title:%s", roomInfo.getRoomId(), roomInfo.getTitle());
        logger.info(message);
    }
}

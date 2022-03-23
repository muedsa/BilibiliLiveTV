package com.muedsa.bilibililiveapiclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.DanmuInfo;
import com.muedsa.bilibililiveapiclient.model.chat.ChatBroadcast;
import com.muedsa.bilibililiveapiclient.uitl.ChatBroadcastPacketUtil;
import com.muedsa.httpjsonclient.HttpJsonClient;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

public class ChatBroadcastWsClient {

    private long roomId;

    private String token;

    private WebSocketClient webSocketClient;

    private Timer heartTimer;

    private CallBack callBack;

    public ChatBroadcastWsClient(long roomId){
        this.roomId = roomId;
        webSocketClient = new WebSocketClient(URI.create(ApiUrlContainer.WS_CHAT), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                //System.out.println("[Open]");
            }

            @Override
            public void onMessage(ByteBuffer byteBuffer) {
                if(callBack != null){
                    List<String> msgList = ChatBroadcastPacketUtil.decode(byteBuffer, null);
                    if(!msgList.isEmpty()){
                        for (String msg : msgList) {
                            try{
                                JSONObject jsonObject = JSON.parseObject(msg);
                                String cmd = jsonObject.getString("cmd");
                                if(ChatBroadcast.CMD_DANMU_MSG.equals(cmd)){
                                    JSONArray infoJsonArray = jsonObject.getJSONArray("info");
                                    String content = infoJsonArray.getString(1);
                                    callBack.onReceiveDanmu(content);
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }

            @Override
            public void onMessage(String message) {
                //System.out.println("Rec: "+ message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                String message = String.format(Locale.CHINA, "[Close] code:%d reason:%s remote:%b", code, reason, remote);
                System.out.println(message);
            }

            @Override
            public void onError(Exception ex) {
                //ex.printStackTrace();
            }
        };
    }

    public void start() throws IOException, InterruptedException  {
        BilibiliLiveApiClient bilibiliLiveApiClient = new BilibiliLiveApiClient();
        BilibiliResponse<DanmuInfo> danmuInfoResponse = bilibiliLiveApiClient.getDanmuInfo(roomId);
        token = danmuInfoResponse.getData().getToken();
        webSocketClient.connectBlocking();
        String joinRoomJson = String.format(Locale.CHINA, ChatBroadcastPacketUtil.ROOM_AUTH_JSON, roomId, token);
        webSocketClient.send(ChatBroadcastPacketUtil.encode(joinRoomJson, 1, ChatBroadcastPacketUtil.OPERATION_AUTH));
        heartTimer = new Timer();
        heartTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                webSocketClient.send(ChatBroadcastPacketUtil.HEART_PACKET);
            }
        }, 1000, 30000);
    }

    public void close(){
        heartTimer.cancel();
        heartTimer = null;
        webSocketClient.close();
        webSocketClient = null;
    }

    public boolean isClosed(){
       return webSocketClient.isClosed();
    }

    public boolean isOpen(){
        return webSocketClient.isOpen();
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack{
//        void onOpen();
//
//        void onStart();

        void onReceiveDanmu(String content);

        void onClose();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ChatBroadcastWsClient client = new ChatBroadcastWsClient(1440094);
        client.start();
        client.setCallBack(new CallBack() {
            @Override
            public void onReceiveDanmu(String content) {
                System.out.println(content);
            }

            @Override
            public void onClose() {

            }
        });
    }
}

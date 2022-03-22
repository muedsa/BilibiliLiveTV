package com.muedsa.bilibililiveapiclient;

import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.DanmuInfo;
import com.muedsa.bilibililiveapiclient.uitl.ChatBroadcastPacketUtil;
import com.muedsa.httpjsonclient.HttpJsonClient;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.Callable;

public class ChatBroadcastWsClient {

    private long roomId;

    private String token;

    private WebSocketClient webSocketClient;

    private Callable danmuCallback;

    public ChatBroadcastWsClient(long roomId,  Callable danmuCallback){
        this(roomId);
        this.danmuCallback = danmuCallback;
    }

    public ChatBroadcastWsClient(long roomId){
        this.roomId = roomId;
        webSocketClient = new WebSocketClient(URI.create(ApiUrlContainer.WS_CHAT), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("[Open]");
            }

            @Override
            public void onMessage(ByteBuffer byteBuffer) {
                ChatBroadcastPacketUtil.decode(byteBuffer);
            }

            @Override
            public void onMessage(String message) {
                System.out.println("Rec: "+ message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                String message = String.format(Locale.CHINA, "[Close] code:%d reason:%s remote:%b", code, reason, remote);
                System.out.println(message);
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };
    }

    public WebSocketClient start() throws IOException, InterruptedException  {
        BilibiliLiveApiClient bilibiliLiveApiClient = new BilibiliLiveApiClient();
        BilibiliResponse<DanmuInfo> danmuInfoResponse = bilibiliLiveApiClient.getDanmuInfo(roomId);
        token = danmuInfoResponse.getData().getToken();
        webSocketClient.connectBlocking();
        String joinRoomJson = String.format(Locale.CHINA, ChatBroadcastPacketUtil.ROOM_AUTH_JSON, roomId, token);
        webSocketClient.send(ChatBroadcastPacketUtil.encode(joinRoomJson, 1, ChatBroadcastPacketUtil.OPERATION_AUTH));
        new Thread(() -> {
            while (webSocketClient.isOpen()){
                webSocketClient.send(ChatBroadcastPacketUtil.HEART_PACKET);
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Uncaught", e);
                }
            }
        }).start();
        return webSocketClient;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ChatBroadcastWsClient client = new ChatBroadcastWsClient(3);
        client.start();
    }
}

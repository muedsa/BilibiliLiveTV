package com.muedsa.bilibililiveapiclient;

import com.muedsa.bilibililiveapiclient.uitl.ChatBroadcastPacketUtil;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

public class ChatBroadcastWsClient {

    private WebSocketClient webSocketClient;

    public ChatBroadcastWsClient(){
        webSocketClient = new WebSocketClient(URI.create(ApiUrlContainer.WS_CHAT)) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                getConnection().send(ChatBroadcastPacketUtil.encode("{\"clientver\":\"1.6.3\",\"platform\":\"web\",\"protover\":2,\"roomid\":23058,\"uid\":0,\"type\":2}", 2));
                new Thread(() -> {
                    while (getConnection().isOpen()){
                        getConnection().send(ChatBroadcastPacketUtil.HEART_PACKET);
                        try {
                            Thread.sleep(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            throw new RuntimeException("Uncaught", e);
                        }
                    }
                }).start();
            }

            @Override
            public void onMessage(ByteBuffer byteBuffer) {
                ChatBroadcastPacketUtil.decode(byteBuffer);
            }

            @Override
            public void onMessage(String message) {

            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println(reason);
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };
    }

    public void start() throws InterruptedException {
        System.out.println("start");
        webSocketClient.connect();
    }
}

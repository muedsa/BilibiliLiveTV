package com.muedsa.bilibililiveapiclient;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.muedsa.bilibililiveapiclient.model.BilibiliResponse;
import com.muedsa.bilibililiveapiclient.model.chat.ChatBroadcast;
import com.muedsa.bilibililiveapiclient.model.live.DanmakuInfo;
import com.muedsa.bilibililiveapiclient.util.ChatBroadcastPacketUtil;
import com.muedsa.httpjsonclient.HttpClientContainer;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ChatBroadcastWsClient {

    private final long roomId;

    private final String token;

    private WebSocketClient webSocketClient;

    private Timer heartTimer;


    private CallBack callBack;

    public ChatBroadcastWsClient(long roomId, String token) {
        this(roomId, token, ApiUrlContainer.WS_CHAT);
    }

    public ChatBroadcastWsClient(long roomId, String token, String url) {
        this.roomId = roomId;
        this.token = token;
        HashMap<String, String> headers = new HashMap<>();
        headers.put(HttpClientContainer.HEADER_KEY_USER_AGENT, HttpClientContainer.HEADER_VALUE_USER_AGENT);
        webSocketClient = new WebSocketClient(URI.create(url), new Draft_6455(), headers) {
            @Override
            public void onOpen(ServerHandshake handshakeData) {
                if (callBack != null) callBack.onStart();
            }

            @Override
            public void onMessage(ByteBuffer byteBuffer) {
                if (callBack != null) {
                    List<String> msgList = ChatBroadcastPacketUtil.decode(byteBuffer, null);
                    if (!msgList.isEmpty()) {
                        for (String msg : msgList) {
                            try {
                                JSONObject jsonObject = JSON.parseObject(msg);
                                String cmd = jsonObject.getString("cmd");
                                if (ChatBroadcast.CMD_DANMU_MSG.equals(cmd)) {
                                    JSONArray infoJsonArray = jsonObject.getJSONArray("info");
                                    JSONArray propertyJsonArray = infoJsonArray.getJSONArray(0);
                                    float textSize = propertyJsonArray.getFloatValue(2);
                                    int textColor = (int) (0x00000000ff000000L | propertyJsonArray.getLongValue(3));
                                    boolean textShadowTransparent = "true".equalsIgnoreCase(propertyJsonArray.getString(11));
                                    String text = infoJsonArray.getString(1);
                                    callBack.onReceiveDanmu(text, textSize, textColor, textShadowTransparent, msg);
                                } else if (ChatBroadcast.CMD_SUPER_CHAT_MESSAGE.equals(cmd) || ChatBroadcast.CMD_SUPER_CHAT_MESSAGE_JPN.equals(cmd)) {
                                    JSONObject dataJsonData = jsonObject.getJSONObject("data");
                                    String message = dataJsonData.getString("message");
                                    String messageFontColor = dataJsonData.getString("message_font_color");
                                    JSONObject userInfoJsonObject = dataJsonData.getJSONObject("user_info");
                                    String uname = userInfoJsonObject.getString("uname");
                                    callBack.onReceiveSuperChatMessage(message, messageFontColor, uname, msg);
                                } else if (ChatBroadcast.CMD_SEND_GIFT.equals(cmd)) {
                                    JSONObject dataJsonData = jsonObject.getJSONObject("data");
                                    String action = dataJsonData.getString("action");
                                    String giftName = dataJsonData.getString("giftName");
                                    Integer num = dataJsonData.getInteger("num");
                                    String uname = dataJsonData.getString("uname");
                                    callBack.onReceiveSendGift(action, giftName, num, uname, msg);
                                } else {
                                    callBack.onReceiveOtherMessage(msg);
                                }
                            } catch (Exception e) {
                                callBack.onError(e);
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
                if (callBack != null) {
                    callBack.onClose(code, reason, remote);
                }
            }

            @Override
            public void onError(Exception ex) {
                if (callBack != null) {
                    callBack.onError(ex);
                }
            }
        };
    }

    public void start() throws InterruptedException {
        if (!webSocketClient.isOpen()) {
            webSocketClient.connectBlocking();
            String joinRoomJson = String.format(Locale.CHINA, ChatBroadcastPacketUtil.ROOM_AUTH_JSON, roomId, token);
            webSocketClient.send(ChatBroadcastPacketUtil.encode(joinRoomJson, 1, ChatBroadcastPacketUtil.OPERATION_AUTH));
        }
        if (heartTimer == null) {
            heartTimer = new Timer();
            heartTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (webSocketClient.isOpen()) {
                        webSocketClient.send(ChatBroadcastPacketUtil.HEART_PACKET);
                    }
                }
            }, 1000, 30000);
        }
    }

    public void close() {
        if (heartTimer != null) {
            heartTimer.cancel();
            heartTimer = null;
        }
        if (webSocketClient != null) {
            webSocketClient.close();
            webSocketClient = null;
        }
    }

    public boolean isClosed() {
        return webSocketClient.isClosed();
    }

    public boolean isOpen() {
        return webSocketClient.isOpen();
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        long roomId = 22603245;
        BilibiliLiveApiClient httpClient = new BilibiliLiveApiClient();
        BilibiliResponse<DanmakuInfo> response = httpClient.getDanmuInfo(roomId);
        String url = "wss://" + response.getData().getHostList().get(0).getHost() + ":" + response.getData().getHostList().get(0).getWssPort() + "/sub";
//        System.out.println("connect to: " + url);
        ChatBroadcastWsClient client = new ChatBroadcastWsClient(roomId, response.getData().getToken(), url);
        client.setCallBack(new CallBack() {

            @Override
            public void onStart() {
            }

            @Override
            public void onReceiveDanmu(String text, float textSize, int textColor, boolean textShadowTransparent, String origin) {
                //String message = String.format(Locale.CHINA, "text:%s, textSize:%f, textColor:%d, textShadowTransparent:%b", text, textSize, textColor, textShadowTransparent);
                //System.out.println(message);
            }

            @Override
            public void onReceiveSuperChatMessage(String message, String messageFontColor, String uname, String origin) {
//                String m = String.format(Locale.CHINA, "[SC]%s:%s, textColor:%s", uname, message, messageFontColor);
//                System.out.println(m);
            }

            @Override
            public void onReceiveSendGift(String action, String giftName, Integer num, String uname, String origin) {
//                String m = String.format(Locale.CHINA, "[礼物]%s%s%sX%d", uname, action, giftName, num);
//                System.out.println(origin);
            }

            @Override
            public void onReceiveOtherMessage(String message) {
//                System.out.println(message);
            }
        });
        client.start();
    }

    public abstract static class CallBack {
        protected void onStart() {
        }

        protected void onReceiveDanmu(String text, float textSize, int textColor, boolean textShadowTransparent, String origin) {
        }

        protected void onReceiveSuperChatMessage(String message, String messageFontColor, String uname, String origin) {
        }

        protected void onReceiveSendGift(String action, String giftName, Integer num, String uname, String origin) {
        }

        protected void onReceiveOtherMessage(String message) {
        }

        protected void onClose(int code, String reason, boolean remote) {
        }

        protected void onError(Throwable throwable) {
        }
    }
}

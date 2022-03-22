package com.muedsa.bilibililiveapiclient.uitl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

import sun.nio.ByteBuffered;

public class ChatBroadcastPacketUtil {

    public final static byte[] HEART_PACKET = new byte[]{0, 0, 0, 16, 0, 16, 0, 1, 0, 0, 0, 2, 0, 0, 0, 1};

    public final static String ROOM_AUTH_JSON = "{\"uid\":0,\"roomid\":%d,\"protover\":2,\"platform\":\"web\",\"type\":2,\"key\":\"%s\"}";

    public final static int PROTOCOL_JSON = 0;
    public final static int PROTOCOL_INT32 = 1;
    public final static int PROTOCOL_ZIP = 2;
    public final static int PROTOCOL_BROTLI = 3;

    public final static int OPERATION_HEART = 2;
    public final static int OPERATION_HOT = 3;
    public final static int OPERATION_NOTIFY = 5;
    public final static int OPERATION_AUTH = 7;
    public final static int OPERATION_AUTH_SUCCESS = 8;

    public static ByteBuffer encode(String content, int protocolVersion, int operation){
        byte[] body = content.getBytes(StandardCharsets.UTF_8);
        ByteBuffer packet = ByteBuffer.allocate(16 + body.length);
        packet.putInt(16 + body.length);
        packet.putShort((short) 16);
        packet.putShort((short) protocolVersion);
        packet.putInt(operation);
        packet.putInt(1);
        packet.put(body);
        packet.compact();
        ByteBufferUtil.dump(packet);
        System.out.println(ByteBufferUtil.toHex(packet));
        return packet;
    }

    public static void decode(ByteBuffer byteBuffer){
        int packetLength = byteBuffer.getInt(0);
        short headerLength = byteBuffer.getShort(4);
        short protocolVersion = byteBuffer.getShort(6);
        int operation = byteBuffer.getInt(8);
        int sequenceId = byteBuffer.getInt(12);
        if(protocolVersion == PROTOCOL_JSON || protocolVersion == PROTOCOL_INT32){
            System.out.printf(Locale.CHINA, "[Rec] packetLength:%d, headerLength:%d, protocolVersion:%d, operation:%d, sequenceId:%d\n",
                    packetLength, headerLength, protocolVersion, operation, sequenceId);
        }
        switch (protocolVersion){
            case PROTOCOL_JSON:
                System.out.println("Data: ");
                byteBuffer.position(16);
                ByteBuffer dataByteBuffer = byteBuffer.slice();
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(dataByteBuffer);
                String[] msgArr = charBuffer.toString().split("[\\x00-\\x1f]+");
                for (String msg : msgArr) {
                    if(msg.length() > 1){
                        System.out.println(msg);
                    }
                }
                break;
            case PROTOCOL_INT32:
                System.out.print("Data: ");
                int hot = byteBuffer.getInt();
                System.out.println("HOT="+ hot);
                break;
            case PROTOCOL_ZIP:
                byte[] input = new byte[packetLength - headerLength];
                byteBuffer.position(16);
                byteBuffer.get(input);
                byte[] output = InflateUtil.unZip(input);
                if(output.length > 0){
                    ByteBuffer unzipByteBuffer = ByteBuffer.wrap(output);
                    decode(unzipByteBuffer);
                }
                break;
            case PROTOCOL_BROTLI:
                System.out.println("!PROTOCOL_BROTLI");
                break;
            default:
                System.out.println("!new protocolVersion" + protocolVersion);
                break;
        }
    }
}

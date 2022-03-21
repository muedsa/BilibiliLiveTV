package com.muedsa.bilibililiveapiclient.uitl;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.Inflater;

public class ChatBroadcastPacketUtil {

    public final static byte[] HEART_PACKET = new byte[]{0,0,0,0,0,16,0,1,0,0,0,2,0,0,0,1};

    public final static int PROTOCOL_JSON = 0;
    public final static int PROTOCOL_INT32 = 1;
    public final static int PROTOCOL_ZIP = 2;
    public final static int PROTOCOL_BROTLI = 3;

    public static byte[] encode(String content, int operation){
        byte[] body = content.getBytes(StandardCharsets.UTF_8);
        byte[] packet = new byte[16 + body.length];
        System.arraycopy(HEART_PACKET, 0, packet, 0, HEART_PACKET.length);
        packet[11] = (byte) operation;
        System.arraycopy(body, 0, packet, 16, body.length);
        return packet;
    }

    public static void decode(ByteBuffer byteBuffer){
        int packetLength = byteBuffer.getInt(0);
        int headerLength = byteBuffer.getShort(4);
        int protocolVersion = byteBuffer.getShort(6);
        int operation = byteBuffer.getInt(8);
        int sequenceId = byteBuffer.getInt(12);
        byte[] bodyByteArr = new byte[6];
        byteBuffer.get(bodyByteArr, 16, packetLength - headerLength);
        switch (protocolVersion){
            case PROTOCOL_JSON:
                System.out.println(Arrays.toString(bodyByteArr));
                break;
            case PROTOCOL_INT32:
                int hot = (bodyByteArr[0]&0xff)<<24 | (bodyByteArr[1]&0xff) << 16 | (bodyByteArr[2]&0xff) << 8 | (bodyByteArr[4]&0xff);
                System.out.println("人气:"+ hot);
                break;
            case PROTOCOL_ZIP:
                Inflater decompress = new Inflater();
                //decompress.
                break;
            case PROTOCOL_BROTLI:
                break;
        }
    }
}

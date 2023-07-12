package com.muedsa.bilibililiveapiclient.util;

import java.nio.ByteBuffer;

public class ByteBufferUtil {
    public static void dump(ByteBuffer byteBuffer) {
        StringBuilder dump = new StringBuilder();
        byte[] bytes = byteBuffer.array();
        for (int i = 0; i < bytes.length; i++) {
            int lineMod = i % 16;
            if (lineMod == 0) {
                dump.append(fullPrefix(Integer.toHexString(i), 8));
                dump.append(": ");
            }
            dump.append(fullPrefix(Integer.toHexString(bytes[i] & 0xFF), 2));
            int mod = i % 2;
            if (mod == 1) {
                dump.append(" ");
            }
            if (lineMod == 15) {
                dump.append("\n");
            }
        }
        System.out.println(dump);
    }

    public static String toHex(ByteBuffer byteBuffer) {
        StringBuilder dump = new StringBuilder();
        byte[] bytes = byteBuffer.array();
        for (int i = 0; i < bytes.length; i++) {
            dump.append(fullPrefix(Integer.toHexString(bytes[i] & 0xFF), 2));
        }
        return dump.toString();
    }

    private static String fullPrefix(String content, int max) {
        if (content.length() < max) {
            StringBuilder prefixBuilder = new StringBuilder();
            for (int i = 0; i < max - content.length(); i++) {
                prefixBuilder.append("0");
            }
            content = prefixBuilder + content;
        }
        return content;
    }
}

package com.muedsa.bilibililiveapiclient.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Inflater;

public class InflateUtil {

    public static byte[] unZip(byte[] input) {
        Inflater inflater = new Inflater();
        inflater.setInput(input);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(input.length);
        try {
            byte[] buff = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buff);
                byteArrayOutputStream.write(buff, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        inflater.end();
        return byteArrayOutputStream.toByteArray();
    }
}

package com.muedsa.httpjsonclient;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class IOUtil {

    public static byte[] convertStreamToByteArray(InputStream inputStream, String coding) throws IOException {
        if (Objects.nonNull(coding)) {
            if (HttpClientContainer.HEADER_VALUE_PART_ENCODING_IDENTITY.equals(coding)) {
                return decodeStreamToByteArray(inputStream);
            } else if (HttpClientContainer.HEADER_VALUE_PART_ENCODING_GZIP.equals(coding)) {
                return decodeStreamToByteArray(new GZIPInputStream(inputStream));
            } else {
                throw new IllegalStateException("coding " + coding + " not supported");
            }
        } else {
            return decodeStreamToByteArray(inputStream);
        }
    }

    public static String convertStreamToString(InputStream inputStream, String coding, String charset) throws IOException {
        byte[] bytes = convertStreamToByteArray(inputStream, coding);
        return new String(bytes, charset);
    }

    private static byte[] decodeStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 8];
        int n;
        try {
            while ((n = inputStream.read(buffer)) >= 0) {
                byteArrayOutputStream.write(buffer, 0, n);
            }
            return byteArrayOutputStream.toByteArray();
        } finally {
            safeClose(byteArrayOutputStream);
            safeClose(inputStream);
        }
    }

    public static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

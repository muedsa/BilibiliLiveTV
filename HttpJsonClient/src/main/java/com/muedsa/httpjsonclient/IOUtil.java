package com.muedsa.httpjsonclient;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class IOUtil {

    public static String convertStreamToString(InputStream inputStream, String coding, String charset) throws IOException {
        if (Objects.nonNull(coding)) {
            if (Container.HEADER_VALUE_PART_ENCODING_IDENTITY.equals(coding)) {
                return decodeStreamToString(inputStream, charset);
            } else if (Container.HEADER_VALUE_PART_ENCODING_GZIP.equals(coding)) {
                return gzipStreamToString(inputStream, charset);
            } else {
                throw new IllegalStateException("coding " + coding + " not supported");
            }
        } else {
            return decodeStreamToString(inputStream, charset);
        }
    }

    private static String decodeStreamToString(InputStream inputStream, String charset) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 8];
        int n;
        try {
            while ((n = inputStream.read(buffer)) >= 0) {
                byteArrayOutputStream.write(buffer, 0, n);
            }
            return byteArrayOutputStream.toString(charset);
        } catch (Exception e) {
            throw e;
        } finally {
            safeClose(inputStream);
            safeClose(byteArrayOutputStream);
        }
    }

    private static String gzipStreamToString(InputStream inputStream, String charset) throws IOException {
        GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
        return decodeStreamToString(gzipInputStream, charset);
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

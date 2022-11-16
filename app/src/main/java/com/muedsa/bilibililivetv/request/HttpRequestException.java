package com.muedsa.bilibililivetv.request;

import com.muedsa.bilibililiveapiclient.ErrorCode;

public class HttpRequestException extends RuntimeException {

    public final long code;

    public HttpRequestException(long code, String message) {
        super(message);
        this.code = code;
    }

    public long getCode() {
        return code;
    }

    public static HttpRequestException create(String message) {
        return new HttpRequestException(ErrorCode.UNKNOWN, message);
    }

    public static HttpRequestException create(long code, String message) {
        return new HttpRequestException(code, message);
    }
}

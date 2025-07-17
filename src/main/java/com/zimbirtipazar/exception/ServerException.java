package com.zimbirtipazar.exception;

import org.springframework.http.HttpStatusCode;

public class ServerException extends BaseException {

    private ServerException(ErrorCode errorCode, String detail) {
        super(errorCode, detail, HttpStatusCode.valueOf(500));
    }

    private ServerException(ErrorCode errorCode, String detail, HttpStatusCode httpStatusCode) {
        super(errorCode, detail, httpStatusCode);
    }

    public static ServerException of(ErrorCode errorCode) {
        return new ServerException(errorCode, null);
    }

    public static ServerException of(ErrorCode errorCode, HttpStatusCode httpStatusCode) {
        return new ServerException(errorCode, null, httpStatusCode);
    }

    public static ServerException of(ErrorCode errorCode, String detail) {
        return new ServerException(errorCode, detail);
    }

    public static ServerException of(ErrorCode errorCode, String detail, HttpStatusCode httpStatusCode) {
        return new ServerException(errorCode, detail, httpStatusCode);
    }

}
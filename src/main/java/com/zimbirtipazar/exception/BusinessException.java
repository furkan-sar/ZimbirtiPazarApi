package com.zimbirtipazar.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class BusinessException extends BaseException {

    private BusinessException(ErrorCode errorCode, String detail) {
        super(errorCode, detail, HttpStatusCode.valueOf(400));
    }

    private BusinessException(ErrorCode errorCode, String detail, HttpStatusCode httpStatusCode) {
        super(errorCode, detail, httpStatusCode);
    }

    public static BusinessException of(ErrorCode errorCode) {
        return new BusinessException(errorCode, null);
    }

    public static BusinessException of(ErrorCode errorCode, HttpStatusCode httpStatusCode) {
        return new BusinessException(errorCode, null, httpStatusCode);
    }

    public static BusinessException of(ErrorCode errorCode, String detail) {
        return new BusinessException(errorCode, detail);
    }

    public static BusinessException of(ErrorCode errorCode, String detail, HttpStatusCode httpStatusCode) {
        return new BusinessException(errorCode, detail, httpStatusCode);
    }

}
package com.zimbirtipazar.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public abstract class BaseException extends RuntimeException {

    private final ErrorCode errorCode;

    private final HttpStatusCode httpStatusCode;

    protected BaseException(ErrorCode errorCode, String detail, HttpStatusCode httpStatusCode) {
        super(ExceptionMessage.build(errorCode, detail));

        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
    }

    protected BaseException(ErrorCode errorCode, HttpStatusCode httpStatusCode) {
        this(errorCode, null, httpStatusCode);
    }


}
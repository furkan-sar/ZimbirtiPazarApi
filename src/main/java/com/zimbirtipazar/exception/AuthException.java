package com.zimbirtipazar.exception;

import org.springframework.http.HttpStatusCode;

public class AuthException extends BaseException {

  private AuthException(ErrorCode errorCode, String detail) {
    super(errorCode, detail, HttpStatusCode.valueOf(500));
  }

  private AuthException(ErrorCode errorCode, String detail, HttpStatusCode httpStatusCode) {
    super(errorCode, detail, httpStatusCode);
  }

  public static AuthException of(ErrorCode errorCode) {
    return new AuthException(errorCode, null);
  }

  public static AuthException of(ErrorCode errorCode, HttpStatusCode httpStatusCode) {
    return new AuthException(errorCode, null, httpStatusCode);
  }

  public static AuthException of(ErrorCode errorCode, String detail) {
    return new AuthException(errorCode, detail);
  }

  public static AuthException of(ErrorCode errorCode, String detail, HttpStatusCode httpStatusCode) {
    return new AuthException(errorCode, detail, httpStatusCode);
  }

}
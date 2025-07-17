package com.zimbirtipazar.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    GENERAL_EXCEPTION("GENERAL_EXCEPTION", "Beklenmeyen bir durum oluştu."),
    BAD_REQUEST("BAD_REQUEST", "Geçersiz istek."),
    DUPLICATE_RECORD_EXIST("DUPLICATE_RECORD_EXIST", "Aynı değerde kayıt bulunmaktadır."),
    USER_NOT_FOUND("USER_NOT_FOUND", "Kullanıcı bulunamadı."),
    USER_NOT_ADMIN("USER_NOT_ADMIN", "Kullanıcı admin değil."),
    USER_ALREADY_ADMIN("USER_ALREADY_ADMIN", "Kullanıcı zaten admin."),
    USER_NOT_APPROVED("USER_NOT_APPROVED", "Hesap onaylanmamıştır."),
    USER_ALREADY_APPROVED("USER_ALREADY_APPROVED", "Hesap zaten onaylanmıştır."),
    TOKEN_IS_EXPIRED("TOKEN_IS_EXPIRED", "Oturum süreniz dolmuş."),
    INVALID_TOKEN("INVALID_TOKEN", "Geçersiz token."),
    BAD_CREDENTIALS("BAD_CREDENTIALS", "Kullanıcı adı ya da şifre yanlış."),
    UNAUTHORIZED("UNAUTHORIZED", "Unauthorized"),
    FORBIDDEN("FORBIDDEN", "Forbidden"),
    PASSWORD_NOT_VALID("PASSWORD_NOT_VALID", "Şifre en az 8 karakter olmalı, en az 1 büyük harf, 1 küçük harf, 1 rakam ve 1 özel karakter içermelidir."),
    USERNAME_ALREADY_TAKEN("USERNAME_ALREADY_TAKEN", "Bu kullanıcı adı zaten alınmış.");

    private final String code;

    private final String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
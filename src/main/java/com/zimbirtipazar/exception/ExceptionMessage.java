package com.zimbirtipazar.exception;

import org.springframework.util.StringUtils;

public class ExceptionMessage {

    private ExceptionMessage() {

    }

    protected static String build(ErrorCode code, String detail) {
        if (code == null) {
            throw ServerException.of(ErrorCode.GENERAL_EXCEPTION, "Hata mesajı oluşturulamadı.");
        }

        String baseMessage = code.getDescription();
        StringBuilder message = new StringBuilder(baseMessage);

        if (StringUtils.hasText(detail)) {
            message.append(": ").append(detail.trim());
        }

        return message.toString();
    }
}


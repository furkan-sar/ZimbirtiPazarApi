package com.zimbirtipazar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zimbirtipazar.exception.BaseException;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private Map<String, Object> exception;

    public static <T> ApiResponse<T> success(T data) {
        return success(data, null);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);

        return response;
    }

    public static ApiResponse<?> error(BaseException baseException) {
        return error(baseException, null);
    }

    public static <T> ApiResponse<?> error(BaseException baseException, T fields) {
        ApiResponse<?> response = new ApiResponse<>();
        response.exception = new HashMap<>();
        response.setSuccess(false);
        response.exception.put("errorCode", baseException.getErrorCode().getCode());
        response.exception.put("message", baseException.getMessage());
        response.exception.put("fields", fields);

        return response;
    }
}

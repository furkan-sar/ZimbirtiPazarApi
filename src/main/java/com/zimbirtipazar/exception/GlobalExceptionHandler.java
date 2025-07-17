package com.zimbirtipazar.exception;

import com.zimbirtipazar.dto.ApiResponse;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ApiResponse<?>> handleBaseException(BaseException e, WebRequest request) {
        return ResponseEntity.status(e.getHttpStatusCode()).body(ApiResponse.error(e));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                                WebRequest request) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage()
                                : "Geçersiz değer"));

        return ResponseEntity.badRequest().body(
                ApiResponse.error(BusinessException.of(ErrorCode.BAD_REQUEST, HttpStatusCode.valueOf(400)), errors));
    }

    @ExceptionHandler(value = {ServletException.class, HttpMessageConversionException.class})
    public ResponseEntity<?> handleExcludedExceptions() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(BusinessException.of(ErrorCode.BAD_REQUEST)));
    }

    @ExceptionHandler({AuthException.class})
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthException e) {
        return new ResponseEntity<>(ApiResponse.error(e), e.getHttpStatusCode());
    }

    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(Exception e) {
        return new ResponseEntity<>(ApiResponse.error(AuthException.of(ErrorCode.UNAUTHORIZED)), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(Exception e) {
        return new ResponseEntity<>(ApiResponse.error(AuthException.of(ErrorCode.FORBIDDEN)), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAllExceptions(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(BusinessException.of(ErrorCode.GENERAL_EXCEPTION)));
    }

}

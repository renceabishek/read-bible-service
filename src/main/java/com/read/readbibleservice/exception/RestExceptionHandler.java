package com.read.readbibleservice.exception;

import com.google.firebase.database.DatabaseException;
import com.google.protobuf.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserCustomException.class, DatabaseException.class})
    public ResponseEntity<Object> handleResourceNotFound(UserCustomException exception, WebRequest request) {
        return buildResponseEntity(exception.getMessage(), exception, exception.getErrorCode().getHttpStatus(),
                exception.getErrorCode().getHttpCode(), (ServletWebRequest)request);
    }

    private ResponseEntity<Object> buildResponseEntity(String message, Exception exception, HttpStatus status, Integer code,
                                                       ServletWebRequest request) {
        ApiError responseBody = createErrorResponse(message, exception, status, code, request);
        return ResponseEntity.status(status).body(responseBody);
    }

    private ApiError createErrorResponse(String message, Exception exception, HttpStatus status, Integer code,
                                         ServletWebRequest request) {
           return ApiError.builder()
                    .errorCode(code)
                    .status(status.value())
                    .message(message)
                    .exception(exception.getClass().getName())
                    .debugMessage(exception.getLocalizedMessage())
                    .path(request.getRequest().getServletPath())
                    .offsetDateTime(OffsetDateTime.now())
                    .subErrors(null)
                    .build();
    }
}

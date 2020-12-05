package com.read.readbibleservice.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    USER_NOT_CONFIRMED_BY_EMAIL(4100, HttpStatus.BAD_REQUEST),
    USER_ALREADY_REGISTERED(4101, HttpStatus.BAD_REQUEST),
    USER_NAME_NOT_FOUND(4102, HttpStatus.BAD_REQUEST);

    int httpCode;
    HttpStatus httpStatus;

    ErrorCode(int httpCode, HttpStatus httpStatus) {
        this.httpCode = httpCode;
        this.httpStatus = httpStatus;
    }
}

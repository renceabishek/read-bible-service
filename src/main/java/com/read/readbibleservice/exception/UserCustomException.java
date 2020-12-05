package com.read.readbibleservice.exception;

import lombok.Data;
import lombok.Getter;

@Getter
public class UserCustomException extends RuntimeException {
    public ErrorCode errorCode;

    public UserCustomException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

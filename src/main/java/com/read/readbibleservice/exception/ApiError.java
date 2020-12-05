package com.read.readbibleservice.exception;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private OffsetDateTime offsetDateTime;
    private int status;
    private int errorCode;
    private String exception;
    private String message;
    private String path;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    abstract static class ApiSubError {

    }
}

package com.ayenijeremiaho.pastebinapi.exception;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
@Builder
public class ExceptionMessage {
    private String message;

    static ExceptionMessage get(Exception exception) {
        log.error("Error occurred => {}, error message is => {}", exception.getClass(), exception.getMessage());

        return ExceptionMessage.builder().message(exception.getMessage()).build();
    }

    static ExceptionMessage get(Class<?> aClass, String message) {
        log.error("Error occurred => {}, error message is => {}", aClass, message);

        return ExceptionMessage.builder().message(message).build();
    }

}

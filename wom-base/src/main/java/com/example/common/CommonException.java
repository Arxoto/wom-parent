package com.example.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonException extends RuntimeException {
    private final HttpStatus status;

    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public CommonException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    public CommonException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public static CommonException of(String message) {
        return new CommonException(message, HttpStatus.BAD_REQUEST);
    }

    public static CommonException of(Throwable cause) {
        return new CommonException(cause, HttpStatus.BAD_REQUEST);
    }
}

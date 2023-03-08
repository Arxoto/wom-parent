package com.example.server;

import com.example.common.RestErrorMessage;
import com.example.common.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<RestErrorMessage> handleException(CommonException e) {
        return new ResponseEntity<>(RestErrorMessage.of(e), e.getStatus());
    }
}

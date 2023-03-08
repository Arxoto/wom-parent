package com.example.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestErrorMessage {
    private String errorMessage;

    public static RestErrorMessage of(CommonException e) {
        return RestErrorMessage.builder()
                .errorMessage(e.getMessage())
                .build();
    }
}

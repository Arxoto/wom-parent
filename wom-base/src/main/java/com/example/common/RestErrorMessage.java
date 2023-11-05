package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestErrorMessage {
    private String errorMessage;

    public static RestErrorMessage of(CommonException e) {
        return new RestErrorMessage(e.getMessage());
    }
}

package com.example.lionking.global.error.exception;

import com.example.lionking.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CustomException(Throwable cause, ErrorCode errorCode) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}

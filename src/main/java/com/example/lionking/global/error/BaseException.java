package com.example.lionking.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final ErrorCode code;
    private final HttpStatus httpStatus;

    public BaseException(ErrorCode code,HttpStatus httpStatus) {
        super(code.getMessage());
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public BaseException (ErrorCode code, HttpStatus httpStatus , String message) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}

package com.example.lionking.global.error.exception;


import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import com.example.lionking.global.response.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;


@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(CustomException e) {
        log.error("CustomException : {}", e.getErrorCode().getMessage(), e);
        return ErrorResponse.createErrorResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class,
            HttpMessageNotReadableException.class,
            InvalidFormatException.class,
            ServletRequestBindingException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return ErrorResponse.createErrorResponseEntity(GlobalErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception e) {
        log.error("NotFoundException : {}", e.getMessage(), e);
        return ErrorResponse.createErrorResponseEntity(GlobalErrorCode.NOT_FOUND);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("NoHandlerFoundException : {}", e.getMessage(), e);
        return ErrorResponse.createErrorResponseEntity(GlobalErrorCode.NOT_SUPPORTED_URI);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return ErrorResponse.createErrorResponseEntity(GlobalErrorCode.INTERNAL_SERVER_ERROR);
    }
}
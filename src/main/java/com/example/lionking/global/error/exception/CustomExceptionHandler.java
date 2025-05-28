package com.example.lionking.global.error.exception;


import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.response.ApiResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleBusinessException(CustomException e) {
        log.error("customException : {}", e.getErrorCode().getMessage(), e);
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class,
            HttpMessageNotReadableException.class,
            InvalidFormatException.class,
            ServletRequestBindingException.class
    })
    public ResponseEntity<ApiResponse> handleBadRequestException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return ResponseEntity
                .status(GlobalErrorCode.BAD_REQUEST.getStatus())
                .body(ApiResponse.fail(GlobalErrorCode.BAD_REQUEST));
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundException(Exception e) {
        log.error("NotFoundException : {}", e.getMessage(), e);
        return ResponseEntity
                .status(GlobalErrorCode.NOT_FOUND.getStatus())
                .body(ApiResponse.fail(GlobalErrorCode.NOT_FOUND));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return ResponseEntity
                .status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiResponse.fail(GlobalErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
        // Validation 에러 시 바로 응답 반환하고 서비스 로직 실행 안 함
        return ResponseEntity.badRequest().body("Validation 실패");
    }

}
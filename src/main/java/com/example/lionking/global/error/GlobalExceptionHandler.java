package com.example.lionking.global.error;


import com.example.lionking.global.response.ApiResponse;
import jakarta.persistence.TransactionRequiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(TransactionRequiredException.class)
    public ResponseEntity<String> handleTransactionRequiredException(TransactionRequiredException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("트랜잭션이 필요합니다. 서비스 메서드에 @Transactional을 추가해주세요.");
    }


/*    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse> handleBusinessException(BaseException e) {
        log.info("GuestBookException Exception: {}", e.getMessage());

        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiResponse.fail(e.getError()));
    }*/


}

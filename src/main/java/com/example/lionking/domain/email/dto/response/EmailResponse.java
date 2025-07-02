package com.example.lionking.domain.email.dto.response;

import com.example.lionking.domain.email.entity.Email;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailResponse {
    private String message;
    private boolean success;

    @Builder
    public EmailResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public EmailResponse(Email email) {

    }
}

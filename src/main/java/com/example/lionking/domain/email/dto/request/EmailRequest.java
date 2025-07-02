package com.example.lionking.domain.email.dto.request;

import com.example.lionking.domain.email.entity.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class EmailRequest {
    @NotBlank(message = "이메일은 필수입니다")
    @jakarta.validation.constraints.Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    @Builder
    public EmailRequest(String email) {
        this.email = email;
    }

    public Email toNotifyEmail(EmailRequest req) {
        return Email.builder()
                .email(req.email)
                .notified(true)
                .build();
    }
}

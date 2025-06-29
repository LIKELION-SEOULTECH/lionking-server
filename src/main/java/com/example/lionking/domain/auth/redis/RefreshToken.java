package com.example.lionking.domain.auth.redis;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {

    private Long userId;
    private String token;
    private LocalDateTime expiry;

}
package com.example.lionking.domain.email.entity;

import com.example.lionking.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean notified;

    @Builder
    public Email(String email, boolean notified) {
        this.email = email;
        this.notified = notified;
    }

    public void setNotified(boolean b) {
        this.notified = b;
    }
}

package com.example.lionking.domain.member.entity;

import com.example.lionking.domain.Project.entity.Project;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Enumerated(value = EnumType.STRING)
    private Position position;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ManyToOne
    private Project project;

    @Column(length = 300)
    private String ProjectReviews;

    @Builder
    public Member(String username, Position position, Role role, String ProjectReviews) {
        this.username = username;
        this.position = position;
        this.role = role;
        this.ProjectReviews = ProjectReviews;
    }
}

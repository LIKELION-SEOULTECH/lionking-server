package com.example.lionking.domain.member.entity;

import com.example.lionking.domain.project.entity.ProjectImage;
import com.example.lionking.domain.project.entity.ProjectParticipation;
import com.example.lionking.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    /**
     * 회원가입 시 등록되는 정보
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;

    @Enumerated(value = EnumType.STRING)
    private Position position;
    @Enumerated(value = EnumType.STRING)
    private Role role;

    private int generation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 마이페이지에서 추가 업데이트 하는 정보
     */
    private String department;
    private String descriptionTag;
    private String description;
    private String techStack;
    private String portfolioUrls; // Github:https://github.com/Jeongh00,

    @Builder
    public Member(String username, Position position, Role role, User user, String department, String descriptionTag, String description, String techStack, String portfolioUrls) {
        this.username = username;
        this.position = position;
        this.role = role;
        this.generation = 13; // 멋사 13기
        this.user = user;
        this.department = department;
        this.descriptionTag = descriptionTag;
        this.description = description;
        this.techStack = techStack;
        this.portfolioUrls = portfolioUrls;
    }

    public void updateProfile(String username, String department, String descriptionTag,
                              String description, String techStack, String portfolioUrls,
                              String position) {
        this.username = username;
        this.department = department;
        this.descriptionTag = descriptionTag;
        this.description = description;
        this.techStack = techStack;
        this.portfolioUrls = portfolioUrls;
        this.position = Position.valueOf(position.toUpperCase());
    }
}

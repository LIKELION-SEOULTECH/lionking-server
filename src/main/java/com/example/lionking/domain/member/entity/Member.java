package com.example.lionking.domain.member.entity;

import com.example.lionking.domain.Project.entity.Project;
import com.example.lionking.domain.Project.entity.ProjectParticipation;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Enumerated(value = EnumType.STRING)
    private Position position;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY) // 이 관계에서 lazy 쓰는 이유 알아보기
    @JoinColumn(name="project_id") // 기본적으로 nullable은 true 모든 멤버가 프로젝트를 가질 필요는 없음
    private Project project;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectParticipation> reviews = new ArrayList<>();

    @Builder
    public Member(String username, Position position, Role role) {
        this.username = username;
        this.position = position;
        this.role = role;
    }

    public void set(Project project) {
        this.project = project;
    }
}

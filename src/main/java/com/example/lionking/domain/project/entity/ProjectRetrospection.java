package com.example.lionking.domain.project.entity;

import com.example.lionking.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectRetrospection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "project_id")
    private Project project;

    @Column(columnDefinition = "TEXT")
    private String content;

    public ProjectRetrospection(Member member, Project project, String content) {
        this.member = member;
        this.project = project;
        this.content = content;
    }
}

package com.example.lionking.domain.Project.entity;

import com.example.lionking.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProjectReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String review;

    private ProjectReview(Project project, Member member, String review) {
        this.project = project;
        this.member = member;
        this.review = review;
    }

    public static ProjectReview of(Project project, Member member, String review) {
        return new ProjectReview(project,member,review);
    }
}

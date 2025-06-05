package com.example.lionking.domain.project.entity;

import com.example.lionking.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProjectParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300)
    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private ProjectParticipation(Project project, Member member, String review) {
        this.project = project;
        this.member = member;
        this.review = review;
    }

    public static ProjectParticipation of(Project project, Member member, String review) {
        return new ProjectParticipation(project,member,review);
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

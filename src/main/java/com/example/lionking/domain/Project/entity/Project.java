package com.example.lionking.domain.Project.entity;

import com.example.lionking.domain.Project.dto.ProjectRequestDto;
import com.example.lionking.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Enumerated(value = EnumType.STRING)
    private ProjectType projectType;
    @Column(length = 300)
    private String projectDescription;
    private String videoLink;
    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProjectImage> projectImages = new ArrayList<>();
    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Member> members = new ArrayList<>();
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectParticipation> reviews = new ArrayList<>();


    public static Project create(ProjectRequestDto projectRequestDto, ProjectType projectType) {
        return Project.builder()
                .title(projectRequestDto.title())
                .projectType(projectType)
                .projectDescription(projectRequestDto.projectDescription())
                .videoLink(projectRequestDto.videoLink())
                .build();
    }

    @Builder
    public Project(String title, ProjectType projectType, String projectDescription, String videoLink) {
        this.title = title;
        this.projectType = projectType;
        this.projectDescription = projectDescription;
        this.videoLink = videoLink;
    }

    public void addProjectImage(ProjectImage projectImage) {
       this.projectImages.add(projectImage);
       projectImage.setProject(this);
    }
    public void addMember(Member member) {
        this.members.add(member);
        member.set(this);
    }
}

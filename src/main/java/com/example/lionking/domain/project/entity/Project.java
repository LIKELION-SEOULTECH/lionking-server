package com.example.lionking.domain.project.entity;

import com.example.lionking.domain.project.dto.request.MemberRetrospection;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private Integer generation;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProjectImage> projectImages = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectParticipation> projectParticipations = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectRetrospection> projectRetrospections = new ArrayList<>();

    @Builder
    public Project(String title, ProjectType projectType, String projectDescription, String videoLink, Integer generation) {
        this.title = title;
        this.projectType = projectType;
        this.projectDescription = projectDescription;
        this.videoLink = videoLink;
        this.generation = generation;
    }

    public void addParticipation(ProjectParticipation participation) {
        this.projectParticipations.add(participation);
    }

    public void addProjectImage(ProjectImage projectImage) {
       this.projectImages.add(projectImage);
    }

    public void addprojectRetrospection(ProjectRetrospection projectRetrospection) {
        this.projectRetrospections.add(projectRetrospection);
    }

    public String getThumbnailKey() {
        return projectImages.stream()
                .filter(image->image.getImageType().equals(ImageType.THUMBNAIL))
                .findFirst()
                .map(ProjectImage::getS3Key)
                .orElse(null);
    }

    public List<String> getLandingKeys() {
        return projectImages.stream()
                .filter(image->image.getImageType().equals(ImageType.LANDING))
                .map(ProjectImage::getS3Key)
                .collect(Collectors.toList());
    }

    public void update(String title, ProjectType projectType, String projectDescription, String videoLink, Integer generation) {
        if (title != null) this.title = title;
        if (projectType != null) this.projectType = projectType;
        if (projectDescription != null) this.projectDescription = projectDescription;
        if (videoLink != null) this.videoLink = videoLink;
        if (generation != null) this.generation = generation;
    }
}

package com.example.lionking.domain.Project.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String s3Key;
    @Enumerated(EnumType.STRING)
    private ImageType imageType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    private ProjectImage(String imageKey, ImageType imageType) {
        this.imageType = imageType;
        this.s3Key = imageKey;
    }

    public static ProjectImage of(String imageKey, ImageType imageType) {
        return new ProjectImage(imageKey, imageType);
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

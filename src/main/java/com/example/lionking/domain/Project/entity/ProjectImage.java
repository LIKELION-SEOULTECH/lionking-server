package com.example.lionking.domain.Project.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ProjectImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String s3Key;
    @Enumerated(EnumType.STRING)
    private ImageType imageType;
    @ManyToOne
    private Project project;
}

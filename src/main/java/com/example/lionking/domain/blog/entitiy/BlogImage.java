package com.example.lionking.domain.blog.entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String s3Key;

    @Enumerated(EnumType.STRING)
    private BlogImageType imageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @Builder
    public BlogImage(String s3Key, BlogImageType imageType, Blog blog) {
        this.s3Key = s3Key;
        this.imageType = imageType;
        this.blog = blog;
    }

    /**
     * Convenience Method
     */
    public void setBlog(Blog blog) {
        this.blog = blog;
        if (!blog.getBlogImages().contains(this)) {
            blog.getBlogImages().add(this);
        }
    }
    public void removeBlog() {
        this.blog = null;
    }
}

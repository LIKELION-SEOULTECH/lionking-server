package com.example.lionking.domain.blog.entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogMedia {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String s3Key;

    @Enumerated(EnumType.STRING)
    private BlogMediaType mediaType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @Builder
    public BlogMedia(String s3Key, BlogMediaType mediaType, Blog blog) {
        this.s3Key = s3Key;
        this.mediaType = mediaType;
        this.blog = blog;
    }

    /**
     * Convenience Method
     */
    public void setBlog(Blog blog) {
        this.blog = blog;
        if (!blog.getBlogMedia().contains(this)) {
            blog.getBlogMedia().add(this);
        }
    }
    public void removeBlog() {
        this.blog = null;
    }
}

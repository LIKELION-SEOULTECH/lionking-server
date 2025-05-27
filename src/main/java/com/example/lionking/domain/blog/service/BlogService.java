package com.example.lionking.domain.blog.service;

import com.example.lionking.domain.blog.dto.BlogImageRequest;
import com.example.lionking.domain.blog.dto.BlogRequest;
import com.example.lionking.domain.blog.dto.BlogResponse;
import com.example.lionking.domain.blog.entitiy.Blog;
import com.example.lionking.domain.blog.entitiy.BlogImage;
import com.example.lionking.domain.blog.repository.BlogRepository;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public BlogResponse postBlog(BlogRequest request, Long authorId) {
        Member author = memberRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        Blog blog = Blog.builder()
                .blogType(request.blogType())
                .title(request.title())
                .content(request.content())
                .author(author)
                .build();

        for (BlogImageRequest img : request.images()) {
            BlogImage blogImage = BlogImage.builder()
                    .s3Key(img.s3Key())
                    .imageType(img.imageType())
                    .blog(blog)
                    .build();

            blog.addBlogImage(blogImage);
        }
        return BlogResponse.from(blogRepository.save(blog));
    }

    public BlogResponse getBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        return BlogResponse.from(blog);
    }
}

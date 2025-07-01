package com.example.lionking.domain.blog.service;


import com.example.lionking.domain.blog.dto.BlogRequest;
import com.example.lionking.domain.blog.dto.BlogResponse;
import com.example.lionking.domain.blog.entitiy.Blog;
import com.example.lionking.domain.blog.repository.BlogRepository;
import com.example.lionking.domain.member.entity.Member;
import com.example.lionking.domain.member.repository.MemberRepository;
import com.example.lionking.global.error.GlobalErrorCode;
import com.example.lionking.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final MemberRepository memberRepository;
    private final SummaryService summaryService;

    @Transactional
    public BlogResponse postBlog(BlogRequest request, Long authorId) {
        Member author = memberRepository.findById(authorId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 유저입니다."));

        // 요약을 비동기적으로 가져오기
        String summary = null;
        try {
            summary = summaryService.getSummary(request.content()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new CustomException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "요약 처리 중 오류 발생");
        }

        Blog blog = Blog.builder()
                .blogType(request.blogType())
                .thumbnailImage(request.thumbnailImage())
                .title(request.title())
                .content(request.content())
                .summary(summary)
                .author(author)
                .build();

        blogRepository.save(blog);
        return BlogResponse.from(blog);
    }

    @Transactional(readOnly = true)
    public List<BlogResponse> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        return blogs.stream()
                .map(BlogResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public BlogResponse getBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 블로그입니다."));
        return BlogResponse.from(blog);
    }

    @Transactional(readOnly = true)
    public List<BlogResponse> getBlogsByAuthorId(Long authorId) {
        List<Blog> blogs = blogRepository.findAllByAuthorId(authorId);
        return blogs.stream()
                .map(BlogResponse::from)
                .toList();
    }

    @Transactional
    public BlogResponse updateBlog(Long blogId, BlogRequest request) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 블로그입니다."));

        // 요약을 비동기적으로 가져오기
        String summary = null;
        try {
            summary = summaryService.getSummary(request.content()).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new CustomException(GlobalErrorCode.INTERNAL_SERVER_ERROR, "요약 처리 중 오류 발생");
        }
        blog.update(request.thumbnailImage(), request.title(), request.content(), summary);
        return BlogResponse.from(blog);
    }

    @Transactional
    public void deleteBlog(Long blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new CustomException(GlobalErrorCode.NOT_FOUND, "존재하지 않는 블로그입니다."));
        blogRepository.delete(blog);
    }
}
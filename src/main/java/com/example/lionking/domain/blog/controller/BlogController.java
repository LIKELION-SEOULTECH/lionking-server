package com.example.lionking.domain.blog.controller;

import com.example.lionking.domain.blog.dto.BlogRequest;
import com.example.lionking.domain.blog.dto.BlogResponse;
import com.example.lionking.domain.blog.service.BlogService;
import com.example.lionking.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blog")
@Tag(name = "블로그 API", description = "블로그 API : 세션 및 아티클")
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/{authorId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "블로그 작성", description = "새로운 블로그 게시글 작성")
    public ApiResponse<BlogResponse> postBlog(
            @RequestBody BlogRequest request,
            @PathVariable Long authorId
    ) {
        BlogResponse response = blogService.postBlog(request, authorId);
        return ApiResponse.success(response, "블로그 게시글 작성 성공");
    }

    @GetMapping("/{blogId}")
    @Operation(summary = "블로그 조회", description = "특정 블로그 ID로 조회하기")
    public ApiResponse<BlogResponse> getByBlogID(@PathVariable Long blogId) {
        BlogResponse response = blogService.getBlog(blogId);
        return ApiResponse.success(response, "블로그 조회 성공");
    }
}

package com.example.lionking.domain.blog.repository;

import com.example.lionking.domain.blog.entitiy.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByAuthorId(Long authorId);
}

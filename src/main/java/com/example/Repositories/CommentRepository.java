package com.example.Repositories;

import com.example.Entites.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface CommentRepository extends JpaRepository<Comment,Long> {
}

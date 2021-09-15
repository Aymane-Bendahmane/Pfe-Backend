package com.example.Repositories;

import com.example.Entites.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface ArticleRepository extends JpaRepository<Article,Long> {
}

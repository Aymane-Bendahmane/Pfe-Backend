package com.example.Repositories;

import com.example.Entites.Article;
import com.example.Entites.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RestResource
public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findArticleByCategory(Category category);
}

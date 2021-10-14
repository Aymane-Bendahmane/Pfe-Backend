package com.example.Repositories;

import com.example.Entites.Article;
import com.example.Entites.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestResource @CrossOrigin(origins = "*")

public interface RatingRepository extends JpaRepository<Rating,Long> {

    Integer countRatingByArticle(Article a);

    List<Rating> findAllByArticle(Article article);

}

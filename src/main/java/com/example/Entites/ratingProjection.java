package com.example.Entites;

import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name="p3",types = {Rating.class})
public interface ratingProjection {
    Long getId();
    Integer getStare();
    Userr getUserr();
    Article getArticle();
    String getComment();
    Date getDate();
}

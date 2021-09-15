package com.example.Entites;


import org.springframework.data.rest.core.config.Projection;

@Projection(name="p2",types = {Article.class})
public interface articleProjection {
    public Long getId();
    public String getArtdesignation();
    public float getPrix();
    public   int getQtstock();
    public  String getDescription();
    public String getPhoto();
    Marque getMarque();
    Category getCategory();
}

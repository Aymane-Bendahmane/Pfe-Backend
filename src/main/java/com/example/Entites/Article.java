package com.example.Entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String artdesignation ;
    private float prix ;
    private int qtstock ;
    private String description ;
    private String photo ;

    @ManyToOne
    Marque marque ;

    @ManyToOne
    Category category ;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany
    Collection <Commande> commandes ;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "article")
    Collection<Rating> ratings;



}
package com.example.Entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Article implements Serializable {
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
    @OneToMany(mappedBy = "article")
    Collection<Rating> ratings;



}
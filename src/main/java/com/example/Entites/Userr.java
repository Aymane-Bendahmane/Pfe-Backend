package com.example.Entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Userr implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idU;
    private String email;
    private String userLogin ;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPassword;
    private String Sexe;
    private String nom;
    private String prenom;
    private String phone;
    private String userAddress ;

    @ManyToMany(fetch = FetchType.EAGER)
    Collection<Role> roles;



    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "userr")
    Collection<Rating> ratings;


}

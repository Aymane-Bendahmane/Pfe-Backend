package com.example.Entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Userr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idU;
    private String email;
    private String userLogin ;
    private String userPassword;
    private String Sexe;
    private String nom;
    private String prenom;
    private String phone;
    private String userAddress ;

    @OneToMany
    Collection<Role> roles;

    @OneToMany(mappedBy = "userr")
    Collection<Commande> commandes;

    @OneToMany(mappedBy = "userr")
    Collection<Rating> ratings;

}

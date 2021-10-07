package com.example.Repositories;

import com.example.Entites.Userr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")

@RestResource
public interface UserRepository extends JpaRepository<Userr,Long> {
    Userr findUserrByNom(@Param("nom") String nom);
}

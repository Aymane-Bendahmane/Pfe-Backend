package com.example.Repositories;

import com.example.Entites.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestResource
public interface CommandeRepository extends JpaRepository<Commande,Long> {
      
}

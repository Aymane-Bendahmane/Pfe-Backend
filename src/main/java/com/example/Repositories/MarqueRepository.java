package com.example.Repositories;

import com.example.Entites.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface MarqueRepository extends JpaRepository<Marque,Long> {
}

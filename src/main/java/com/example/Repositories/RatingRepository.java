package com.example.Repositories;

import com.example.Entites.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestResource @CrossOrigin(origins = "http://localhost:4200")

public interface RatingRepository extends JpaRepository<Rating,Long> {
}

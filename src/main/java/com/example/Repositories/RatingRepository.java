package com.example.Repositories;

import com.example.Entites.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface RatingRepository extends JpaRepository<Rating,Long> {
}

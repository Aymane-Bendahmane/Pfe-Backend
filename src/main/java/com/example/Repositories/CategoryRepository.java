package com.example.Repositories;

import com.example.Entites.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface CategoryRepository extends JpaRepository<Category,Long> {
}

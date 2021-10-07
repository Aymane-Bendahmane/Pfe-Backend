package com.example.Repositories;

import com.example.Entites.Article;
import com.example.Entites.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestResource
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findCategoryByCatNom(String s);
}

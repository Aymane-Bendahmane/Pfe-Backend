package com.example.Repositories;

import com.example.Entites.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestResource @CrossOrigin("*")
public interface ProductItemRepository extends JpaRepository<ProductItem,Long> {
}

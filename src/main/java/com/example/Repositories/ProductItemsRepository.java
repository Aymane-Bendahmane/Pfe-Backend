package com.example.Repositories;

import com.example.Entites.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestResource
@CrossOrigin(origins = "*")
public interface ProductItemsRepository extends JpaRepository<ProductItem,Long> {
}

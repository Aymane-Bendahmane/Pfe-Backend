package com.example.Repositories;

import com.example.Entites.Userr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface UserRepository extends JpaRepository<Userr,Long> {
}

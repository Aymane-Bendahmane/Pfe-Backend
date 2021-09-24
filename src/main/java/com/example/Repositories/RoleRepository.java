package com.example.Repositories;

import com.example.Entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")

@RestResource
public interface RoleRepository extends JpaRepository<Role,Long> {
}

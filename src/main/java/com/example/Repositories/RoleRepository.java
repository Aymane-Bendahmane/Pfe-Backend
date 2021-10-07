package com.example.Repositories;

import com.example.Entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*")

@RestResource
public interface RoleRepository extends JpaRepository<Role,Long> {

    List<Role> findRolesByRole(@Param("role")String role);
}

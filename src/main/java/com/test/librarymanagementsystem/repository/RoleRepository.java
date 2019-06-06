package com.test.librarymanagementsystem.repository;

import com.test.librarymanagementsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}

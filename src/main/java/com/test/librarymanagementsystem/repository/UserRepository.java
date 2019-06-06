package com.test.librarymanagementsystem.repository;

import com.test.librarymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}

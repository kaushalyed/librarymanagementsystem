package com.test.librarymanagementsystem.repository;

import com.test.librarymanagementsystem.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}

package com.test.librarymanagementsystem.repository;

import com.test.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book> findBooksByAuthorName(String name);
    @Query("SELECT name FROM Book ")
    List<String> findBookNames();

}

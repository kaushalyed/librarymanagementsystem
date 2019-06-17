package com.test.librarymanagementsystem.service;

import com.test.librarymanagementsystem.dto.response.BookDTO;
import com.test.librarymanagementsystem.model.Book;

import java.util.List;

public interface BookService {

    void addBook(Book book);
    void updateBook(Book book, Long id);
    List<BookDTO> getBooks();
    List<String> getBookNames();
    BookDTO getBook(Long id);
    Book getBookById(Long id);
    List<BookDTO> getBooksByAuthorName(String name);
    long getTotalBookCount();
}

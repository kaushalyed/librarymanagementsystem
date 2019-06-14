package com.test.librarymanagementsystem.service;

import com.test.librarymanagementsystem.model.Book;

import java.util.List;

public interface BookService {

    void addBook(Book book);
    void updateBook(Book book, Long id);
    List<Book> getBooks();
    Book getBook(Long id);
    List<Book> getBooksByAuthorName(String name);
}

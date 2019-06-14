package com.test.librarymanagementsystem.service.impl;

import com.test.librarymanagementsystem.exception.BookNotAvailableException;
import com.test.librarymanagementsystem.model.Book;
import com.test.librarymanagementsystem.repository.BookRepository;
import com.test.librarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED )
    public void updateBook(Book book, Long id) {
        Optional optional = bookRepository.findById(id);
        if(optional.isPresent()){
            book.setId(id);
            bookRepository.save(book);
        }else{
            throw new BookNotAvailableException("Book doesn't exist with given id");
        }
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBook(Long id) {
        Optional optional = bookRepository.findById(id);
        if(optional.isPresent()){
            return (Book)optional.get();
        }else{
            throw new BookNotAvailableException("Book doesn't exist with given id");
        }
    }

    @Override
    public List<Book> getBooksByAuthorName(String name) {
        //Optional optional = bookRepository.
        return null;
    }
}

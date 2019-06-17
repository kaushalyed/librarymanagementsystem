package com.test.librarymanagementsystem.service.impl;

import com.test.librarymanagementsystem.dto.response.BookDTO;
import com.test.librarymanagementsystem.exception.BookNotAvailableException;
import com.test.librarymanagementsystem.model.Book;
import com.test.librarymanagementsystem.repository.BookRepository;
import com.test.librarymanagementsystem.service.BookService;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    /*
    -----Propagation
    Propagation.REQUIRED --> Support a current transaction, create a new one if none exists

    Propagation.REQUIRED_NEW --> it will create a new transaction

    ----Transaction Isolation levels
    READ_COMMITTED: A constant indicating that dirty reads are prevented; non-repeatable reads and phantom reads can occur.

    READ_UNCOMMITTED: This isolation level states that a transaction may read data that is still uncommitted by other transactions.

    REPEATABLE_READ: If a row is read twice in the same transaciton, result will always be the same

    SERIALIZABLE: Performs all transactions in a sequence

     */


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
    public List<BookDTO> getBooks() {
        List<Book> books = bookRepository.findAll();
        if(books!=null && books.size()>0){
            return books.stream().map(x->modelMapper.map(x,BookDTO.class)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public BookDTO getBook(Long id) {
        Optional optional = bookRepository.findById(id);
        if(optional.isPresent()){
            return modelMapper.map(optional.get(),BookDTO.class);
        }else{
            throw new BookNotAvailableException("Book doesn't exist with given id");
        }
    }
    @Override
    public Book getBookById(Long id) {
        Optional optional = bookRepository.findById(id);
        if(optional.isPresent()){
            return (Book)optional.get();
        }else{
            throw new BookNotAvailableException("Book doesn't exist with given id");
        }
    }

    @Override
    public List<BookDTO> getBooksByAuthorName(String name) {
        return null;
    }
    @Override
    public List<String> getBookNames(){
        return bookRepository.findBookNames();
    }
    @Override
    public long getTotalBookCount(){
        Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Book.class);
        criteria.setProjection(Projections.rowCount());
        Long count=(Long)criteria.uniqueResult();
        if(count==null){
            return 0;
        }
        return count;
    }
}

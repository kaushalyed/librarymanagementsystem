package com.test.librarymanagementsystem.resources;

import com.test.librarymanagementsystem.model.Book;
import com.test.librarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BookResource {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/books", method= RequestMethod.POST)
    public ResponseEntity addBook(@RequestBody Book book){
        Map<String,Object> responseMap = new HashMap<>();
        try{
            bookService.addBook(book);
            responseMap.put("success","Book added successfully");
        }catch (Exception e){
            responseMap.put("errorMsg",e.getMessage());
        }
        return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/books/{id}", method= RequestMethod.PUT)
    public ResponseEntity updateBook(@RequestBody Book book, @PathVariable(value = "id") Long id){
        Map<String,Object> responseMap = new HashMap<>();
        try{
            bookService.updateBook(book,id);
            responseMap.put("success","Book details updated successfully");
        }catch (Exception e){
            responseMap.put("errorMsg",e.getMessage());
        }
        return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping("/books")
    public ResponseEntity getBooks(){
        Map<String,Object> responseMap = new HashMap<>();
        try{
            responseMap.put("books",bookService.getBooks());
        }catch (Exception e){
            responseMap.put("errorMsg",e.getMessage());
        }
        return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @RequestMapping("/books/{id}")
    public ResponseEntity getBook(@PathVariable Long id){
        Map<String,Object> responseMap = new HashMap<>();
        try{
            responseMap.put("books",bookService.getBook(id));
        }catch (Exception e){
            responseMap.put("errorMsg",e.getMessage());
        }
        return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @RequestMapping("/books/name")
    public ResponseEntity getBook(){
        Map<String,Object> responseMap = new HashMap<>();
        try{
            responseMap.put("bookNames",bookService.getBookNames());
        }catch (Exception e){
            responseMap.put("errorMsg",e.getMessage());
        }
        return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @RequestMapping("/books/count")
    public ResponseEntity getBookCount(){
        Map<String,Object> responseMap = new HashMap<>();
        try{
            responseMap.put("bookCount",bookService.getTotalBookCount());
        }catch (Exception e){
            responseMap.put("errorMsg",e.getMessage());
        }
        return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

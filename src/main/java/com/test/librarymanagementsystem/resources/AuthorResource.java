package com.test.librarymanagementsystem.resources;

import com.test.librarymanagementsystem.model.Author;
import com.test.librarymanagementsystem.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthorResource {

    @Autowired
    private AuthorService authorService;

    @RequestMapping(value = "authors",method = RequestMethod.POST)
    public ResponseEntity addAuthor(@RequestBody Author author){
        Map<String,Object> responseMap = new HashMap<>();
        try{
            authorService.addAuthor(author);
            responseMap.put("success","Author added successfully");
        }catch (Exception e){
            responseMap.put("errorMsg",e.getMessage());
        }
        return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @RequestMapping(value = "/authors/{id}",method = RequestMethod.PUT)
    public ResponseEntity updateAuthor(@RequestBody Author author,@PathVariable(value = "id") Long id){
        Map<String,Object> responseMap = new HashMap<>();
        try{
            authorService.updateAuthor(author,id);
            responseMap.put("success","Author details updated successfully");
        }catch (Exception e){
            responseMap.put("errorMsg",e.getMessage());
        }
        return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @RequestMapping("authors")
    public ResponseEntity getAuthors(){
        Map<String,Object> responseMap = new HashMap<>();
        try{
            responseMap.put("authors",authorService.getAuthors());
        }catch (Exception e){
            responseMap.put("errorMsg",e.getMessage());
        }
        return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @RequestMapping("authors/{id}")
    public ResponseEntity getAuthor(@PathVariable Long id){
        Map<String,Object> responseMap = new HashMap<>();
        try{
            responseMap.put("author",authorService.getAuthor(id));
        }catch (Exception e){
            responseMap.put("errorMsg",e.getMessage());
        }
        return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

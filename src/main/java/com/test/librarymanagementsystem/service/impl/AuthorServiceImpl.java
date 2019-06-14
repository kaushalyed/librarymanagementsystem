package com.test.librarymanagementsystem.service.impl;

import com.test.librarymanagementsystem.exception.AuthorNotExistExcpetion;
import com.test.librarymanagementsystem.model.Author;
import com.test.librarymanagementsystem.repository.AuthorRepository;
import com.test.librarymanagementsystem.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public void addAuthor(Author author){
        authorRepository.save(author);
    }
    @Override
    public void updateAuthor(Author author,Long id){
        Optional optional = authorRepository.findById(id);
        if(optional.isPresent()){
        	author.setId(id);
          authorRepository.save(author);
        }else{
            throw new AuthorNotExistExcpetion("Author not exist with given id");
        }
    }

    @Override
    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }
    @Override
    public Author getAuthor(Long id){
         Optional optional = authorRepository.findById(id);
         if(optional.isPresent()){
             return (Author)optional.get();
         }else{
             throw new AuthorNotExistExcpetion("Author not exist with given id");
         }
    }

}

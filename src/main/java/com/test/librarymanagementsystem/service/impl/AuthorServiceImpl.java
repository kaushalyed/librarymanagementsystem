package com.test.librarymanagementsystem.service.impl;

import com.test.librarymanagementsystem.dto.response.AuthorDTO;
import com.test.librarymanagementsystem.exception.AuthorNotExistExcpetion;
import com.test.librarymanagementsystem.model.Author;
import com.test.librarymanagementsystem.repository.AuthorRepository;
import com.test.librarymanagementsystem.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;

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
    public List<AuthorDTO> getAuthors(){
        List<Author> authors = authorRepository.findAll();
        if(authors!=null && authors.size()>0){
           return authors.stream().map(x->modelMapper.map(x,AuthorDTO.class)).collect(Collectors.toList());
        }
        return null;
    }
    @Override
    public AuthorDTO getAuthor(Long id){
         Optional optional = authorRepository.findById(id);
         if(optional.isPresent()){
             return modelMapper.map(optional.get(),AuthorDTO.class);
         }else{
             throw new AuthorNotExistExcpetion("Author not exist with given id");
         }
    }

}

package com.test.librarymanagementsystem.service;

import com.test.librarymanagementsystem.dto.response.AuthorDTO;
import com.test.librarymanagementsystem.model.Author;

import java.util.List;

public interface AuthorService {

    void addAuthor(Author author);
    void updateAuthor(Author author,Long id);
    List<AuthorDTO> getAuthors();
    AuthorDTO getAuthor(Long id);

}

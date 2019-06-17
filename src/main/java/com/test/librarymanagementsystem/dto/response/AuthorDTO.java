package com.test.librarymanagementsystem.dto.response;

import com.test.librarymanagementsystem.model.Book;

import java.util.List;

public class AuthorDTO {

    private Long id;
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

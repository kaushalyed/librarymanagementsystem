package com.test.librarymanagementsystem.exception;

public class BookNotAvailableException extends RuntimeException {

    public BookNotAvailableException(String msg){
        super(msg);
    }
}

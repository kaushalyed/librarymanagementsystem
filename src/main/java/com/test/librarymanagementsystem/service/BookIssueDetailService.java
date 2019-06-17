package com.test.librarymanagementsystem.service;


import com.test.librarymanagementsystem.dto.request.SearchBookIssueRequest;
import com.test.librarymanagementsystem.dto.response.BookIssueDTO;
import com.test.librarymanagementsystem.dto.response.BookIssueResponse;
import com.test.librarymanagementsystem.model.BookIssueDetail;

import java.util.List;

public interface BookIssueDetailService {

    void issueBook(BookIssueDetail bookIssueDetail);
    void submitBook(Long id);
    List<BookIssueDTO> getIssuedBookByUserId(Long id);
    List<BookIssueDTO> getIssuedBook();
    BookIssueResponse searchIssuedBook(SearchBookIssueRequest searchBookIssueRequest);
    List<BookIssueDTO> getIssuedBookAsynchronousUseCase();

}

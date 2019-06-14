package com.test.librarymanagementsystem.dto.response;

import java.util.List;

public class BookIssueResponse{
    private List<BookIssueDTO> bookIssues;
    private Long totalCount=0l;

    public List<BookIssueDTO> getBookIssues() {
        return bookIssues;
    }

    public void setBookIssues(List<BookIssueDTO> bookIssues) {
        this.bookIssues = bookIssues;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }


}

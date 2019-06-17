package com.test.librarymanagementsystem.dto.response;

import java.util.List;

public class BookIssueResponse{
    private List<BookIssueDTO> bookIssueDTOs;
    private Long totalCount=0l;

    public List<BookIssueDTO> getBookIssueDTOs() {
        return bookIssueDTOs;
    }

    public void setBookIssueDTOs(List<BookIssueDTO> bookIssueDTOs) {
        this.bookIssueDTOs = bookIssueDTOs;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }


}

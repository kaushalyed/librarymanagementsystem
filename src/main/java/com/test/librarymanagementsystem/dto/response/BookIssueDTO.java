package com.test.librarymanagementsystem.dto.response;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class BookIssueDTO {

    private Long bookIssueId;
    private String bookName;
    private String authorName;
    private String userFirstName;
    private String userLastName;

    public BookIssueDTO() {
    }
    public BookIssueDTO(String bookName) {
        this.bookName = bookName;
    }

    public BookIssueDTO(Long bookIssueId,Date expectedReturnDate,Date issueDate,String bookName, String authorName, String userFirstName, String userLastName) {
        this.bookIssueId = bookIssueId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.issueDate = issueDate;
        this.expectedReturnDate = expectedReturnDate;
    }

    @Temporal(TemporalType.DATE)
    private Date issueDate;
    @Temporal(TemporalType.DATE)
    private Date expectedReturnDate;

    public Long getBookIssueId() {
        return bookIssueId;
    }

    public void setBookIssueId(Long bookIssueId) {
        this.bookIssueId = bookIssueId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(Date expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }
}

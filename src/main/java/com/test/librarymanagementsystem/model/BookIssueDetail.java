package com.test.librarymanagementsystem.model;

import javax.persistence.*;

import com.test.librarymanagementsystem.dto.response.BookIssueDTO;

import java.util.Date;

import static javax.persistence.TemporalType.DATE;

@Entity
@Table(name="book_issue_details")
@SqlResultSetMapping(
		name="BookIssueSearchMapping",
				classes={
	                    @ConstructorResult(
	                        targetClass=BookIssueDTO.class,
	                           columns = {
	                                   @ColumnResult(name="bookIssueId",type=Long.class),
	                                   @ColumnResult(name="expectedReturnDate"),
                                       @ColumnResult(name="issueDate"),
                                       @ColumnResult(name="bookName"),
                                       @ColumnResult(name="authorName"),
                                       @ColumnResult(name="userFirstName"),
                                       @ColumnResult(name="userLastName")
	                           }

	                    )
	                }
		)
public class BookIssueDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(DATE)
    @Column(name="issue_date")
    private Date issueDate;

    @Temporal(DATE)
    @Column(name="expected_submission_date")
    private Date expectedSubmissionDate;

    @Temporal(DATE)
    @Column(name="submission_date")
    private Date submissionDate;

    @Column
    private boolean submitted;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpectedSubmissionDate() {
        return expectedSubmissionDate;
    }

    public void setExpectedSubmissionDate(Date expectedSubmissionDate) {
        this.expectedSubmissionDate = expectedSubmissionDate;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

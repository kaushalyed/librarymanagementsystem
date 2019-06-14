package com.test.librarymanagementsystem.repository;

import com.test.librarymanagementsystem.model.Book;
import com.test.librarymanagementsystem.model.BookIssueDetail;
import com.test.librarymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookIssueDetailRepository extends JpaRepository<BookIssueDetail,Long> {

	  @Query(value=" from BookIssueDetail bk where submitted=false",nativeQuery = false)
	  List<BookIssueDetail> getIssuedBooks();

	  List<BookIssueDetail> findByUserAndSubmitted(User user,boolean submitted);

}

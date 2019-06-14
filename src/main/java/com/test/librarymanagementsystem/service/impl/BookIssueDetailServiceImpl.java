package com.test.librarymanagementsystem.service.impl;

import com.test.librarymanagementsystem.dto.request.SearchBookIssueRequest;
import com.test.librarymanagementsystem.dto.response.BookIssueDTO;
import com.test.librarymanagementsystem.dto.response.BookIssueResponse;
import com.test.librarymanagementsystem.exception.BookNotAvailableException;
import com.test.librarymanagementsystem.exception.SearchFilterException;
import com.test.librarymanagementsystem.model.Book;
import com.test.librarymanagementsystem.model.BookIssueDetail;
import com.test.librarymanagementsystem.model.User;
import com.test.librarymanagementsystem.repository.BookIssueDetailRepository;
import com.test.librarymanagementsystem.service.BookIssueDetailService;
import com.test.librarymanagementsystem.service.BookService;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookIssueDetailServiceImpl implements BookIssueDetailService {

    @Autowired
    private BookIssueDetailRepository bookIssueDetailRepository;

    @Autowired
    private BookService bookService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public void issueBook(BookIssueDetail bookIssueDetail) {
        Book book = bookService.getBook(bookIssueDetail.getBook().getId());
        if(book != null){
            if(book.getAvailableQuantity()<=0){
                throw new BookNotAvailableException("Required book Not available");
            }
        }
        bookIssueDetailRepository.save(bookIssueDetail);
        try{
         book.setAvailableQuantity(book.getAvailableQuantity()-1);
         bookService.updateBook(book,book.getId());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void submitBook(Long id) {
        Optional optional = bookIssueDetailRepository.findById(id);
        BookIssueDetail bookIssueDetail = null;
        if(optional.isPresent()){
            bookIssueDetail = (BookIssueDetail) optional.get();
            bookIssueDetail.setSubmitted(true);
            bookIssueDetail.setSubmissionDate(new Date());
            bookIssueDetailRepository.save(bookIssueDetail);
        }else{
            throw new BookNotAvailableException("Book not exist");
        }
        try{
            Book book = bookService.getBook(bookIssueDetail.getBook().getId());
            book.setAvailableQuantity(book.getAvailableQuantity()+1);
            bookService.updateBook(book,book.getId());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<BookIssueDetail> getIssuedBookByUserId(Long id) {
        User user = new User();
        user.setId(id);
        return bookIssueDetailRepository.findByUserAndSubmitted(user,false);
    }
    @Override
    public List<BookIssueDetail> getIssuedBook() {
        return bookIssueDetailRepository.getIssuedBooks();
        //return null;
    }

    @Override
    public List<BookIssueDetail> searchIssuedBook(SearchBookIssueRequest searchBookIssueRequest) {

        if(searchBookIssueRequest.getSearchFieldMap()==null && searchBookIssueRequest.getSearchFieldMap().isEmpty()){
            throw new SearchFilterException("Filter is empty");
        }

        Integer pageNo = searchBookIssueRequest.getPageNo();
        Integer pageSize = searchBookIssueRequest.getPageSize();
        List<String> sortFields = searchBookIssueRequest.getSortFields();
        StringBuilder searchQuery = new StringBuilder("select a.id ,a.book_id,a.expected_submission_date,a.issue_date,a.submission_date,a.submitted,a.user_id from book_issue_details a, book b, author c, user d")
        //StringBuilder searchQuery = new StringBuilder("select a.id as bookIssueId,a.expected_submission_date as expectedReturnDate,a.issue_date as issueDate,b.name as bookName,c.name as authorName,d.first_name as userFirstName,d.last_name as userLastName from book_issue_details a, book b, author c, user d")
        //StringBuilder searchQuery = new StringBuilder("select b.name as bookName from book_issue_details a, book b, author c, user d ")
                                    .append(" where a.book_id=b.id and ")
                                    .append(" a.user_id=d.id and ")
                                    .append(" b.author_id=c.id and ")
                                    .append(" a.submitted=0" );

        StringBuilder countQuery = new StringBuilder("Select count(*) from from book_issue_details a, book b, author c, user d")
                                         .append(" where a.book_id=b.id and ")
                                         .append(" a.user_id=d.id and ")
                                         .append(" b.author_id=c.id and ")
                                         .append(" a.submitted=0" );


        StringBuilder whereClause = new StringBuilder();
        searchBookIssueRequest.getSearchFieldMap().forEach((key,value)->{
            if("bookName".equals(key)){
                whereClause.append(" and b.name like '%"+value+"%'");
            }else if("authorName".equals(key)){
                whereClause.append(" and c.name like '%"+value+"%'");
            }else if("userFirstName".equals(key)){
                whereClause.append(" and d.first_name like '%"+value+"%'");
            }else if("userLastName".equals(key)){
                whereClause.append(" and d.last_name like '%"+value+"%'");
            }
        });
        searchQuery.append(whereClause);
        countQuery.append(whereClause);

        if(sortFields!=null && !sortFields.isEmpty()){
            searchQuery.append(" order by ");

            sortFields.stream().forEach(s->{
                if("bookName".equals(s)){
                    searchQuery.append(" b.name,");
                }else if("authorName".equals(s)){
                    searchQuery.append(" c.name,");
                }else if("userFirstName".equals(s)){
                    searchQuery.append(" d.first_name,");
                }else if("userLastName".equals(s)){
                    searchQuery.append(" d.last_name,");
                }
            });
            searchQuery.deleteCharAt(searchQuery.length()-1);
            if(searchBookIssueRequest.getSortOrder()!=null){
                searchQuery.append("  "+searchBookIssueRequest.getSortOrder());
            }
        }

        //Query query = entityManager.createNativeQuery (searchQuery.toString()).unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.aliasToBean(BookIssueDTO.class));
        Query query = entityManager.createNativeQuery (searchQuery.toString(),BookIssueDTO.class);
        Query totalCountQuery = entityManager.createNativeQuery (countQuery.toString());

        query.setFirstResult((pageNo-1)*pageSize);
        query.setMaxResults(pageSize);
        List<BookIssueDTO> bookIssueDetails = query.getResultList();
        Long totalResultCount= ((BigInteger)totalCountQuery.getSingleResult()).longValue();

        System.out.println("Book issue details: "+bookIssueDetails.size());
        System.out.println("Count: "+totalResultCount);

        //List<Long> ids = bookIssueDetails.stream().map(a->a.getId()).collect(Collectors.toList());
        //ids.stream().forEach(id-> System.out.println(id));

        //return bookIssueDetailRepository.findAllById(ids);
        return null;
    }
}

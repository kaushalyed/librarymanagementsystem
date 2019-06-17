package com.test.librarymanagementsystem.service.impl;

import com.test.librarymanagementsystem.dto.request.SearchBookIssueRequest;
import com.test.librarymanagementsystem.dto.response.BookIssueDTO;
import com.test.librarymanagementsystem.dto.response.BookIssueResponse;
import com.test.librarymanagementsystem.exception.BookNotAvailableException;
import com.test.librarymanagementsystem.model.Book;
import com.test.librarymanagementsystem.model.BookIssueDetail;
import com.test.librarymanagementsystem.model.User;
import com.test.librarymanagementsystem.repository.BookIssueDetailRepository;
import com.test.librarymanagementsystem.service.BookIssueDetailService;
import com.test.librarymanagementsystem.service.BookService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public void issueBook(BookIssueDetail bookIssueDetail) {
        Book book = bookService.getBookById(bookIssueDetail.getBook().getId());
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
            Book book = bookService.getBookById(bookIssueDetail.getBook().getId());
            book.setAvailableQuantity(book.getAvailableQuantity()+1);
            bookService.updateBook(book,book.getId());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<BookIssueDTO> getIssuedBookByUserId(Long id) {
        User user = new User();
        user.setId(id);
        List<BookIssueDetail> bookIssueDetails = bookIssueDetailRepository.findByUserAndSubmitted(user,false);
        modelMapper.addMappings(new PropertyMap<BookIssueDetail, BookIssueDTO>() {
            protected void configure() {
                map().setAuthorName(source.getBook().getAuthor().getName());
            }
        });
        List<BookIssueDTO> bookIssueDTOS = bookIssueDetails.stream().map(x->modelMapper.map(x,BookIssueDTO.class)).collect(Collectors.toList());
        return bookIssueDTOS;
    }
    @Override
    public List<BookIssueDTO> getIssuedBook() {
        List<BookIssueDetail> bookIssueDetails = bookIssueDetailRepository.getIssuedBooks();
        //explicit mapping
        modelMapper.addMappings(new PropertyMap<BookIssueDetail, BookIssueDTO>() {
            protected void configure() {
                map().setAuthorName(source.getBook().getAuthor().getName());
            }
        });
       List<BookIssueDTO> bookIssueDTOS = bookIssueDetails.stream().map(x->modelMapper.map(x,BookIssueDTO.class)).collect(Collectors.toList());
        return bookIssueDTOS;
    }

    @Override
    public BookIssueResponse searchIssuedBook(SearchBookIssueRequest searchBookIssueRequest) {

       /* if(searchBookIssueRequest.getSearchFieldMap()==null && searchBookIssueRequest.getSearchFieldMap().isEmpty()){
            throw new SearchFilterException("Filter is empty");
        }*/

        Integer pageNo = searchBookIssueRequest.getPageNo();
        Integer pageSize = searchBookIssueRequest.getPageSize();
        List<String> sortFields = searchBookIssueRequest.getSortFields();
        //StringBuilder searchQuery = new StringBuilder("select a.id ,a.book_id,a.expected_submission_date,a.issue_date,a.submission_date,a.submitted,a.user_id from book_issue_details a, book b, author c, user d")
        StringBuilder searchQuery = new StringBuilder("select a.id as bookIssueId,a.expected_submission_date as expectedReturnDate,a.issue_date as issueDate,b.name as bookName,c.name as authorName,d.first_name as userFirstName,d.last_name as userLastName from book_issue_details a, book b, author c, user d")
                                    .append(" where a.book_id=b.id and ")
                                    .append(" a.user_id=d.id and ")
                                    .append(" b.author_id=c.id and ")
                                    .append(" a.submitted=0" );

        StringBuilder countQuery = new StringBuilder("Select count(*) from book_issue_details a, book b, author c, user d")
                                         .append(" where a.book_id=b.id and ")
                                         .append(" a.user_id=d.id and ")
                                         .append(" b.author_id=c.id and ")
                                         .append(" a.submitted=0" );

        //check if no fields available in search then fetch all
        if(searchBookIssueRequest.getSearchFieldMap()!=null && !searchBookIssueRequest.getSearchFieldMap().isEmpty()) {
            StringBuilder whereClause = new StringBuilder();
            searchBookIssueRequest.getSearchFieldMap().forEach((key, value) -> {
                if ("bookName".equals(key)) {
                    whereClause.append(" and b.name like '%" + value + "%'");
                } else if ("authorName".equals(key)) {
                    whereClause.append(" and c.name like '%" + value + "%'");
                } else if ("userFirstName".equals(key)) {
                    whereClause.append(" and d.first_name like '%" + value + "%'");
                } else if ("userLastName".equals(key)) {
                    whereClause.append(" and d.last_name like '%" + value + "%'");
                }
            });
            searchQuery.append(whereClause);
            countQuery.append(whereClause);

            if (sortFields != null && !sortFields.isEmpty()) {
                searchQuery.append(" order by ");

                sortFields.stream().forEach(s -> {
                    if ("bookName".equals(s)) {
                        searchQuery.append(" b.name,");
                    } else if ("authorName".equals(s)) {
                        searchQuery.append(" c.name,");
                    } else if ("userFirstName".equals(s)) {
                        searchQuery.append(" d.first_name,");
                    } else if ("userLastName".equals(s)) {
                        searchQuery.append(" d.last_name,");
                    }
                });
                searchQuery.deleteCharAt(searchQuery.length() - 1);
                if (searchBookIssueRequest.getSortOrder() != null) {
                    searchQuery.append("  " + searchBookIssueRequest.getSortOrder());
                }
            }
        }
        Query query = entityManager.createNativeQuery (searchQuery.toString(),"BookIssueSearchMapping");

      
        //Query query = entityManager.createNativeQuery (searchQuery.toString(),BookIssueDTO.class);
        Query totalCountQuery;
        totalCountQuery = entityManager.createNativeQuery (countQuery.toString());

        query.setFirstResult((pageNo-1)*pageSize);
        query.setMaxResults(pageSize);

        //get the result with pagination
        List<BookIssueDTO> bookIssueDTOS = query.getResultList();
        //get the total result count
        Long totalResultCount= ((BigInteger)totalCountQuery.getSingleResult()).longValue();

        BookIssueResponse bookIssueResponse = new BookIssueResponse();
        bookIssueResponse.setBookIssueDTOs(bookIssueDTOS);
        bookIssueResponse.setTotalCount(totalResultCount);

        return bookIssueResponse;
    }
    @Override
    public List<BookIssueDTO> getIssuedBookAsynchronousUseCase(){

        final Integer pageSize = 2;
        Integer pageNo=1;

        SearchBookIssueRequest searchBookIssueRequest = new SearchBookIssueRequest();
        searchBookIssueRequest.setPageNo(pageNo);
        searchBookIssueRequest.setPageSize(pageSize);

        BookIssueResponse bookIssueResponses = searchIssuedBook(searchBookIssueRequest);
        List<BookIssueDTO> bookIssueDTOS = bookIssueResponses.getBookIssueDTOs();

        Long totalCount = bookIssueResponses.getTotalCount()-pageSize;
        Integer iteration = (int) Math.ceil(totalCount*1.0/pageSize);

        List<Integer> iterationCount = new ArrayList<Integer>();
        //i initialize with 2 to use as page number as well
        //started with 2 that's why added iteration+2
        for(int i=2;i<iteration+2;i++) {
        	iterationCount.add(i);
        }
        ArrayList<Thread> arrThreads = new ArrayList<Thread>();
        iterationCount.forEach(x->{
                Thread t = new Thread(() -> {
                    SearchBookIssueRequest bookIssueRequest = new SearchBookIssueRequest();
                    bookIssueRequest.setPageNo(x);
                    bookIssueRequest.setPageSize(pageSize);
                    bookIssueDTOS.addAll(searchIssuedBook(searchBookIssueRequest).getBookIssueDTOs());
                });
                t.start();
                arrThreads.add(t);
        });
        try{
            for (int i = 0; i < arrThreads.size(); i++)
            {
                arrThreads.get(i).join();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookIssueDTOS;
    }
}

package com.test.librarymanagementsystem.resources;

import com.test.librarymanagementsystem.dto.request.SearchBookIssueRequest;
import com.test.librarymanagementsystem.model.BookIssueDetail;
import com.test.librarymanagementsystem.service.BookIssueDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BookIssueDetailResource {

      @Autowired
      private BookIssueDetailService bookIssueDetailService;

      @RequestMapping(value = "/issuebooks",method = RequestMethod.POST)
      public ResponseEntity issueBook(@RequestBody BookIssueDetail bookIssueDetail){
          Map<String,Object> responseMap = new HashMap<>();
          try{
              bookIssueDetailService.issueBook(bookIssueDetail);
              responseMap.put("success","Book issued successfully");
          }catch (Exception e){
              responseMap.put("errorMsg",e.getMessage());
          }
          return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);

      }
    @RequestMapping("/issuedbooks")
    public ResponseEntity issuedBook(){
        Map<String,Object> responseMap = new HashMap<>();
        try{
            responseMap.put("issuedBooks",bookIssueDetailService.getIssuedBook());
        }catch (Exception e){
            responseMap.put("errorMsg",e.getMessage());
        }
        return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @RequestMapping(value = "/issuebooks/submit/{id}",method = RequestMethod.PUT)
    public ResponseEntity submitBook(@PathVariable Long id){
        Map<String,Object> responseMap = new HashMap<>();
        try{
            bookIssueDetailService.submitBook(id);
            responseMap.put("success","Book submitted");
        }catch (Exception e){
            responseMap.put("errorMsg",e.getMessage());
        }
        return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/issuebooks/user/{id}",method = RequestMethod.GET)
    public ResponseEntity getIssuedBookByUserId(@PathVariable Long id){
        Map<String,Object> responseMap = new HashMap<>();
        try{
            responseMap.put("issuedBooks",bookIssueDetailService.getIssuedBookByUserId(id));
        }catch (Exception e){
            responseMap.put("errorMsg",e.getMessage());
        }
        return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @RequestMapping(value = "/issuebooks/search",method = RequestMethod.POST)
    public ResponseEntity searchBookIssue(@RequestBody SearchBookIssueRequest searchIssuedBook){
        Map<String,Object> responseMap = new HashMap<>();
        try{
            responseMap.put("issuedBooks",bookIssueDetailService.searchIssuedBook(searchIssuedBook));
        }catch (Exception e){
            responseMap.put("errorMsg",e.getMessage());
        }
        return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @RequestMapping("/issuedbooks/asynchronous")
    public ResponseEntity searchBookIssueAsynchronous(){
        Map<String,Object> responseMap = new HashMap<>();
        try{
            responseMap.put("issuedBooks",bookIssueDetailService.getIssuedBookAsynchronousUseCase());
        }catch (Exception e){
            responseMap.put("errorMsg",e.getMessage());
        }
        return new ResponseEntity(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

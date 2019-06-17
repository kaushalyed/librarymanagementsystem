package com.test.librarymanagementsystem.resources;

import com.test.librarymanagementsystem.model.Role;
import com.test.librarymanagementsystem.model.User;
import com.test.librarymanagementsystem.service.RoleService;
import com.test.librarymanagementsystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserResource {

    Logger logger = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/login")
    String login(){
        /*return  new ModelAndView("home");*/
        return "login";
    }
    @RequestMapping("/home")
    String homePage(){
        /*return  new ModelAndView("home");*/
        return "home";
    }
    @RequestMapping("/create-user")
    String createUser(){
        /*return  new ModelAndView("home");*/
        return "create-user";
    }

    @RequestMapping(value = "/users",method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user){

        logger.info("-----------------------Create User Called---------------------");
        Map<String,Object> responseMap=new HashMap<String,Object>();
        try{
            userService.createUser(user);
            responseMap.put("success","user created successfully");
            return new ResponseEntity<>(responseMap,HttpStatus.OK);
        }catch(Exception e) {
            responseMap.put("errorMessage", e.getMessage());
            return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping("/users")
    @ResponseBody
    public ResponseEntity getUsers(){
        Map<String,Object> responseMap=new HashMap<String,Object>();
        try{
            responseMap.put("users",userService.getUsers());
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }catch(Exception e) {
            responseMap.put("errorMessage",e.getMessage());
            return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //logger.info("----------------------Users Size-----------------------"+userService.getUsers().size());
        //return userService.getUsers();
    }
    @RequestMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity getUser(@PathVariable(value="id") Long id){
        Map<String,Object> responseMap=new HashMap<String,Object>();
        try{
            responseMap.put("users",userService.getUser(id));
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }catch(Exception e) {
            responseMap.put("errorMessage",e.getMessage());
            return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping("/users/byname/{firstName}")
    @ResponseBody
    public ResponseEntity getUser(@PathVariable String firstName){
        Map<String,Object> responseMap=new HashMap<String,Object>();
        try{
            responseMap.put("users",userService.getUsersByFirstName(firstName));
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }catch(Exception e) {
            responseMap.put("errorMessage",e.getMessage());
            return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/users/{id}",method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody User user,@PathVariable(value="id") Long id){

        logger.info("-----------------------Update User Called---------------------");
        Map<String,Object> responseMap=new HashMap<String,Object>();
        try{
            userService.updateUser(user,id);
            responseMap.put("success","user updated successfully");
            return new ResponseEntity<>(responseMap,HttpStatus.OK);
        }catch(Exception e) {
            responseMap.put("errorMessage", e.getMessage());
            return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/users/roles")
    @ResponseBody
    public List<Role> getRoles(){
        logger.info("-------------------getroles called-----------------------");
        /*Map<String,Object> responseMap = new HashMap<>();
        try{
            responseMap.put("roles",roleService.getRoles());
            return new ResponseEntity<>(responseMap,HttpStatus.OK);
        }catch(Exception e){
            responseMap.put("errorMessage",e.getMessage());
            return new ResponseEntity<>(responseMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
/*        List<Role> list = roleService.getRoles();
        System.out.println("List size: "+list.size());

        roleService.getRoles().stream().forEach(s->System.out.println("ROle: "+s.getName()));*/
        return roleService.getRoles();
    }
    @RequestMapping(value = "/users/disable-user/{id}",method = RequestMethod.PUT)
    public ResponseEntity disableUser(@PathVariable(value = "id") Long id){
        logger.info("-------------------getroles called-----------------------");
        Map<String,Object> responseMap = new HashMap<>();
        try{
            userService.disableUser(id);
            responseMap.put("success","user disabled successfully");
            return new ResponseEntity<>(responseMap,HttpStatus.OK);
        }catch(Exception e){
            responseMap.put("errorMessage",e.getMessage());
            return new ResponseEntity<>(responseMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/users/enable-user/{id}",method = RequestMethod.PUT)
    public ResponseEntity enableUser(@PathVariable(value = "id") Long id){

        Map<String,Object> responseMap = new HashMap<>();
        try{
            userService.enableUser(id);
            responseMap.put("success","user enabled successfully");
            return new ResponseEntity<>(responseMap,HttpStatus.OK);
        }catch(Exception e){
            responseMap.put("errorMessage",e.getMessage());
            return new ResponseEntity<>(responseMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


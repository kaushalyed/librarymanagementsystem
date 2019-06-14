package com.test.librarymanagementsystem.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.librarymanagementsystem.exception.InvalidUserId;
import com.test.librarymanagementsystem.exception.UserNameNotFoundException;
import com.test.librarymanagementsystem.model.User;
import com.test.librarymanagementsystem.repository.UserRepository;
import com.test.librarymanagementsystem.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void createUser(User user){
        userRepository.save(user);
    }
    @Transactional
    @Override
    public void updateUser(User user,Long id){
        Optional userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            throw new InvalidUserId("User Id doesn't exist");
        }
        user.setId(id);
        userRepository.save(user);
    }
    @Override
    public void disableUser(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setEnabled(false);
            userRepository.save(user);
        }else{
            throw new InvalidUserId("User id doesn't exist");
        }
    }
    @Override
    public void enableUser(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setEnabled(true);
            userRepository.save(user);
        }else{
            throw new InvalidUserId("User id doesn't exist");
        }
    }

    @Override
    public List<User> getUsersByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user =  userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }else {
            throw new InvalidUserId("User id doesn't exist");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user != null) {
            return user;
        }
        throw new UserNameNotFoundException(" Bad credentials");
    }
}

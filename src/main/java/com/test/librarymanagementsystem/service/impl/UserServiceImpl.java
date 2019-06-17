package com.test.librarymanagementsystem.service.impl;

import com.test.librarymanagementsystem.dto.response.UserDTO;
import com.test.librarymanagementsystem.exception.InvalidUserId;
import com.test.librarymanagementsystem.exception.UserNameNotFoundException;
import com.test.librarymanagementsystem.model.User;
import com.test.librarymanagementsystem.repository.UserRepository;
import com.test.librarymanagementsystem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void createUser(User user){
        user.setUserPass(passwordEncoder.encode(user.getUserPass()));
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
    public List<UserDTO> getUsersByFirstName(String firstName) {
        List<User> users = userRepository.findByFirstName(firstName);
        List<UserDTO> userDTOS = null;
        if(users!=null && users.size()>0){
            userDTOS = users.stream().map(x->modelMapper.map(x,UserDTO.class)).collect(Collectors.toList());
        }
        return userDTOS;
    }

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = users.stream().map(x->modelMapper.map(x,UserDTO.class)).collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public UserDTO getUser(Long id) {
        Optional<User> user =  userRepository.findById(id);
        if(user.isPresent()){
            return modelMapper.map(user.get(),UserDTO.class);
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

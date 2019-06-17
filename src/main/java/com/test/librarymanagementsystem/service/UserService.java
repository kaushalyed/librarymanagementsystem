package com.test.librarymanagementsystem.service;

import com.test.librarymanagementsystem.dto.response.UserDTO;
import com.test.librarymanagementsystem.model.User;

import java.util.List;


public interface UserService {

    void createUser(User user);
    void updateUser(User user,Long id);
    List<UserDTO> getUsers();
    UserDTO getUser(Long id);
    void disableUser(Long id);
    void enableUser(Long id);
    List<UserDTO> getUsersByFirstName(String firstName);
}

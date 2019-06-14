package com.test.librarymanagementsystem.repository;

import com.test.librarymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByFirstName(String firstName);

    @Query("SELECT DISTINCT user FROM User user " +
            "WHERE user.userName = :userName")
    User findByUserName(@Param("userName") String userName);

}

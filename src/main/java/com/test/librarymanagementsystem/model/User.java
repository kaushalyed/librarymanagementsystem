package com.test.librarymanagementsystem.model;

import com.test.librarymanagementsystem.constant.DBConstant;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name="user_name")
    private String userName;

    @NotBlank
    @NotNull
    @Column(name="first_name")
    private String firstName;

    @NotBlank
    @NotNull
    @Column(name="last_name")
    private String lastName;

    @NotNull
    @NotBlank
    @Column(name="user_pass")
    private String userPass;


    @Length(min=DBConstant.SIZE_FIVE,max = DBConstant.SIZE_FOURY_FIVE)
    @Column(name="email",unique = true,nullable = false,length = DBConstant.SIZE_FOURY_FIVE)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns= {
            @JoinColumn(name="user_id",referencedColumnName="id")},inverseJoinColumns= {
            @JoinColumn(name="role_id",referencedColumnName="id")})
    private List<Role> roles;

    @Column(name="enabled")
    private boolean enabled;

    @Column(name="account_expired")
    private boolean accountExpired;

    @Column(name="credentials_expired")
    private boolean credentialsExpired;

    @Column(name="account_locked")
    private boolean accountLocked;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

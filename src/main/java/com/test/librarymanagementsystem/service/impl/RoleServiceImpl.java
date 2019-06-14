package com.test.librarymanagementsystem.service.impl;

import com.test.librarymanagementsystem.model.Role;
import com.test.librarymanagementsystem.repository.RoleRepository;
import com.test.librarymanagementsystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

}

package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Admin;

public interface AdminService {

    Admin login(String email, String password);
    
    
}

package com.example.mapping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mapping.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    Admin findByEmail(String email);
}

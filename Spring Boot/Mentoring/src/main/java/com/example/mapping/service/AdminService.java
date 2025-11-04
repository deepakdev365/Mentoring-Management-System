package com.example.mapping.service;

import java.util.List;

import com.example.mapping.model.Admin;

public interface AdminService {

    // Save new admin
    Admin saveAdmin(Admin admin);

    // Get all admins (useful if multiple admins exist)
    List<Admin> getAllAdmins();

    // Get admin by ID
    Admin getAdminById(Long id);

    // Get admin by email
    Admin getAdminByEmail(String email);

    // Update admin details
    Admin updateAdmin(Long id, Admin admin);

    // Delete admin by ID
    void deleteAdmin(Long id);
}

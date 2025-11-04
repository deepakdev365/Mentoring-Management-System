package com.example.mapping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapping.model.Admin;
import com.example.mapping.repository.AdminRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Save new admin
    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // Get all admins
    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Get admin by ID
    @Override
    public Admin getAdminById(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        return admin.orElse(null);
    }

    // Get admin by email
    @Override
    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    // Update admin details
    @Override
    public Admin updateAdmin(Long id, Admin updatedAdmin) {
        Admin existingAdmin = adminRepository.findById(id).orElse(null);
        if (existingAdmin != null) {
            existingAdmin.setFullName(updatedAdmin.getFullName());
            existingAdmin.setEmail(updatedAdmin.getEmail());
            existingAdmin.setPassword(updatedAdmin.getPassword());
            return adminRepository.save(existingAdmin);
        }
        return null;
    }

    // Delete admin by ID
    @Override
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}

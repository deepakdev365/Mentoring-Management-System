package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Complaint;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByRegistrationNumber(String registrationNumber);
    
    List<Complaint> findByMentorId(Long mentorId);

}
package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Complaint;

public interface ComplaintService {

    Complaint addComplaint(Complaint complaint);

    List<Complaint> getByStudent(String regNo);
    
    void updateStatus(Long id, String status);
    
    List<Complaint> getByMentor(Long mentorId);

}
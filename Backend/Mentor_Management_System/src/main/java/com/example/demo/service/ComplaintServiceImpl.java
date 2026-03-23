package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Complaint;
import com.example.demo.repository.ComplaintRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Override
    public Complaint addComplaint(Complaint complaint){

        complaint.setStatus("Pending");
        complaint.setCreatedAt(LocalDateTime.now());

        return complaintRepository.save(complaint);
    }

    @Override
    public List<Complaint> getByStudent(String regNo){
        return complaintRepository.findByRegistrationNumber(regNo);
    }
    
    @Override
    public void updateStatus(Long id, String status){

        Complaint complaint = complaintRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Complaint not found"));

        complaint.setStatus(status);

        complaintRepository.save(complaint);
    }
    
    @Override
    public List<Complaint> getByMentor(Long mentorId){
        return complaintRepository.findByMentorId(mentorId);
    }
}
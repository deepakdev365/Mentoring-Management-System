package com.example.mapping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapping.model.Complaint;
import com.example.mapping.repository.ComplaintRepository;
import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Override
    public Complaint addComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    @Override
    public List<Complaint> getComplaintsByMentor(String mentorEmail) {
        return complaintRepository.findByMentor_Email(mentorEmail);
    }

    @Override
    public List<Complaint> getComplaintsByStudent(String studentEmail) {
        return complaintRepository.findByStudent_Email(studentEmail);
    }

    @Override
    public Complaint updateStatus(Long complaintId, String status) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(() ->
                new RuntimeException("Complaint not found"));
        complaint.setStatus(status);
        return complaintRepository.save(complaint);
    }
}

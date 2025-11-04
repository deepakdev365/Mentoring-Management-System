package com.example.mapping.service;

import com.example.mapping.model.Complaint;
import java.util.List;

public interface ComplaintService {
    Complaint addComplaint(Complaint complaint);
    List<Complaint> getComplaintsByMentor(String mentorEmail);
    List<Complaint> getComplaintsByStudent(String studentEmail);
    Complaint updateStatus(Long complaintId, String status);
}

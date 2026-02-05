package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Complaint;

public interface ComplaintService {

    Complaint createComplaint(Complaint complaint);

    List<Complaint> getAllComplaints();
}

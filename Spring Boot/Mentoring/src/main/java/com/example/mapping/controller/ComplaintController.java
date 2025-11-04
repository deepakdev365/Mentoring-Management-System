package com.example.mapping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mapping.model.Complaint;
import com.example.mapping.service.ComplaintService;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "http://localhost:4200")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping("/add")
    public Complaint addComplaint(@RequestBody Complaint complaint) {
        return complaintService.addComplaint(complaint);
    }

    @GetMapping("/mentor/{email}")
    public List<Complaint> getComplaintsByMentor(@PathVariable String email) {
        return complaintService.getComplaintsByMentor(email);
    }

    @GetMapping("/student/{email}")
    public List<Complaint> getComplaintsByStudent(@PathVariable String email) {
        return complaintService.getComplaintsByStudent(email);
    }

    @PutMapping("/update-status/{id}")
    public Complaint updateStatus(@PathVariable Long id, @RequestParam String status) {
        return complaintService.updateStatus(id, status);
    }
}

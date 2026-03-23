package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Complaint;
import com.example.demo.service.ComplaintService;

@RestController
@RequestMapping("/complaint")
@CrossOrigin(origins = "http://localhost:4200")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping("/add")
    public Complaint addComplaint(@RequestBody Complaint complaint){
        return complaintService.addComplaint(complaint);
    }

    @GetMapping("/student/{regNo}")
    public List<Complaint> getByStudent(@PathVariable String regNo){
        return complaintService.getByStudent(regNo);
    }
    
    @PutMapping("/update-status/{id}")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status){

        complaintService.updateStatus(id, status);
        return "Updated";
    }
    
    
    @GetMapping("/mentor/{mentorId}")
    public List<Complaint> getByMentor(@PathVariable Long mentorId){
        return complaintService.getByMentor(mentorId);
    }
}
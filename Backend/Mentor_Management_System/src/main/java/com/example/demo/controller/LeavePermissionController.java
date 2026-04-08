package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.LeavePermission;
import com.example.demo.service.LeavePermissionService;

@RestController
@RequestMapping("/leave")
@CrossOrigin
public class LeavePermissionController {

    @Autowired
    private LeavePermissionService service;

    // Student apply leave
    @PostMapping("/apply")
    public LeavePermission applyLeave(@RequestBody LeavePermission leave) {
        return service.applyLeave(leave);
    }

    // Get single leave request by ID
    @GetMapping("/{id}")
    public LeavePermission getLeaveById(@PathVariable Long id) {
        return service.getLeaveById(id);
    }

    // Admin view all leave requests
    @GetMapping("/all")
    public List<LeavePermission> getAllLeaves() {
        return service.getAllLeaves();
    }

    // Admin approve leave
    @PutMapping("/approve/{id}")
    public LeavePermission approveLeave(@PathVariable Long id) {
        return service.approveLeave(id);
    }

    // Admin reject leave
    @PutMapping("/reject/{id}")
    public LeavePermission rejectLeave(@PathVariable Long id) {
        return service.rejectLeave(id);
    }

    // Mentor get leaves for their mentees
    @GetMapping("/mentor/{mentorId}")
    public List<LeavePermission> getLeavesByMentorId(@PathVariable Integer mentorId) {
        System.out.println("Fetching leaves for mentor ID: " + mentorId);
        List<LeavePermission> leaves = service.getLeavesByMentorId(mentorId);
        System.out.println("Found " + leaves.size() + " leaves.");
        return leaves;
    }

    // Student get their own leaves
    @GetMapping("/student/{studentId}")
    public List<LeavePermission> getStudentLeaves(@PathVariable Long studentId) {
        return service.getLeavesByStudentId(studentId);
    }
}
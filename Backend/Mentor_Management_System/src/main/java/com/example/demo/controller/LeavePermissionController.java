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
}
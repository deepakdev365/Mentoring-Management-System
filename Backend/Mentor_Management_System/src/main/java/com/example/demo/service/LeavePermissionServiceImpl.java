package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.LeavePermission;
import com.example.demo.repository.LeavePermissionRepository;

@Service
public class LeavePermissionServiceImpl implements LeavePermissionService {

    @Autowired
    private LeavePermissionRepository repository;

    @Override
    public LeavePermission applyLeave(LeavePermission leave) {
        leave.setStatus("Pending");
        return repository.save(leave);
    }

    @Override
    public List<LeavePermission> getAllLeaves() {
        return repository.findAll();
    }

    @Override
    public LeavePermission approveLeave(Long id) {
        LeavePermission leave = repository.findById(id).orElse(null);
        leave.setStatus("Approved");
        return repository.save(leave);
    }

    @Override
    public LeavePermission rejectLeave(Long id) {
        LeavePermission leave = repository.findById(id).orElse(null);
        leave.setStatus("Rejected");
        return repository.save(leave);
    }
}
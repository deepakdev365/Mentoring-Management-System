package com.example.demo.service;

import java.util.List;
import com.example.demo.model.LeavePermission;

public interface LeavePermissionService {

    LeavePermission applyLeave(LeavePermission leave);

    List<LeavePermission> getAllLeaves();

    LeavePermission approveLeave(Long id);

    LeavePermission rejectLeave(Long id);

    List<LeavePermission> getLeavesByStudentId(Long studentId);

    List<LeavePermission> getLeavesByMentorId(Integer mentorId);

    LeavePermission getLeaveById(Long id);
}
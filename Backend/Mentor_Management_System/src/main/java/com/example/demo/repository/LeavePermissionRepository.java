package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.LeavePermission;

public interface LeavePermissionRepository extends JpaRepository<LeavePermission, Long> {

}
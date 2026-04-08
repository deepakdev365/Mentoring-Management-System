package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.model.LeavePermission;
import java.util.List;

public interface LeavePermissionRepository extends JpaRepository<LeavePermission, Long> {
    List<LeavePermission> findByStudentId(Long studentId);

    @Query("SELECT l FROM LeavePermission l WHERE l.studentId IN (SELECT s.sl_No FROM Student s WHERE s.mentor.id = :mentorId)")
    List<LeavePermission> findByMentorId(@Param("mentorId") Integer mentorId);
}
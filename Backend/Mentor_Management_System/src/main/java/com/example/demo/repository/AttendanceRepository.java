package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Attendance;
import com.example.demo.model.SubjectRegistration;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findBySubjectRegistration(SubjectRegistration subjectRegistration);

    boolean existsBySubjectRegistration(SubjectRegistration subjectRegistration);

}
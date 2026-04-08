package com.example.demo.repository;

import com.example.demo.model.StudentMarks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentMarksRepository extends JpaRepository<StudentMarks, Long> {
    List<StudentMarks> findByRollNo(String rollNo);

    Optional<StudentMarks> findByRollNoAndSubjectCodeAndSemester(String rollNo, String subjectCode, String semester);

    List<StudentMarks> findByEmail(String email);
}
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.StudentMarks;

public interface StudentMarksRepository extends JpaRepository<StudentMarks, Long> {
}

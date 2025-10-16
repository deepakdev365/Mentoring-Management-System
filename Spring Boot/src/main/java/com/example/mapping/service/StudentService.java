package com.example.mapping.service;

import java.util.List;
import java.util.Optional;

import com.example.mapping.model.Student;

public interface StudentService {
	 // Create / Register a new student
    Student saveStudent(Student student);

    // Get all students
    List<Student> getAllStudents();

    // Get a student by ID
    Optional<Student> getStudentById(Long id);

    // Update student details
    Student updateStudent(Student student);

    // Delete a student by ID
    void deleteStudent(Long id);

    // Optional: Find student by email (for login)
    Optional<Student> getStudentByEmail(String email);

}

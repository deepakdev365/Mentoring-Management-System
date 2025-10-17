package com.example.mapping.service;

import java.util.List;
import java.util.Optional;

import com.example.mapping.model.Student;

public interface StudentService {
    Student saveStudent(Student student);

    List<Student> getAllStudents();

    Optional<Student> getStudentById(Long id);

    Student updateStudent(Student student);

    void deleteStudent(Long id);

    Optional<Student> getStudentByEmail(String email);

}

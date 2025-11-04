package com.example.mapping.service;

import java.util.List;
import com.example.mapping.model.Student;

public interface StudentService {

    Student saveStudent(Student student);

    Student updateStudent(Long registrationNumber, Student student);

    List<Student> getAllStudents();

    Student getStudentById(Long registrationNumber);

    void deleteStudent(Long registrationNumber);

    Student getStudentByEmail(String email);
}

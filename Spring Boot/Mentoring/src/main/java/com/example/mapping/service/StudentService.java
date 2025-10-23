package com.example.mapping.service;

import java.util.List;
import com.example.mapping.model.Student;

public interface StudentService {

    Student saveStudent(Student student);

    Student updateStudent(String registrationNumber, Student student);

    List<Student> getAllStudents();

    Student getStudentById(String registrationNumber);

    void deleteStudent(String registrationNumber);

    Student getStudentByEmail(String email);
}

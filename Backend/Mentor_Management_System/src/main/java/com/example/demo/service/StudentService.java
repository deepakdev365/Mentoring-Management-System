package com.example.demo.service;

import com.example.demo.model.Student;

public interface StudentService {
	Student getStudentbyregistrationNumber(Long registrationNumber);
	Student saveStudent(Student student);

}

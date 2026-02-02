package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Student;

public interface StudentService {
	Student saveStudent(Student student);
	
	void saveAllStudent(List<Student> students);

}

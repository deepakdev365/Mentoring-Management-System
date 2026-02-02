package com.example.demo.service;
import com.example.demo.repository.StudentRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Student;


@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


	@Override
	public Student saveStudent(Student student) {
		// TODO Auto-generated method stub
		return studentRepository.save(student);
	}


	@Override
	public void saveAllStudent(List<Student> students) {
		// TODO Auto-generated method stub
		studentRepository.saveAll(students);
		
	}

	
}

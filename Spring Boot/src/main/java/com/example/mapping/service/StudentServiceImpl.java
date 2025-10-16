package com.example.mapping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.mapping.model.Student;
import com.example.mapping.repository.StudentRepository;

public class StudentServiceImpl implements StudentService {

	 @Autowired
	    private StudentRepository studentRepository;

	    @Override
	    public Student saveStudent(Student student) {
	        return studentRepository.save(student);
	    }

	    @Override
	    public List<Student> getAllStudents() {
	        return studentRepository.findAll();
	    }

	    @Override
	    public Optional<Student> getStudentById(Long id) {
	        return studentRepository.findById(id);
	    }

	    @Override
	    public Student updateStudent(Student student) {
	        return studentRepository.save(student);  // save() also updates if ID exists
	    }

	    @Override
	    public void deleteStudent(Long id) {
	        studentRepository.deleteById(id);
	    }

	    @Override
	    public Optional<Student> getStudentByEmail(String email) {
	        return studentRepository.findByEmail(email);
	    }

}

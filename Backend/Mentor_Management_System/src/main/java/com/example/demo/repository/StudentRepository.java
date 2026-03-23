package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	
	 Student findByRegistrationNumber(String registrationNumber);
	 
	 Optional<Student> findByEmailAndPassword(String email, String password);
	 List<Student> findByRegistrationNumberIn(List<String> registrationNumbers);
	 
	 List<Student> findByMentor_Id(Integer mentorId);	 
}

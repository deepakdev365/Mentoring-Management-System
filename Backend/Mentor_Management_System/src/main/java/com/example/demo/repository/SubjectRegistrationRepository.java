package com.example.demo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Student;
import com.example.demo.model.SubjectRegistration;



@Repository
public interface SubjectRegistrationRepository extends JpaRepository<SubjectRegistration, Long>{
	  boolean existsByStudentAndSubjectCode(Student student, String subjectCode);
	
	  Optional<SubjectRegistration> findByStudentAndSubjectCode(Student student, String subjectCode);


}

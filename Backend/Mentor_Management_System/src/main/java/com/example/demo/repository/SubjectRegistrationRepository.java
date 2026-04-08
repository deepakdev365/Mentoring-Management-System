package com.example.demo.repository;


import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Student;
import com.example.demo.model.SubjectRegistration;



@Repository
public interface SubjectRegistrationRepository extends JpaRepository<SubjectRegistration, Long>{
	  boolean existsByStudentAndSubjectCodeAndSemester(Student student, String subjectCode, String semester);
	
	  Optional<SubjectRegistration> findByStudentAndSubjectCodeAndSemester(Student student, String subjectCode, String semester);
	
	  List<SubjectRegistration> findByStudentRegistrationNumber(String registrationNumber);


}

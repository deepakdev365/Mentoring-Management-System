package com.example.demo.service;

import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.MentorRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Mentor;
import com.example.demo.model.Student;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final MentorRepository mentorRepository;

    public StudentServiceImpl(StudentRepository studentRepository,
                              MentorRepository mentorRepository) {
        this.studentRepository = studentRepository;
        this.mentorRepository = mentorRepository;
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void saveAllStudent(List<Student> students) {
        studentRepository.saveAll(students);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student login(String email, String password) {
        return studentRepository
                .findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }

	@Override
	public void assignMentor(Long mentorId, List<String> registrationNumbers) {
		// TODO Auto-generated method stub
		Mentor mentor = mentorRepository.findById(mentorId.intValue())
	            .orElseThrow(() -> new RuntimeException("Mentor not found"));

	    List<Student> students =
	            studentRepository.findByRegistrationNumberIn(registrationNumbers);

	    if (students.isEmpty()) {
	        throw new RuntimeException("No students found");
	    }

	    for (Student student : students) {
	        student.setMentor(mentor);
	    }

	    studentRepository.saveAll(students);

	}

  
}
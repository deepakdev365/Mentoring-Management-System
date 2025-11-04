package com.example.mapping.service;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mapping.model.Student;
import com.example.mapping.repository.StudentRepository;
import com.example.mapping.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Student with this email already exists!");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long registrationNumber, Student student) {
        Optional<Student> existing = studentRepository.findById(registrationNumber);
        if (existing.isPresent()) {
            Student old = existing.get();
            old.setFullName(student.getFullName());
            old.setEmail(student.getEmail());
            old.setDepartment(student.getDepartment());
            old.setSemester(student.getSemester());
            old.setPermanentAddress(student.getPermanentAddress());
            old.setPhoneNumber(student.getPhoneNumber());
            old.setMentor(student.getMentor());
            old.setPassword(student.getPassword());
            return studentRepository.save(old);
        } else {
            throw new RuntimeException("Student not found with ID: " + registrationNumber);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long registrationNumber) {
        return studentRepository.findById(registrationNumber)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + registrationNumber));
    }

    @Override
    public void deleteStudent(Long registrationNumber) {
        studentRepository.deleteById(registrationNumber);
    }

    @Override
    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
}

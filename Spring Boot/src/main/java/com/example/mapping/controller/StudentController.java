package com.example.mapping.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mapping.model.Student;
import com.example.mapping.service.StudentService;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Get all students
    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> studentOpt = studentService.getStudentById(id);
        return studentOpt.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    // Register a new student
    @PostMapping("/register")
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    // Update student
    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Optional<Student> studentOpt = studentService.getStudentById(id);
        if (!studentOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Student student = studentOpt.get();
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setPassword(studentDetails.getPassword());
        student.setRollNo(studentDetails.getRollNo());
        student.setClassName(studentDetails.getClassName());
        student.setCgpa(studentDetails.getCgpa());
        student.setAttendance(studentDetails.getAttendance());

        Student updatedStudent = studentService.updateStudent(student);
        return ResponseEntity.ok(updatedStudent);
    }

    // Delete student
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!studentService.getStudentById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    // Get student by email
    @GetMapping("/email/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
        Optional<Student> studentOpt = studentService.getStudentByEmail(email);
        return studentOpt.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }
}

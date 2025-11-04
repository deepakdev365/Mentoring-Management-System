package com.example.mapping.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.mapping.model.Student;
import com.example.mapping.service.StudentService;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/login")
    public String loginStudent(@RequestBody Student student) {
        Student existingStudent = studentService.getStudentByEmail(student.getEmail());
        if (existingStudent != null && existingStudent.getPassword().equals(student.getPassword())) {
            return "Login successful";
        } else {
            return "Invalid email or password";
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.saveStudent(student));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{registrationNumber}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long registrationNumber) {
        return ResponseEntity.ok(studentService.getStudentById(registrationNumber));
    }

    @PutMapping("/update/{registrationNumber}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long registrationNumber, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(registrationNumber, student));
    }

    @DeleteMapping("/delete/{registrationNumber}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long registrationNumber) {
        studentService.deleteStudent(registrationNumber);
        return ResponseEntity.ok("Student deleted successfully!");
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
        return ResponseEntity.ok(studentService.getStudentByEmail(email));
    }
}

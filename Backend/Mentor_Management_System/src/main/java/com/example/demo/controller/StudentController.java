package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadStudents(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please upload a file.");
            }

            List<String> errors = studentService.uploadStudents(file);

            if (errors.isEmpty()) {
                return ResponseEntity.ok("Students uploaded successfully");
            } else {
                return ResponseEntity.badRequest().body(errors);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error while uploading students: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        try {
            Student student = studentService.login(email, password);
            if (student == null) {
                return ResponseEntity.badRequest().body("Invalid Email or Password");
            }
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid Email or Password");
        }
    }

    @GetMapping("/mentor/{id}")
    public List<Student> getStudentsByMentor(@PathVariable int id) {
        return studentService.getStudentsByMentorId(id);
    }

    @GetMapping("/{regNo}")
    public Student getStudent(@PathVariable String regNo) {
        return studentService.getStudentByRegNo(regNo);
    }

    @PutMapping("/unassign/{regNo}")
    public String unassign(@PathVariable String regNo) {
        studentService.unassignMentor(regNo);
        return "Mentor unassigned successfully";
    }
}
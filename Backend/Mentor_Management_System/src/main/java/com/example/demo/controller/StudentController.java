package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Student student) {

        Student dbStudent =
                studentRepository.findByEmailAndPassword(
                        student.getEmail(),
                        student.getPassword()
                );

        if (dbStudent != null) {
            return ResponseEntity.ok(dbStudent);
        } else {
            return ResponseEntity.status(401)
                    .body("Invalid email or password");
        }
    }
}

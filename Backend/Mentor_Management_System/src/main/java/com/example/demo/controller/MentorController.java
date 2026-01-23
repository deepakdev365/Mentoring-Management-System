package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Mentor;
import com.example.demo.service.MentorService;

@RestController
@RequestMapping("/mentor")
@CrossOrigin(origins = "http://localhost:4200")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Mentor mentor) {

        Mentor m = mentorService.login(
                mentor.getEmail(),
                mentor.getPassword()
        );

        if (m == null) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        return ResponseEntity.ok("Login successful");
    }
}

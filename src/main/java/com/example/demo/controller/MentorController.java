package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.MentorService;

@RestController
@RequestMapping("/mentor")
@CrossOrigin("*")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(
            @RequestParam("file") MultipartFile file) {

        mentorService.uploadMentorExcel(file);
        return ResponseEntity.ok("Mentor Excel Uploaded Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String email,
            @RequestParam String password) {

        mentorService.login(email, password);
        return ResponseEntity.ok("Login successful");
    }
}

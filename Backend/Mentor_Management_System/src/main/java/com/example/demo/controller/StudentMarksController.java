package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.StudentMarksService;

@RestController
@RequestMapping("/marks")
public class StudentMarksController {

    private final StudentMarksService service;

    public StudentMarksController(StudentMarksService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            service.uploadExcel(file);
            return ResponseEntity.ok("Excel uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }
}

package com.example.demo.controller;

import com.example.demo.model.StudentMarks;
import com.example.demo.service.StudentMarksService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/marks")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentMarksController {

    private final StudentMarksService service;

    public StudentMarksController(StudentMarksService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMarks(@RequestParam("file") MultipartFile file) {
        try {
            List<String> errors = service.uploadExcel(file);
            if (errors.isEmpty()) {
                return ResponseEntity.ok("Student marks uploaded successfully.");
            } else {
                return ResponseEntity.badRequest().body("Upload failed: " + String.join(", ", errors));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentMarks>> getAllMarks() {
        return ResponseEntity.ok(service.getAllMarks());
    }

    @GetMapping("/roll/{rollNo}")
    public ResponseEntity<List<StudentMarks>> getMarksByRollNo(@PathVariable String rollNo) {
        return ResponseEntity.ok(service.getMarksByRollNo(rollNo));
    }

    @GetMapping("/{rollNo}")
    public ResponseEntity<List<StudentMarks>> getByRollNo(@PathVariable String rollNo) {
        return ResponseEntity.ok(service.getMarksByRollNo(rollNo));
    }
}

package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.SubjectRegistration;
import com.example.demo.service.SubjectRegistrationService;


@RestController
@RequestMapping("/subject")
@CrossOrigin(origins = "http://localhost:4200")
public class SubjectRegistrationController {

    @Autowired
    private SubjectRegistrationService subjectRegistrationService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadSubjectRegistration(@RequestParam("subject") MultipartFile file) {

        try {

            List<String> errors = subjectRegistrationService.uploadSubjectRegistration(file);

            if (errors.isEmpty()) {
                return ResponseEntity.ok("Subject Registration Uploaded Successfully");
            } 
            else {
                return ResponseEntity.badRequest().body(errors);
            }

        } 
        catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/student/{regNo}")
    public ResponseEntity<List<SubjectRegistration>> getStudentRegistrations(@PathVariable String regNo) {
        return ResponseEntity.ok(subjectRegistrationService.getRegistrationsByRegNo(regNo));
    }
}
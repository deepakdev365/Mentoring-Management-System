package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Mentor;
import com.example.demo.service.MentorService;


@RestController
@RequestMapping("/mentor")
@CrossOrigin(origins =  "http://localhost:4200",
allowedHeaders = "*")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(
            @RequestParam("file") MultipartFile file) {

        List<String> errors = mentorService.uploadMentorExcel(file);
        if (errors.isEmpty()) {
            return ResponseEntity.ok("Mentor Excel Uploaded Successfully");
        } else {
            return ResponseEntity.badRequest().body("Upload failed: " + String.join(", ", errors));
        }
    }

    @PostMapping("/login")
    public Mentor login(@RequestParam String email,
                        @RequestParam String password) {

        return mentorService.login(email, password); 
    }
    @GetMapping("/all")
    public List<Mentor> getAllMentors(){
    	return mentorService.getAllMentors();
    }
}

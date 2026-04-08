package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.AttendanceService;


@RestController
@RequestMapping("/attendance")
@CrossOrigin("*")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadAttendance(
            @RequestParam("attendance") MultipartFile file) {

        try {

            List<String> errors = attendanceService.uploadAttendance(file);

            if (errors.isEmpty()) {
                return ResponseEntity.ok("Attendance uploaded successfully");
            } 
            else {
                return ResponseEntity.badRequest().body(errors);
            }

        } 
        catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error uploading attendance: " + e.getMessage());
        }
    }
}
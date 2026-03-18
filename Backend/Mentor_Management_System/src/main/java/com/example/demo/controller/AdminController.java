package com.example.demo.controller;

import com.example.demo.model.Admin;
import com.example.demo.service.AdminService;
import com.example.demo.service.StudentService;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/admin")

public class AdminController {

    private final AdminService adminService;
    private final StudentService studentService;

    public AdminController(AdminService adminService,
                           StudentService studentService) {
        this.adminService = adminService;
        this.studentService = studentService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin admin) {

        Admin dbAdmin =
                adminService.login(admin.getEmail(), admin.getPassword());

        if (dbAdmin != null) {
            return ResponseEntity.ok("Successfully logged In.");
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }

    @PostMapping("/assign-mentor")
    public String assignMentor(@RequestBody Map<String, Object> data) {

        Long mentorId = Long.valueOf(data.get("mentorId").toString());

        List<Integer> studentIdsRaw = (List<Integer>) data.get("studentIds");

        List<Long> studentIds = studentIdsRaw.stream()
                .map(Long::valueOf)
                .toList();

        studentService.assignMentor(mentorId, studentIds);

        return "Mentor assigned successfully";
    }
}
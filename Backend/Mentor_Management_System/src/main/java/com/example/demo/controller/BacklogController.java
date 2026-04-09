package com.example.demo.controller;

import com.example.demo.model.Backlog;
import com.example.demo.service.BacklogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/backlogs")
@CrossOrigin(origins = "http://localhost:4200")
public class BacklogController {

    @Autowired
    private BacklogService backlogService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadBacklogs(@RequestParam("file") MultipartFile file) {
        try {
            List<String> errors = backlogService.uploadExcel(file);
            if (errors.isEmpty()) {
                return ResponseEntity.ok("Backlogs uploaded successfully.");
            } else {
                return ResponseEntity.badRequest().body("Upload failed: " + String.join(", ", errors));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/student/{email:.+}")
    public ResponseEntity<List<Backlog>> getBacklogsByEmail(@PathVariable String email) {
        List<Backlog> backlogs = backlogService.getBacklogsByEmail(email);
        return ResponseEntity.ok(backlogs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBacklog(@PathVariable Long id) {
        backlogService.deleteBacklogById(id);
        return ResponseEntity.ok("Backlog deleted successfully.");
    }
}

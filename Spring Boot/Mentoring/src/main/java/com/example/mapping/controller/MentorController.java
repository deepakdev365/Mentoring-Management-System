package com.example.mapping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mapping.model.Mentor;
import com.example.mapping.service.MentorService;

import java.util.List;

@RestController
@RequestMapping("/mentors")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    // Register a new mentor
    @PostMapping("/register")
    public Mentor registerMentor(@RequestBody Mentor mentor) {
        return mentorService.registerMentor(mentor);
    }

    // Get all mentors
    @GetMapping
    public List<Mentor> getAllMentors() {
        return mentorService.getAllMentors();
    }

    // Get mentor by code
    @GetMapping("/{code}")
    public Mentor getMentorByCode(@PathVariable String code) {
        return mentorService.getMentorByCode(code);
    }

    // Delete mentor
    @DeleteMapping("/{id}")
    public String deleteMentor(@PathVariable Long id) {
        mentorService.deleteMentor(id);
        return "Mentor deleted successfully!";
    }
}

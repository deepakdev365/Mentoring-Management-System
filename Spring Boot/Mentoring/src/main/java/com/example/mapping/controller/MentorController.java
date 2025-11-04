package com.example.mapping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mapping.model.Mentor;
import com.example.mapping.service.MentorService;

import java.util.List;

@RestController
@RequestMapping("/mentors")
@CrossOrigin(origins = "http://localhost:4200")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    @PostMapping("/login")
    public String loginMentor(@RequestBody Mentor mentor) {
        Mentor existingMentor = mentorService.getMentorByEmail(mentor.getEmail());
        if (existingMentor != null && existingMentor.getPassword().equals(mentor.getPassword())) {
            return "Login successful";
        } else {
            return "Invalid email or password";
        }
    }

    @PostMapping("/register")
    public Mentor registerMentor(@RequestBody Mentor mentor) {
        return mentorService.registerMentor(mentor);
    }

    @GetMapping
    public List<Mentor> getAllMentors() {
        return mentorService.getAllMentors();
    }

    @GetMapping("/email/{email}")
    public Mentor getMentorByEmail(@PathVariable String email) {
        return mentorService.getMentorByEmail(email);
    }

    @DeleteMapping("/{id}")
    public String deleteMentor(@PathVariable Long id) {
        mentorService.deleteMentor(id);
        return "Mentor deleted successfully!";
    }
}

package com.example.mapping.controller;

import com.example.mapping.model.Admin;
import com.example.mapping.model.Mentor;
import com.example.mapping.model.Student;
import com.example.mapping.repository.MentorRepository;
import com.example.mapping.repository.StudentRepository;
import com.example.mapping.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")  
public class AdminController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private AdminService adminService;   

  
    @PostMapping("/login")
    public String loginAdmin(@RequestBody Admin admin) {
        Admin existingAdmin = adminService.getAdminByEmail(admin.getEmail());

        if (existingAdmin != null && existingAdmin.getPassword().equals(admin.getPassword())) {
            return "Login successful";
        } else {
            return "Invalid email or password";
        }
    }

    // ✅ Assign mentees evenly to mentors
    @PostMapping("/assign-mentees")
    public String assignMenteesToMentors() {
        List<Student> students = studentRepository.findAll();
        List<Mentor> mentors = mentorRepository.findAll();

        if (mentors.isEmpty()) {
            return "No mentors available!";
        }
        if (students.isEmpty()) {
            return "No students available!";
        }

        int totalStudents = students.size();
        int totalMentors = mentors.size();

        int menteesPerMentor = totalStudents / totalMentors;
        int extraMentees = totalStudents % totalMentors;

        int studentIndex = 0;

        for (int i = 0; i < mentors.size(); i++) {
            Mentor mentor = mentors.get(i);
            int assignedCount = menteesPerMentor + (i < extraMentees ? 1 : 0);

            for (int j = 0; j < assignedCount && studentIndex < students.size(); j++) {
                Student student = students.get(studentIndex);
                student.setMentor(mentor);
                studentRepository.save(student);
                studentIndex++;
            }
        }

        return "Mentees assigned successfully! Total students: " + totalStudents +
               ", Total mentors: " + totalMentors +
               ", Each mentor got around " + menteesPerMentor + " mentees.";
    }
}

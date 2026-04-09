package com.example.demo.controller;

import com.example.demo.dto.DashboardSummaryDTO;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentMarksRepository studentMarksRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private SubjectRegistrationRepository registrationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @GetMapping("/student/{regNo}")
    public DashboardSummaryDTO getStudentSummary(@PathVariable String regNo) {
        Student student = studentRepository.findByRegistrationNumber(regNo).orElse(null);
        if (student == null) return new DashboardSummaryDTO(0.0, 0.0, "N/A", "Active", 0);

        // Calculate Attendance
        List<Attendance> attendances = attendanceRepository.findBySubjectRegistration_Student_RegistrationNumber(regNo);
        double avgAttendance = attendances.stream().mapToDouble(Attendance::getPercentage).average().orElse(0.0);

        // Calculate Performance
        List<StudentMarks> marks = studentMarksRepository.findByRollNo(regNo);
        double avgMarks = marks.stream()
                .mapToDouble(m -> m.getPercentage() != null ? m.getPercentage() : 0.0)
                .average()
                .orElse(0.0);

        // Get Payment Status
        List<Payment> payments = paymentRepository.findByStudentId(student.getSl_No());
        String paymentStatus = payments.isEmpty() ? "No Record" : payments.get(payments.size()-1).getPaymentStatus();

        // Get Active Courses
        long courseCount = registrationRepository.findByStudentRegistrationNumber(regNo).size();

        return new DashboardSummaryDTO(
            Math.round(avgAttendance * 10.0) / 10.0,
            Math.round(avgMarks * 10.0) / 10.0,
            paymentStatus,
            "Enrolled",
            (int)courseCount
        );
    }

    @GetMapping("/mentor/{mentorId}/backlogs")
    public List<Backlog> getMentorStudentBacklogs(@PathVariable Integer mentorId) {
        List<Student> students = studentRepository.findByMentor_Id(mentorId);
        List<String> emails = students.stream()
            .map(Student::getEmail)
            .filter(email -> email != null && !email.trim().isEmpty())
            .collect(Collectors.toList());

        if (emails.isEmpty()) {
            return new ArrayList<>();
        }
        return backlogRepository.findByEmailInIgnoreCase(emails);
    }

    @GetMapping("/mentor/{mentorId}/low-attendance")
    public List<Map<String, Object>> getMentorLowAttendanceStudents(@PathVariable Integer mentorId) {
        List<Student> students = studentRepository.findByMentor_Id(mentorId);
        List<Map<String, Object>> lowAttendanceList = new ArrayList<>();

        for (Student student : students) {
            String regNo = student.getRegistrationNumber();
            if (regNo == null || regNo.isEmpty()) continue;

            List<Attendance> attendances = attendanceRepository.findBySubjectRegistration_Student_RegistrationNumber(regNo);
            if (attendances.isEmpty()) continue;
            
            double avgAttendance = attendances.stream().mapToDouble(Attendance::getPercentage).average().orElse(0.0);
            
            if (avgAttendance < 75.0) {
                Map<String, Object> map = new HashMap<>();
                map.put("registrationNumber", regNo);
                map.put("fullName", student.getFullName());
                map.put("branch", student.getBranch());
                map.put("attendancePercentage", Math.round(avgAttendance * 10.0) / 10.0);
                lowAttendanceList.add(map);
            }
        }
        return lowAttendanceList;
    }
}

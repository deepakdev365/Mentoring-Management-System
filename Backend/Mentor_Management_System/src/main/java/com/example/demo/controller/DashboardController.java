package com.example.demo.controller;

import com.example.demo.dto.DashboardSummaryDTO;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}

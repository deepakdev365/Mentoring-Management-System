package com.example.demo.controller;

import com.example.demo.model.Payment;
import com.example.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Make Payment
    @PostMapping("/pay")
    public Payment makePayment(@RequestBody Payment payment) {
        return paymentService.makePayment(payment);
    }

    // Get Payment by ID
    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    // Get Payments by Student
    @GetMapping("/student/{studentId}")
    public List<Payment> getPaymentsByStudent(@PathVariable Long studentId) {
        return paymentService.getPaymentsByStudent(studentId);
    }

    @GetMapping("/email/{email}")
    public Payment getPaymentByEmail(@PathVariable String email) {
        return paymentService.getPaymentByEmail(email);
    }

    @GetMapping("/roll/{rollNo}")
    public List<Payment> getPaymentsByRollNo(@PathVariable String rollNo) {
        return paymentService.getPaymentsByRollNo(rollNo);
    }

    // Get Payments by Mentor
    @GetMapping("/mentor/{mentorId}")
    public List<Payment> getPaymentsByMentor(@PathVariable Long mentorId) {
        return paymentService.getPaymentsByMentor(mentorId);
    }

    // Get All Payments (Admin)
    @GetMapping("/all")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPayments(@RequestParam("file") MultipartFile file) {
        try {
            List<String> errors = paymentService.uploadPayments(file);
            if (errors.isEmpty()) {
                return ResponseEntity.ok("Payments uploaded successfully!");
            } else {
                return ResponseEntity.badRequest().body(String.join("\n", errors));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }
}

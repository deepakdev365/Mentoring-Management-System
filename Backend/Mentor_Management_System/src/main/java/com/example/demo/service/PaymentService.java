package com.example.demo.service;

import com.example.demo.model.Payment;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface PaymentService {

    Payment makePayment(Payment payment);

    Payment getPaymentById(Long paymentId);

    List<Payment> getPaymentsByStudent(Long studentId);

    List<Payment> getPaymentsByMentor(Long mentorId);

    List<Payment> getAllPayments();
    
    Payment getPaymentByEmail(String email);
    
    List<Payment> getPaymentsByRollNo(String rollNo);

    List<String> uploadPayments(MultipartFile file) throws Exception;
}

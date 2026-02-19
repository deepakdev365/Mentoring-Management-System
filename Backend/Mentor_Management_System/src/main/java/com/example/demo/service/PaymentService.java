package com.example.demo.service;

import com.example.demo.model.Payment;
import java.util.List;

public interface PaymentService {

    Payment makePayment(Payment payment);

    Payment getPaymentById(Long paymentId);

    List<Payment> getPaymentsByStudent(Long studentId);

    List<Payment> getPaymentsByMentor(Long mentorId);

    List<Payment> getAllPayments();
}

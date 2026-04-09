package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.model.Notification;
import com.example.demo.repository.NotificationRepository;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(String message, String recipientRegNo) {

        Notification n = new Notification();
        n.setMessage(message);
        n.setRecipientRegNo(recipientRegNo);

        return notificationRepository.save(n);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Override
    public List<Notification> getNotificationsForStudent(String regNo) {
        return notificationRepository.findByRecipientRegNoOrRecipientRegNoIsNullOrderByCreatedAtDesc(regNo);
    }
}
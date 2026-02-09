package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Notification;
import com.example.demo.repository.NotificationRepository;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository repository;

    @Override
    public Notification sendNotification(Notification notification) {
        return repository.save(notification);
    }

    @Override
    public List<Notification> getNotifications(Long receiverId, String receiverRole) {
        return repository.findByReceiverIdAndReceiverRole(receiverId, receiverRole);
    }
}

package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Notification;

public interface NotificationService {

    Notification createNotification(String message);

    List<Notification> getAllNotifications();
}

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Notification;
import com.example.demo.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@CrossOrigin("*")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @PostMapping("/send")
    public Notification send(@RequestBody Notification notification) {
        return service.sendNotification(notification);
    }

    @GetMapping("/{receiverId}/{receiverRole}")
    public List<Notification> getNotifications(
            @PathVariable Long receiverId,
            @PathVariable String receiverRole) {

        return service.getNotifications(receiverId, receiverRole);
    }
}

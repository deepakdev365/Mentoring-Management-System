package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Notification;
import com.example.demo.service.NotificationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin("*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // CREATE ANNOUNCEMENT or DIRECT MESSAGE
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Map<String, String> body){

        String message = body.get("message");
        String recipientRegNo = body.get("recipientRegNo");

        notificationService.createNotification(message, recipientRegNo);

        return ResponseEntity.ok("Notification sent");
    }

    // GET ALL (Global)
    @GetMapping("/all")
    public List<Notification> getAll(){
        return notificationService.getAllNotifications();
    }
    
    // GET FOR SPECIFIC STUDENT
    @GetMapping("/student/{regNo}")
    public List<Notification> getForStudent(@PathVariable String regNo){
        return notificationService.getNotificationsForStudent(regNo);
    }
}
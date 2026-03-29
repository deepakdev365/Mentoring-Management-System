package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Meeting;
import com.example.demo.service.MeetingService;

@RestController
@RequestMapping("/api/meetings")
@CrossOrigin
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping("/create")
    public ResponseEntity<?> createMeeting(@RequestBody Meeting meeting){

        meetingService.createMeeting(meeting);

        return ResponseEntity.ok("Meeting created successfully");
    }

    @GetMapping("/student/{regNo}")
    public List<Meeting> getByStudent(@PathVariable String regNo){
        return meetingService.getMeetingsByStudent(regNo);
    }

    @GetMapping("/mentor/{mentorId}")
    public List<Meeting> getByMentor(@PathVariable Long mentorId){
        return meetingService.getMeetingsByMentor(mentorId);
    }
}
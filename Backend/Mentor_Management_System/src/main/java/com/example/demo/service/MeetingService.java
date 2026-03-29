package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Meeting;

public interface MeetingService {

    Meeting createMeeting(Meeting meeting);

    List<Meeting> getMeetingsByStudent(String regNo);

    List<Meeting> getMeetingsByMentor(Long mentorId);
}
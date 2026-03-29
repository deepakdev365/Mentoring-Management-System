package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Meeting;
import com.example.demo.repository.MeetingRepository;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Override
    public Meeting createMeeting(Meeting meeting) {

        return meetingRepository.save(meeting);
    }

    @Override
    public List<Meeting> getMeetingsByStudent(String regNo) {
        return meetingRepository.findByRegistrationNumber(regNo);
    }

    @Override
    public List<Meeting> getMeetingsByMentor(Long mentorId) {
        return meetingRepository.findByMentorId(mentorId);
    }
}
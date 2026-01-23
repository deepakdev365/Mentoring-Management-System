package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Mentor;
import com.example.demo.repository.MentorRepository;

import java.util.Optional;

@Service
public class MentorService {

    @Autowired
    private MentorRepository mentorRepository;

    public Mentor login(String email, String password) {
        return mentorRepository
                .findByEmailAndPassword(email, password)
                .orElse(null);
    }
}

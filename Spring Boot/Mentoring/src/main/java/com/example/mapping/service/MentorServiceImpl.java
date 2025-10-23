package com.example.mapping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapping.model.Mentor;
import com.example.mapping.repository.MentorRepository;
import com.example.mapping.service.MentorService;

@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    private MentorRepository mentorRepository;

    @Override
    public Mentor registerMentor(Mentor mentor) {
        if (mentorRepository.existsByEmail(mentor.getEmail())) {
            throw new RuntimeException("Mentor with this email already exists!");
        }
        return mentorRepository.save(mentor);
    }

    @Override
    public Mentor loginMentor(String email, String password) {
        Mentor mentor = mentorRepository.findByEmail(email);
        if (mentor != null && mentor.getPassword().equals(password)) {
            return mentor;
        }
        throw new RuntimeException("Invalid email or password!");
    }

    @Override
    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    @Override
    public Optional<Mentor> getMentorById(Long id) {
        return mentorRepository.findById(id);
    }

    @Override
    public Mentor getMentorByEmail(String email) {
        return mentorRepository.findByEmail(email);
    }

    @Override
    public Mentor getMentorByCode(String mentorCode) {
        return mentorRepository.findByMentorCode(mentorCode);
    }

    @Override
    public boolean existsByEmail(String email) {
        return mentorRepository.existsByEmail(email);
    }

    @Override
    public Mentor updateMentor(Long id, Mentor mentorDetails) {
        return mentorRepository.findById(id).map(mentor -> {
            mentor.setName(mentorDetails.getName());
            mentor.setEmail(mentorDetails.getEmail());
            mentor.setDepartment(mentorDetails.getDepartment());
            mentor.setDesignation(mentorDetails.getDesignation());
            mentor.setPhone(mentorDetails.getPhone());
            mentor.setGender(mentorDetails.getGender());
            return mentorRepository.save(mentor);
        }).orElseThrow(() -> new RuntimeException("Mentor not found with ID: " + id));
    }

    @Override
    public void deleteMentor(Long id) {
        mentorRepository.deleteById(id);
    }
}

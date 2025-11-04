package com.example.mapping.service;

import java.util.List;
import java.util.Optional;
import com.example.mapping.model.Mentor;

public interface MentorService {

    Mentor registerMentor(Mentor mentor);

    Mentor loginMentor(String email, String password);

    List<Mentor> getAllMentors();

    Optional<Mentor> getMentorById(Long id);

    Mentor getMentorByEmail(String email);

    boolean existsByEmail(String email);

    Mentor updateMentor(Long id, Mentor mentorDetails);

    void deleteMentor(Long id);
}

package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import com.example.demo.model.Mentor;

public interface MentorService {

    void uploadMentorExcel(MultipartFile file);

    Mentor login(String email, String password);
    
    List<Mentor> getAllMentors();
}

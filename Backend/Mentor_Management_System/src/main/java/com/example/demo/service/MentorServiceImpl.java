package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Mentor;
import com.example.demo.repository.MentorRepository;
import com.example.demo.service.MentorService;
import com.example.demo.util.ExcelHelper;

@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    private MentorRepository mentorRepository;

    @Override
    public void uploadMentorExcel(MultipartFile file) {
        try {
            List<Mentor> mentors =
                    ExcelHelper.excelToMentor(file.getInputStream());
            mentorRepository.saveAll(mentors);
        } catch (Exception e) {
            throw new RuntimeException("Excel upload failed", e);
        }
    }

    @Override
    public Mentor login(String email, String password) {
        return mentorRepository
                .findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
}

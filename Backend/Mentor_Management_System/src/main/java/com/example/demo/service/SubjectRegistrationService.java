package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;
import com.example.demo.model.SubjectRegistration;

import java.util.List;

public interface SubjectRegistrationService {

    List<String> uploadSubjectRegistration(MultipartFile file) throws Exception;
    
    List<SubjectRegistration> getRegistrationsByRegNo(String regNo);

}

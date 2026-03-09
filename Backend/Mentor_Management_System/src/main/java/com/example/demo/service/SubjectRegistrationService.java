package com.example.demo.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface SubjectRegistrationService {

    List<String> uploadSubjectRegistration(MultipartFile file) throws Exception;

}
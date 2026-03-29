package com.example.demo.service;

import com.example.demo.model.StudentMarks;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentMarksService {
    void uploadExcel(MultipartFile file) throws Exception;
    List<StudentMarks> getAllMarks();
    List<StudentMarks> getMarksByRollNo(String rollNo);
}

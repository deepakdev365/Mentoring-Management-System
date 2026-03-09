package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface AttendanceService {

    List<String> uploadAttendance(MultipartFile file) throws Exception;

}
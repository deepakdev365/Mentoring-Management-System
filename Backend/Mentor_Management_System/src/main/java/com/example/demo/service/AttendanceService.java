package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttendanceService {

    List<String> uploadAttendance(MultipartFile file) throws Exception;

}

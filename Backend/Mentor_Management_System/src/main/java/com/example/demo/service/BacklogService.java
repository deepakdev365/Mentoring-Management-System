package com.example.demo.service;

import com.example.demo.model.Backlog;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BacklogService {

    List<String> uploadExcel(MultipartFile file) throws Exception;

    List<Backlog> getBacklogsByEmail(String email);

    void deleteBacklogById(Long id);
}

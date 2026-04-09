package com.example.demo.repository;

import com.example.demo.model.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

@Repository
public interface BacklogRepository extends JpaRepository<Backlog, Long> {

    List<Backlog> findByEmailIgnoreCase(String email);

    List<Backlog> findByEmailInIgnoreCase(List<String> emails);

    boolean existsByEmailIgnoreCaseAndSubjectCodeIgnoreCase(String email, String subjectCode);

    Optional<Backlog> findByRollNoAndSubjectCodeAndSemester(String rollNo, String subjectCode, String semester);
}

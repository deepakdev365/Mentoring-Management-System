package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Mentor;

public interface MentorRepository extends JpaRepository<Mentor, Integer> {

    Optional<Mentor> findByEmail(String email);

    Optional<Mentor> findByEmailAndPassword(String email, String password);
}

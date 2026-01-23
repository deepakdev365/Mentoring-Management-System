package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Mentor;

import java.util.Optional;
@Repository
public interface MentorRepository extends JpaRepository<Mentor, Integer> {

    Optional<Mentor> findByEmailAndPassword(String email, String password);
}

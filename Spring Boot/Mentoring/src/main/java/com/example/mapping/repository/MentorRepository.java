package com.example.mapping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mapping.model.Mentor;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {
    Mentor findByEmail(String email);
    boolean existsByEmail(String email);
}

package com.example.mapping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mapping.model.Complaint;
import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByMentor_Email(String email);
    List<Complaint> findByStudent_Email(String email);
}

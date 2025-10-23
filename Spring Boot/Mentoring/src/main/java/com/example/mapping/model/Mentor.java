package com.example.mapping.model;

import jakarta.persistence.*;
import java.util.List;

import org.springframework.boot.autoconfigure.jms.JmsProperties.Listener.Session;

@Entity
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mentorId;

    @Column(unique = true)
    private String mentorCode;

    private String name;
    private String email;
    private String password;
    private String department;
    private String designation;
    private String phone;
    private String gender;
  
    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
    private List<Student> mentees;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
    private List<Session> sessions;

    public Mentor() {
        super();
    }

    public Mentor(String mentorCode, String name, String email, String password,
                  String department, String designation, String phone, String gender) {
        super();
        this.mentorCode = mentorCode;
        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;
        this.designation = designation;
        this.phone = phone;
        this.gender = gender;
    }

    public Mentor(Long mentorId, String mentorCode, String name, String email, String password,
                  String department, String designation, String phone, String gender, List<Student> mentees) {
        this.mentorId = mentorId;
        this.mentorCode = mentorCode;
        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;
        this.designation = designation;
        this.phone = phone;
        this.gender = gender;
        this.mentees = mentees;
    }

    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public String getMentorCode() {
        return mentorCode;
    }

    public void setMentorCode(String mentorCode) {
        this.mentorCode = mentorCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Student> getMentees() {
        return mentees;
    }

    public void setMentees(List<Student> mentees) {
        this.mentees = mentees;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    @Override
    public String toString() {
        return "Mentor [mentorId=" + mentorId + ", mentorCode=" + mentorCode +
               ", name=" + name + ", email=" + email + ", department=" + department +
               ", designation=" + designation + ", phone=" + phone + "]";
    }
}



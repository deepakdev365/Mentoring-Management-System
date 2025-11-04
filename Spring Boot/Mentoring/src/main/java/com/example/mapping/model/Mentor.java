package com.example.mapping.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mentorId;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    private String gender;
    private String department;
    private String designation;
    private String password;

    @OneToMany(mappedBy = "mentor")
    @JsonIgnore
    private List<Student> students;

	public Long getMentorId() {
		return mentorId;
	}

	public void setMentorId(Long mentorId) {
		this.mentorId = mentorId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Mentor(Long mentorId, String fullName, String email, String phoneNumber, String gender, String department,
			String designation, String password, List<Student> students) {
		super();
		this.mentorId = mentorId;
		this.fullName = fullName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.department = department;
		this.designation = designation;
		this.password = password;
		this.students = students;
	}

	public Mentor() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Mentor [mentorId=" + mentorId + ", fullName=" + fullName + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", gender=" + gender + ", department=" + department + ", designation=" + designation + ", password="
				+ password + ", students=" + students + "]";
	}

   }

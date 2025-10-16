package com.example.mapping.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String name;
    private String email;
    private String password;
    private String rollNo;
    private String className;
    private Double cgpa;
    private Integer attendance;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Double getCgpa() {
		return cgpa;
	}
	public void setCgpa(Double cgpa) {
		this.cgpa = cgpa;
	}
	public Integer getAttendance() {
		return attendance;
	}
	public void setAttendance(Integer attendance) {
		this.attendance = attendance;
	}
	public Student(Long id, String name, String email, String password, String rollNo, String className, Double cgpa,
			Integer attendance) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.rollNo = rollNo;
		this.className = className;
		this.cgpa = cgpa;
		this.attendance = attendance;
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Sudent [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", rollNo="
				+ rollNo + ", className=" + className + ", cgpa=" + cgpa + ", attendance=" + attendance + "]";
	}

}

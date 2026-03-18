package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "mentor")
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String password;
    private String fullName;
    private String department;
    private String designation;
    private String phoneNo;
    
 // Address details
    private String localAddress;
    private String permanentAddress;
    private String city;
    private String state;
    private String zipCode;
    
    @OneToMany(mappedBy = "mentor")
    @JsonManagedReference
    private List<Student> students;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getLocalAddress() {
		return localAddress;
	}

	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Mentor(int id, String email, String password, String fullName, String department, String designation,
			String phoneNo, String localAddress, String permanentAddress, String city, String state, String zipCode,
			List<Student> students) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.department = department;
		this.designation = designation;
		this.phoneNo = phoneNo;
		this.localAddress = localAddress;
		this.permanentAddress = permanentAddress;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.students = students;
	}

	public Mentor() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Mentor [id=" + id + ", email=" + email + ", password=" + password + ", fullName=" + fullName
				+ ", department=" + department + ", designation=" + designation + ", phoneNo=" + phoneNo
				+ ", localAddress=" + localAddress + ", permanentAddress=" + permanentAddress + ", city=" + city
				+ ", state=" + state + ", zipCode=" + zipCode + ", students=" + students + "]";
	}
   
    
    
    
    
}

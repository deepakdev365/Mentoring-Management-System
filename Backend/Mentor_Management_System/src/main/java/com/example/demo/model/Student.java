package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long registrationNumber;   // Auto-generated registration number

	    // Personal details
	    private String fullName;
	    private String fatherGuardianName;

	    @Column(unique = true, nullable = false)
	    private String email;

	    private String dob;
	    private String gender;
	    private String nationality;
	    private String religion;
	    private String emergencyContact;

	    @Column(nullable = false)
	    private String phoneNumber;

	    // Address details
	    private String localAddress;
	    private String permanentAddress;
	    private String city;
	    private String state;
	    private String zipCode;

	    // Academic details
	    private String admissionNumber;
	    private String applicationNumber;
	    private String feeCategory;
	    private String dateOfAdmission;
	    private String program;
	    private String branch;
	    private String semester;
	    private String rollNo;
	    private String eligibilityNumber;
	    private String prnNo;
	    private String batch;
	    private String department;
	    private String password;
		public Long getRegistrationNumber() {
			return registrationNumber;
		}
		public void setRegistrationNumber(Long registrationNumber) {
			this.registrationNumber = registrationNumber;
		}
		public String getFullName() {
			return fullName;
		}
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
		public String getFatherGuardianName() {
			return fatherGuardianName;
		}
		public void setFatherGuardianName(String fatherGuardianName) {
			this.fatherGuardianName = fatherGuardianName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getDob() {
			return dob;
		}
		public void setDob(String dob) {
			this.dob = dob;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public String getNationality() {
			return nationality;
		}
		public void setNationality(String nationality) {
			this.nationality = nationality;
		}
		public String getReligion() {
			return religion;
		}
		public void setReligion(String religion) {
			this.religion = religion;
		}
		public String getEmergencyContact() {
			return emergencyContact;
		}
		public void setEmergencyContact(String emergencyContact) {
			this.emergencyContact = emergencyContact;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
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
		public String getAdmissionNumber() {
			return admissionNumber;
		}
		public void setAdmissionNumber(String admissionNumber) {
			this.admissionNumber = admissionNumber;
		}
		public String getApplicationNumber() {
			return applicationNumber;
		}
		public void setApplicationNumber(String applicationNumber) {
			this.applicationNumber = applicationNumber;
		}
		public String getFeeCategory() {
			return feeCategory;
		}
		public void setFeeCategory(String feeCategory) {
			this.feeCategory = feeCategory;
		}
		public String getDateOfAdmission() {
			return dateOfAdmission;
		}
		public void setDateOfAdmission(String dateOfAdmission) {
			this.dateOfAdmission = dateOfAdmission;
		}
		public String getProgram() {
			return program;
		}
		public void setProgram(String program) {
			this.program = program;
		}
		public String getBranch() {
			return branch;
		}
		public void setBranch(String branch) {
			this.branch = branch;
		}
		public String getSemester() {
			return semester;
		}
		public void setSemester(String semester) {
			this.semester = semester;
		}
		public String getRollNo() {
			return rollNo;
		}
		public void setRollNo(String rollNo) {
			this.rollNo = rollNo;
		}
		public String getEligibilityNumber() {
			return eligibilityNumber;
		}
		public void setEligibilityNumber(String eligibilityNumber) {
			this.eligibilityNumber = eligibilityNumber;
		}
		public String getPrnNo() {
			return prnNo;
		}
		public void setPrnNo(String prnNo) {
			this.prnNo = prnNo;
		}
		public String getBatch() {
			return batch;
		}
		public void setBatch(String batch) {
			this.batch = batch;
		}
		public String getDepartment() {
			return department;
		}
		public void setDepartment(String department) {
			this.department = department;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public Student(Long registrationNumber, String fullName, String fatherGuardianName, String email, String dob,
				String gender, String nationality, String religion, String emergencyContact, String phoneNumber,
				String localAddress, String permanentAddress, String city, String state, String zipCode,
				String admissionNumber, String applicationNumber, String feeCategory, String dateOfAdmission,
				String program, String branch, String semester, String rollNo, String eligibilityNumber, String prnNo,
				String batch, String department, String password) {
			super();
			this.registrationNumber = registrationNumber;
			this.fullName = fullName;
			this.fatherGuardianName = fatherGuardianName;
			this.email = email;
			this.dob = dob;
			this.gender = gender;
			this.nationality = nationality;
			this.religion = religion;
			this.emergencyContact = emergencyContact;
			this.phoneNumber = phoneNumber;
			this.localAddress = localAddress;
			this.permanentAddress = permanentAddress;
			this.city = city;
			this.state = state;
			this.zipCode = zipCode;
			this.admissionNumber = admissionNumber;
			this.applicationNumber = applicationNumber;
			this.feeCategory = feeCategory;
			this.dateOfAdmission = dateOfAdmission;
			this.program = program;
			this.branch = branch;
			this.semester = semester;
			this.rollNo = rollNo;
			this.eligibilityNumber = eligibilityNumber;
			this.prnNo = prnNo;
			this.batch = batch;
			this.department = department;
			this.password = password;
		}
		public Student() {
			super();
			// TODO Auto-generated constructor stub
		}
		@Override
		public String toString() {
			return "Student [registrationNumber=" + registrationNumber + ", fullName=" + fullName
					+ ", fatherGuardianName=" + fatherGuardianName + ", email=" + email + ", dob=" + dob + ", gender="
					+ gender + ", nationality=" + nationality + ", religion=" + religion + ", emergencyContact="
					+ emergencyContact + ", phoneNumber=" + phoneNumber + ", localAddress=" + localAddress
					+ ", permanentAddress=" + permanentAddress + ", city=" + city + ", state=" + state + ", zipCode="
					+ zipCode + ", admissionNumber=" + admissionNumber + ", applicationNumber=" + applicationNumber
					+ ", feeCategory=" + feeCategory + ", dateOfAdmission=" + dateOfAdmission + ", program=" + program
					+ ", branch=" + branch + ", semester=" + semester + ", rollNo=" + rollNo + ", eligibilityNumber="
					+ eligibilityNumber + ", prnNo=" + prnNo + ", batch=" + batch + ", department=" + department
					+ ", password=" + password + "]";
		}

	    
	    
	    
}

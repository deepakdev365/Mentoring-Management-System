package com.example.demo.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="subject_registration_id")
    private SubjectRegistration subjectRegistration;

    private int totalClasses;
    private int presentClasses;
    private double percentage;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SubjectRegistration getSubjectRegistration() {
		return subjectRegistration;
	}
	public void setSubjectRegistration(SubjectRegistration subjectRegistration) {
		this.subjectRegistration = subjectRegistration;
	}
	public int getTotalClasses() {
		return totalClasses;
	}
	public void setTotalClasses(int totalClasses) {
		this.totalClasses = totalClasses;
	}
	public int getPresentClasses() {
		return presentClasses;
	}
	public void setPresentClasses(int presentClasses) {
		this.presentClasses = presentClasses;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public Attendance(Long id, SubjectRegistration subjectRegistration, int totalClasses, int presentClasses,
			double percentage) {
		super();
		this.id = id;
		this.subjectRegistration = subjectRegistration;
		this.totalClasses = totalClasses;
		this.presentClasses = presentClasses;
		this.percentage = percentage;
	}
	public Attendance() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Attendance [id=" + id + ", subjectRegistration=" + subjectRegistration + ", totalClasses="
				+ totalClasses + ", presentClasses=" + presentClasses + ", percentage=" + percentage + "]";
	}
    
    

}
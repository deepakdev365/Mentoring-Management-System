package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "student_marks")
public class StudentMarks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "roll_no")
    private String rollNo;

    @Column(name = "subject_code")
    private String subjectCode;

    private int internalMarks;
    private int externalMarks;
    private int totalMarks;
    private double percentage;
    private String grade;
    private String result;

    // getters & setters
    public Long getId() { return id; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public int getInternalMarks() { return internalMarks; }
    public void setInternalMarks(int internalMarks) { this.internalMarks = internalMarks; }

    public int getExternalMarks() { return externalMarks; }
    public void setExternalMarks(int externalMarks) { this.externalMarks = externalMarks; }

    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
}
